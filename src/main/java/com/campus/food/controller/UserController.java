package com.campus.food.controller;

import com.campus.food.dto.ProfileUpdateRequest;
import com.campus.food.entity.User;
import com.campus.food.service.FavoriteService;
import com.campus.food.service.ReviewService;
import com.campus.food.service.SecurityUtil;
import com.campus.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/profile/edit")
    public String editProfile(Authentication auth, Model model) {
        User user = securityUtil.getCurrentUser(auth);
        if (user == null) {
            return "redirect:/login";
        }
        ProfileUpdateRequest req = new ProfileUpdateRequest();
        req.setNickname(user.getNickname());
        model.addAttribute("profileUpdate", req);
        return "profile-edit";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid ProfileUpdateRequest request,
                                BindingResult result,
                                Authentication auth,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "profile-edit";
        }
        try {
            User user = securityUtil.getCurrentUser(auth);
            if (user == null) {
                return "redirect:/login";
            }
            user.setNickname(request.getNickname());
            userService.save(user);
            redirectAttributes.addFlashAttribute("success", "个人信息已更新");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "更新失败: " + e.getMessage());
        }
        return "redirect:/user/profile";
    }
}
