package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.Category;
import com.dim.RestaurantManager.model.entity.Item;
import com.dim.RestaurantManager.model.entity.MenuItem;
import com.dim.RestaurantManager.repository.CategoryRepository;
import com.dim.RestaurantManager.repository.ItemRepository;
import com.dim.RestaurantManager.repository.MenuRepository;
import com.dim.RestaurantManager.repository.OrderRepository;
import com.dim.RestaurantManager.service.CategoryService;
import com.dim.RestaurantManager.service.exceptions.common.CommonErrorMessages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MenuRepository menuRepository, ItemRepository itemRepository, OrderRepository orderRepository) {
        this.categoryRepository = categoryRepository;
        this.menuRepository = menuRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void init() {
        if(categoryRepository.count() == 0){
            categoryRepository.saveAllAndFlush(List.of(
                    new Category().setName("Бургери"),
                    new Category().setName("Салати"),
                    new Category().setName("Напитки")
            ));
        }
    }

    @Override
    public boolean hasCategory(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    @Override
    public void addCategory(String name) {
        Category category = new Category()
                .setName(name);
        categoryRepository.saveAndFlush(category);
    }

    @Override
    public boolean hasCategory(Long value) {
        return categoryRepository.findById(value).isPresent();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void editCategoryName(String categoryName, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> CommonErrorMessages.category(categoryId));

        category.setName(categoryName);
        categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        List<MenuItem> menuItems = menuRepository.findAllByCategoryId(categoryId);

        for (MenuItem menuItem : menuItems) {
            Item item = menuItem.getItem();
            menuRepository.deleteById(menuItem.getId());
            orderRepository.deleteAllByItemId(item.getId());
            itemRepository.deleteById(item.getId());
        }

        categoryRepository.deleteById(categoryId);
    }
}
