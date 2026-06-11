package com.campus.food.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(nullable = false)
    private Integer rating = 5;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 500)
    private String image;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "like_count")
    private Integer likeCount = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Review() {
    }

    public Review(Long id, User user, Shop shop, Integer rating, String content,
                  String image, Status status, Integer likeCount, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.shop = shop;
        this.rating = rating;
        this.content = content;
        this.image = image;
        this.status = status;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }

    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    public static class ReviewBuilder {
        private Long id;
        private User user;
        private Shop shop;
        private Integer rating = 5;
        private String content;
        private String image;
        private Status status = Status.PENDING;
        private Integer likeCount = 0;
        private LocalDateTime createdAt = LocalDateTime.now();

        ReviewBuilder() {
        }

        public ReviewBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReviewBuilder user(User user) {
            this.user = user;
            return this;
        }

        public ReviewBuilder shop(Shop shop) {
            this.shop = shop;
            return this;
        }

        public ReviewBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public ReviewBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ReviewBuilder image(String image) {
            this.image = image;
            return this;
        }

        public ReviewBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public ReviewBuilder likeCount(Integer likeCount) {
            this.likeCount = likeCount;
            return this;
        }

        public ReviewBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Review build() {
            return new Review(id, user, shop, rating, content, image, status, likeCount, createdAt);
        }
    }
}
