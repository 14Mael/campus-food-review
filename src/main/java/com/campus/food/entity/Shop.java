package com.campus.food.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 200)
    private String location;

    @Column(length = 500)
    private String image;

    @Column(length = 50)
    private String category;

    @Column(name = "avg_rating")
    private Double avgRating = 0.0;

    @Column(name = "review_count")
    private Integer reviewCount = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> menuItems;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    public Shop() {
    }

    public Shop(Long id, String name, String description, String location, String image,
                String category, Double avgRating, Integer reviewCount, LocalDateTime createdAt,
                List<MenuItem> menuItems, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.image = image;
        this.category = category;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
        this.createdAt = createdAt;
        this.menuItems = menuItems;
        this.reviews = reviews;
    }

    public static ShopBuilder builder() {
        return new ShopBuilder();
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop)) return false;
        Shop shop = (Shop) o;
        return Objects.equals(id, shop.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Shop{id=" + id + ", name='" + name + "', category='" + category + "'}";
    }

    public static class ShopBuilder {
        private Long id;
        private String name;
        private String description;
        private String location;
        private String image;
        private String category;
        private Double avgRating = 0.0;
        private Integer reviewCount = 0;
        private LocalDateTime createdAt = LocalDateTime.now();
        private List<MenuItem> menuItems;
        private List<Review> reviews;

        ShopBuilder() {
        }

        public ShopBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ShopBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ShopBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ShopBuilder location(String location) {
            this.location = location;
            return this;
        }

        public ShopBuilder image(String image) {
            this.image = image;
            return this;
        }

        public ShopBuilder category(String category) {
            this.category = category;
            return this;
        }

        public ShopBuilder avgRating(Double avgRating) {
            this.avgRating = avgRating;
            return this;
        }

        public ShopBuilder reviewCount(Integer reviewCount) {
            this.reviewCount = reviewCount;
            return this;
        }

        public ShopBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ShopBuilder menuItems(List<MenuItem> menuItems) {
            this.menuItems = menuItems;
            return this;
        }

        public ShopBuilder reviews(List<Review> reviews) {
            this.reviews = reviews;
            return this;
        }

        public Shop build() {
            return new Shop(id, name, description, location, image, category, avgRating, reviewCount, createdAt, menuItems, reviews);
        }
    }
}
