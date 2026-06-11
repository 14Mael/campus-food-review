package com.campus.food.service;

import com.campus.food.entity.User;
import com.campus.food.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        String username = auth.getName();
        return userRepository.findByUsername(username).orElse(null);
    }

    public Long getCurrentUserId(Authentication auth) {
        User user = getCurrentUser(auth);
        return user != null ? user.getId() : null;
    }
}
