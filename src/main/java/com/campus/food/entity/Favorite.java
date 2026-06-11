package com.campus.food.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "favorites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "shop_id"}))
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Favorite() {
    }

    public Favorite(Long id, User user, Shop shop, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.shop = shop;
        this.createdAt = createdAt;
    }

    public static FavoriteBuilder builder() {
        return new FavoriteBuilder();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorite)) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(id, favorite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class FavoriteBuilder {
        private Long id;
        private User user;
        private Shop shop;
        private LocalDateTime createdAt = LocalDateTime.now();

        FavoriteBuilder() {
        }

        public FavoriteBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FavoriteBuilder user(User user) {
            this.user = user;
            return this;
        }

        public FavoriteBuilder shop(Shop shop) {
            this.shop = shop;
            return this;
        }

        public FavoriteBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Favorite build() {
            return new Favorite(id, user, shop, createdAt);
        }
    }
}
