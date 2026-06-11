package com.campus.food.service;

import com.campus.food.entity.Favorite;
import com.campus.food.entity.Shop;
import com.campus.food.entity.User;
import com.campus.food.repository.FavoriteRepository;
import com.campus.food.repository.ShopRepository;
import com.campus.food.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Transactional
    public boolean toggleFavorite(Long shopId, Long userId) {
        if (favoriteRepository.existsByUserIdAndShopId(userId, shopId)) {
            favoriteRepository.findByUserIdAndShopId(userId, shopId)
                    .ifPresent(favoriteRepository::delete);
            return false; // removed
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("用户不存在"));
            Shop shop = shopRepository.findById(shopId)
                    .orElseThrow(() -> new RuntimeException("店铺不存在"));
            Favorite favorite = Favorite.builder()
                    .user(user)
                    .shop(shop)
                    .build();
            favoriteRepository.save(favorite);
            return true; // added
        }
    }

    public boolean isFavorited(Long shopId, Long userId) {
        return favoriteRepository.existsByUserIdAndShopId(userId, shopId);
    }

    public long countByShop(Long shopId) {
        return favoriteRepository.countByShopId(shopId);
    }

    public Page<Favorite> findByUser(Long userId, int page, int size) {
        return favoriteRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));
    }
}
