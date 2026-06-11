package com.campus.food.controller;

import com.campus.food.dto.RegisterRequest;
import com.campus.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "用户名或密码错误");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterRequest request, BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.register(request);
            redirectAttributes.addFlashAttribute("success", "注册成功，请登录");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
}
