package com.campus.food.service;

import com.campus.food.entity.Shop;
import com.campus.food.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public Page<Shop> findAll(int page, int size, String sortBy) {
        Sort sort;
        switch (sortBy) {
            case "rating":
                sort = Sort.by(Sort.Direction.DESC, "avgRating");
                break;
            case "reviews":
                sort = Sort.by(Sort.Direction.DESC, "reviewCount");
                break;
            default:
                sort = Sort.by(Sort.Direction.ASC, "id");
        }
        return shopRepository.findAll(PageRequest.of(page, size, sort));
    }

    public Page<Shop> searchByName(String name, int page, int size) {
        return shopRepository.findByNameContaining(name, PageRequest.of(page, size));
    }

    public Page<Shop> findByCategory(String category, int page, int size) {
        return shopRepository.findByCategoryContaining(category, PageRequest.of(page, size));
    }

    public Shop findById(Long id) {
        return shopRepository.findById(id).orElseThrow(() -> new RuntimeException("店铺不存在"));
    }

    @Transactional
    public Shop save(Shop shop) {
        return shopRepository.save(shop);
    }

    @Transactional
    public void deleteById(Long id) {
        shopRepository.deleteById(id);
    }

    public List<Shop> findTopRated(int limit) {
        return shopRepository.findTopRated(PageRequest.of(0, limit));
    }

    public List<Shop> findMostReviewed(int limit) {
        return shopRepository.findMostReviewed(PageRequest.of(0, limit));
    }

    public List<String> findAllCategories() {
        return shopRepository.findAll().stream()
                .map(Shop::getCategory)
                .distinct()
                .sorted()
                .collect(java.util.stream.Collectors.toList());
    }
}
