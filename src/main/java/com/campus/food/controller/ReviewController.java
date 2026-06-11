package com.campus.food.controller;

import com.campus.food.dto.ReviewRequest;
import com.campus.food.entity.Review;
import com.campus.food.entity.User;
import com.campus.food.service.ReviewService;
import com.campus.food.service.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Value("${app.upload.path}")
    private String uploadPath;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping("/create")
    public String createReview(@Valid ReviewRequest request,
                               BindingResult result,
                               @RequestParam(required = false) MultipartFile image,
                               Authentication auth,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "请填写完整的点评信息");
            return "redirect:/review/new?shopId=" + request.getShopId();
        }

        try {
            User user = securityUtil.getCurrentUser(auth);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "请先登录");
                return "redirect:/login";
            }
            String imagePath = null;
            if (image != null && !image.isEmpty()) {
                imagePath = saveImage(image);
            }
            reviewService.createReview(request, user.getId(), imagePath);
            redirectAttributes.addFlashAttribute("success", "点评已提交，等待审核");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "提交失败: " + e.getMessage());
        }
        return "redirect:/shops/" + request.getShopId();
    }

    @PostMapping("/{id}/like")
    @ResponseBody
    public String toggleLike(@PathVariable Long id, Authentication auth) {
        try {
            User user = securityUtil.getCurrentUser(auth);
            if (user == null) {
                return "{\"success\": false, \"message\": \"请先登录\"}";
            }
            boolean liked = reviewService.toggleLike(id, user.getId());
            return "{\"success\": true, \"liked\": " + liked + "}";
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteReview(@PathVariable Long id, Authentication auth,
                               RedirectAttributes redirectAttributes) {
        try {
            User user = securityUtil.getCurrentUser(auth);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "请先登录");
                return "redirect:/login";
            }
            Review r = reviewService.findById(id);
            if (r.getUser().getId().equals(user.getId()) || user.getRole() == User.Role.ADMIN) {
                Long shopId = r.getShop().getId();
                reviewService.deleteReview(id);
                redirectAttributes.addFlashAttribute("success", "点评已删除");
                return "redirect:/shops/" + shopId;
            } else {
                redirectAttributes.addFlashAttribute("error", "无权删除此点评");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除失败: " + e.getMessage());
        }
        return "redirect:/index";
    }

    private String saveImage(MultipartFile file) throws IOException {
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
