package com.campus.food.repository;

import com.campus.food.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserIdAndShopId(Long userId, Long shopId);

    Optional<Favorite> findByUserIdAndShopId(Long userId, Long shopId);

    long countByShopId(Long shopId);

    Page<Favorite> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
