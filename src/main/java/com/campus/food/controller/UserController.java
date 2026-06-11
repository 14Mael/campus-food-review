package com.campus.food.controller;

import com.campus.food.entity.User;
import com.campus.food.service.FavoriteService;
import com.campus.food.service.ReviewService;
import com.campus.food.service.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/profile")
    @Transactional
    public String profile(@RequestParam(defaultValue = "0") int reviewPage,
                          @RequestParam(defaultValue = "0") int favoritePage,
                          Authentication auth, Model model) {
        User user = securityUtil.getCurrentUser(auth);
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("profileUser", user);
        model.addAttribute("reviews",
                reviewService.findByUser(user.getId(), reviewPage, 10).getContent());
        model.addAttribute("favorites",
                favoriteService.findByUser(user.getId(), favoritePage, 10).getContent());
        return "profile";
    }
}
