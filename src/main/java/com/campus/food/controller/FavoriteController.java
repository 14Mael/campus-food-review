package com.campus.food.controller;

import com.campus.food.entity.User;
import com.campus.food.service.FavoriteService;
import com.campus.food.service.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping("/{shopId}/toggle")
    @ResponseBody
    public String toggleFavorite(@PathVariable Long shopId, Authentication auth) {
        try {
            User user = securityUtil.getCurrentUser(auth);
            if (user == null) {
                return "{\"success\": false, \"message\": \"请先登录\"}";
            }
            boolean favorited = favoriteService.toggleFavorite(shopId, user.getId());
            long count = favoriteService.countByShop(shopId);
            return "{\"success\": true, \"favorited\": " + favorited + ", \"count\": " + count + "}";
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
        }
    }

    @PostMapping("/{shopId}/toggle-web")
    public String toggleFavoriteWeb(@PathVariable Long shopId, Authentication auth,
                                    RedirectAttributes redirectAttributes) {
        try {
            User user = securityUtil.getCurrentUser(auth);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "请先登录");
                return "redirect:/login";
            }
            boolean favorited = favoriteService.toggleFavorite(shopId, user.getId());
            redirectAttributes.addFlashAttribute("success",
                    favorited ? "已收藏" : "已取消收藏");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/shops/" + shopId;
    }
}
