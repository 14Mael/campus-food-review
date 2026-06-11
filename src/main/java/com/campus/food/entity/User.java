package com.campus.food.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String nickname;

    @Column(length = 200)
    private String avatar;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favorite> favorites;

    public User() {
    }

    public User(Long id, String username, String password, String nickname, String avatar,
                Role role, Boolean enabled, LocalDateTime createdAt,
                List<Review> reviews, List<Favorite> favorites) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.role = role;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.reviews = reviews;
        this.favorites = favorites;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', nickname='" + nickname + "'}";
    }

    public enum Role {
        USER, ADMIN
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;
        private String nickname;
        private String avatar;
        private Role role = Role.USER;
        private Boolean enabled = true;
        private LocalDateTime createdAt = LocalDateTime.now();
        private List<Review> reviews;
        private List<Favorite> favorites;

        UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserBuilder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder reviews(List<Review> reviews) {
            this.reviews = reviews;
            return this;
        }

        public UserBuilder favorites(List<Favorite> favorites) {
            this.favorites = favorites;
            return this;
        }

        public User build() {
            return new User(id, username, password, nickname, avatar, role, enabled, createdAt, reviews, favorites);
        }
    }
}
