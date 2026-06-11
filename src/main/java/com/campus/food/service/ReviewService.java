package com.campus.food.service;

import com.campus.food.dto.ReviewRequest;
import com.campus.food.entity.*;
import com.campus.food.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewLikeRepository reviewLikeRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Review createReview(ReviewRequest request, Long userId, String imagePath) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new RuntimeException("店铺不存在"));

        Review review = Review.builder()
                .user(user)
                .shop(shop)
                .rating(request.getRating())
                .content(request.getContent())
                .image(imagePath)
                .status(Review.Status.PENDING)
                .build();

        review = reviewRepository.save(review);
        updateShopRating(shop.getId());
        return review;
    }

    @Transactional
    public void updateShopRating(Long shopId) {
        Double avg = reviewRepository.findAverageRatingByShopId(shopId);
        long count = reviewRepository.countByShopIdAndStatus(shopId, Review.Status.APPROVED);
        Shop shop = shopRepository.findById(shopId).orElse(null);
        if (shop != null) {
            shop.setAvgRating(avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0);
            shop.setReviewCount((int) count);
            shopRepository.save(shop);
        }
    }

    public Page<Review> findByShop(Long shopId, int page, int size) {
        return reviewRepository.findByShopIdOrderByCreatedAtDesc(shopId, PageRequest.of(page, size));
    }

    public Page<Review> findByUser(Long userId, int page, int size) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));
    }

    public Page<Review> findByStatus(Review.Status status, int page, int size) {
        return reviewRepository.findByStatusOrderByCreatedAtDesc(status, PageRequest.of(page, size));
    }

    public List<Review> findTopLiked(int limit) {
        return reviewRepository.findTopLiked(PageRequest.of(0, limit));
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("点评不存在"));
    }

    @Transactional
    public Review approveReview(Long id) {
        Review review = findById(id);
        review.setStatus(Review.Status.APPROVED);
        review = reviewRepository.save(review);
        updateShopRating(review.getShop().getId());
        return review;
    }

    @Transactional
    public Review rejectReview(Long id) {
        Review review = findById(id);
        review.setStatus(Review.Status.REJECTED);
        review = reviewRepository.save(review);
        return review;
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = findById(id);
        Long shopId = review.getShop().getId();
        reviewRepository.deleteById(id);
        updateShopRating(shopId);
    }

    public long countByStatus(Review.Status status) {
        return reviewRepository.countByStatus(status);
    }

    @Transactional
    public boolean toggleLike(Long reviewId, Long userId) {
        if (reviewLikeRepository.existsByUserIdAndReviewId(userId, reviewId)) {
            reviewLikeRepository.findByUserIdAndReviewId(userId, reviewId)
                    .ifPresent(reviewLikeRepository::delete);
            reviewRepository.findById(reviewId).ifPresent(r -> {
                r.setLikeCount(r.getLikeCount() - 1);
                reviewRepository.save(r);
            });
            return false; // unliked
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("用户不存在"));
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("点评不存在"));
            ReviewLike like = ReviewLike.builder()
                    .user(user)
                    .review(review)
                    .build();
            reviewLikeRepository.save(like);
            review.setLikeCount(review.getLikeCount() + 1);
            reviewRepository.save(review);
            return true; // liked
        }
    }

    public boolean hasUserLiked(Long reviewId, Long userId) {
        return reviewLikeRepository.existsByUserIdAndReviewId(userId, reviewId);
    }
}
