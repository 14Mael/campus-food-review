package com.campus.food.service;

import com.campus.food.entity.MenuItem;
import com.campus.food.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItem> findByShopId(Long shopId) {
        return menuItemRepository.findByShopIdOrderBySortOrderAsc(shopId);
    }

    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("菜品不存在"));
    }

    public MenuItem save(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public void deleteById(Long id) {
        menuItemRepository.deleteById(id);
    }
}
