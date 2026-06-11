package com.campus.food.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 500)
    private String image;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    public MenuItem() {
    }

    public MenuItem(Long id, Shop shop, String name, String description,
                    BigDecimal price, String image, Integer sortOrder) {
        this.id = id;
        this.shop = shop;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.sortOrder = sortOrder;
    }

    public static MenuItemBuilder builder() {
        return new MenuItemBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(id, menuItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class MenuItemBuilder {
        private Long id;
        private Shop shop;
        private String name;
        private String description;
        private BigDecimal price;
        private String image;
        private Integer sortOrder = 0;

        MenuItemBuilder() {
        }

        public MenuItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MenuItemBuilder shop(Shop shop) {
            this.shop = shop;
            return this;
        }

        public MenuItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MenuItemBuilder description(String description) {
            this.description = description;
            return this;
        }

        public MenuItemBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public MenuItemBuilder image(String image) {
            this.image = image;
            return this;
        }

        public MenuItemBuilder sortOrder(Integer sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public MenuItem build() {
            return new MenuItem(id, shop, name, description, price, image, sortOrder);
        }
    }
}
