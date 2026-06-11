package com.campus.food.repository;

import com.campus.food.entity.Review;
import com.campus.food.entity.Review.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByShopIdOrderByCreatedAtDesc(Long shopId, Pageable pageable);

    Page<Review> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Page<Review> findByStatusOrderByCreatedAtDesc(Status status, Pageable pageable);

    List<Review> findByShopIdAndStatus(Long shopId, Status status);

    long countByStatus(Status status);

    @Query("SELECT r FROM Review r WHERE r.status = 'APPROVED' ORDER BY r.likeCount DESC")
    List<Review> findTopLiked(Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.shop.id = ?1 AND r.status = 'APPROVED'")
    Double findAverageRatingByShopId(Long shopId);

    long countByShopIdAndStatus(Long shopId, Status status);
}
