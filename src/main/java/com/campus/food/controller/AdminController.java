package com.campus.food.controller;

import com.campus.food.entity.*;
import com.campus.food.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    private com.campus.food.repository.UserRepository userRepository;

    @GetMapping({"", "/"})
    @Transactional
    public String dashboard(Model model) {
        long pendingCount = reviewService.countByStatus(Review.Status.PENDING);
        long approvedCount = reviewService.countByStatus(Review.Status.APPROVED);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("shopCount", shopService.findAll(0, 1, "latest").getTotalElements());
        model.addAttribute("topShops", shopService.findTopRated(5));
        return "admin/index";
    }

    // ========== Shop Management ==========

    @GetMapping("/shops")
    public String listShops(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Shop> shopPage = shopService.findAll(page, 20, "latest");
        model.addAttribute("shops", shopPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", shopPage.getTotalPages());
        return "admin/shops";
    }

    @GetMapping("/shops/new")
    public String newShopForm(Model model) {
        model.addAttribute("shop", new Shop());
        return "admin/shop-form";
    }

    @PostMapping("/shops/save")
    public String saveShop(@ModelAttribute Shop shop,
                           @RequestParam(required = false) MultipartFile imageFile,
                           RedirectAttributes redirectAttributes) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                shop.setImage(saveImage(imageFile));
            }
            shopService.save(shop);
            redirectAttributes.addFlashAttribute("success", "店铺保存成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "保存失败: " + e.getMessage());
        }
        return "redirect:/admin/shops";
    }

    @GetMapping("/shops/edit/{id}")
    public String editShopForm(@PathVariable Long id, Model model) {
        model.addAttribute("shop", shopService.findById(id));
        return "admin/shop-form";
    }

    @GetMapping("/shops/delete/{id}")
    public String deleteShop(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            shopService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "店铺已删除");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除失败: " + e.getMessage());
        }
        return "redirect:/admin/shops";
    }

    // ========== Menu Items ==========

    @GetMapping("/shops/{shopId}/menus")
    public String listMenuItems(@PathVariable Long shopId, Model model) {
        model.addAttribute("shop", shopService.findById(shopId));
        model.addAttribute("menuItems", menuItemService.findByShopId(shopId));
        return "admin/menus";
    }

    @PostMapping("/menus/save")
    public String saveMenuItem(@RequestParam Long shopId,
                               @RequestParam(required = false) Long id,
                               @RequestParam String name,
                               @RequestParam BigDecimal price,
                               @RequestParam(required = false) String description,
                               @RequestParam(defaultValue = "0") Integer sortOrder,
                               RedirectAttributes redirectAttributes) {
        try {
            Shop shop = shopService.findById(shopId);
            MenuItem item;
            if (id != null) {
                item = menuItemService.findById(id);
            } else {
                item = MenuItem.builder().shop(shop).build();
            }
            item.setName(name);
            item.setPrice(price);
            item.setDescription(description);
            item.setSortOrder(sortOrder);
            menuItemService.save(item);
            redirectAttributes.addFlashAttribute("success", "菜品保存成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "保存失败: " + e.getMessage());
        }
        return "redirect:/admin/shops/" + shopId + "/menus";
    }

    @GetMapping("/menus/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            MenuItem item = menuItemService.findById(id);
            Long shopId = item.getShop().getId();
            menuItemService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "菜品已删除");
            return "redirect:/admin/shops/" + shopId + "/menus";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除失败: " + e.getMessage());
            return "redirect:/admin/shops";
        }
    }

    // ========== Review Moderation ==========

    @GetMapping("/reviews")
    @Transactional
    public String listReviews(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "PENDING") Review.Status status,
                              Model model) {
        Page<Review> reviewPage = reviewService.findByStatus(status, page, 20);
        model.addAttribute("reviews", reviewPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reviewPage.getTotalPages());
        model.addAttribute("currentStatus", status);
        model.addAttribute("statuses", Review.Status.values());
        return "admin/reviews";
    }

    @GetMapping("/reviews/approve/{id}")
    public String approveReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reviewService.approveReview(id);
            redirectAttributes.addFlashAttribute("success", "点评已通过审核");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "操作失败: " + e.getMessage());
        }
        return "redirect:/admin/reviews";
    }

    @GetMapping("/reviews/reject/{id}")
    public String rejectReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reviewService.rejectReview(id);
            redirectAttributes.addFlashAttribute("success", "点评已驳回");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "操作失败: " + e.getMessage());
        }
        return "redirect:/admin/reviews";
    }

    @GetMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reviewService.deleteReview(id);
            redirectAttributes.addFlashAttribute("success", "点评已删除");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除失败: " + e.getMessage());
        }
        return "redirect:/admin/reviews";
    }

    // ========== Users ==========

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    private String saveImage(MultipartFile file) throws IOException {
        String uploadPath = System.getProperty("user.dir") + "/uploads";
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String originalName = file.getOriginalFilename();
        String suffix = "";
        if (originalName != null && originalName.contains(".")) {
            suffix = originalName.substring(originalName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID().toString() + suffix;
        Path targetPath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), targetPath);
        return "/uploads/" + fileName;
    }
}
