package com.campus.food.repository;

import com.campus.food.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Page<Shop> findByCategoryContaining(String category, Pageable pageable);

    Page<Shop> findByNameContaining(String name, Pageable pageable);

    Page<Shop> findByLocationContaining(String location, Pageable pageable);

    @Query("SELECT s FROM Shop s ORDER BY s.avgRating DESC")
    List<Shop> findTopRated(Pageable pageable);

    @Query("SELECT s FROM Shop s ORDER BY s.reviewCount DESC")
    List<Shop> findMostReviewed(Pageable pageable);

    List<Shop> findByCategory(String category);
}
