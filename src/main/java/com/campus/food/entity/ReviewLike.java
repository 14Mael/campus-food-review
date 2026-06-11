package com.campus.food.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "review_likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "review_id"}))
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public ReviewLike() {
    }

    public ReviewLike(Long id, User user, Review review, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.review = review;
        this.createdAt = createdAt;
    }

    public static ReviewLikeBuilder builder() {
        return new ReviewLikeBuilder();
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

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
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
        if (!(o instanceof ReviewLike)) return false;
        ReviewLike reviewLike = (ReviewLike) o;
        return Objects.equals(id, reviewLike.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class ReviewLikeBuilder {
        private Long id;
        private User user;
        private Review review;
        private LocalDateTime createdAt = LocalDateTime.now();

        ReviewLikeBuilder() {
        }

        public ReviewLikeBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReviewLikeBuilder user(User user) {
            this.user = user;
            return this;
        }

        public ReviewLikeBuilder review(Review review) {
            this.review = review;
            return this;
        }

        public ReviewLikeBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReviewLike build() {
            return new ReviewLike(id, user, review, createdAt);
        }
    }
}
