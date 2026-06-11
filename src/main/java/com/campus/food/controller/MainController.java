package com.campus.food.controller;

import com.campus.food.entity.Review;
import com.campus.food.entity.Shop;
import com.campus.food.entity.User;
import com.campus.food.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private SecurityUtil securityUtil;

    @GetMapping({"/", "/index"})
    @Transactional
    public String index(Model model) {
        List<Shop> topRated = shopService.findTopRated(6);
        List<Shop> mostReviewed = shopService.findMostReviewed(6);
        List<Review> hotReviews = reviewService.findTopLiked(8);

        model.addAttribute("topRated", topRated);
        model.addAttribute("mostReviewed", mostReviewed);
        model.addAttribute("hotReviews", hotReviews);
        return "index";
    }

    @GetMapping("/shops")
    public String listShops(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "latest") String sort,
                            @RequestParam(required = false) String category,
                            @RequestParam(required = false) String keyword,
                            Model model) {
        Page<Shop> shopPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            shopPage = shopService.searchByName(keyword, page, 12);
        } else if (category != null && !category.trim().isEmpty()) {
            shopPage = shopService.findByCategory(category, page, 12);
        } else {
            shopPage = shopService.findAll(page, 12, sort);
        }
        List<String> categories = shopService.findAllCategories();

        model.addAttribute("shops", shopPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", shopPage.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", categories);
        return "shops";
    }

    @GetMapping("/shops/{id}")
    @Transactional
    public String shopDetail(@PathVariable Long id,
                             @RequestParam(defaultValue = "0") int page,
                             Authentication auth,
                             Model model) {
        Shop shop = shopService.findById(id);
        Page<Review> reviewPage = reviewService.findByShop(id, page, 10);

        model.addAttribute("shop", shop);
        model.addAttribute("menuItems", menuItemService.findByShopId(id));
        model.addAttribute("reviews", reviewPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reviewPage.getTotalPages());
        model.addAttribute("favoriteCount", favoriteService.countByShop(id));

        User user = securityUtil.getCurrentUser(auth);
        if (user != null) {
            model.addAttribute("isFavorited", favoriteService.isFavorited(id, user.getId()));
            model.addAttribute("currentUser", user);
        }

        // Check if each review is liked by current user
        if (user != null) {
            for (Review r : reviewPage.getContent()) {
                r.setLikeCount(r.getLikeCount()); // no-op, just reference
            }
        }

        return "shop-detail";
    }

    @GetMapping("/review/new")
    public String newReviewForm(@RequestParam Long shopId, Model model) {
        Shop shop = shopService.findById(shopId);
        model.addAttribute("shop", shop);
        model.addAttribute("ratings", new int[]{1, 2, 3, 4, 5});
        return "review-form";
    }
}
