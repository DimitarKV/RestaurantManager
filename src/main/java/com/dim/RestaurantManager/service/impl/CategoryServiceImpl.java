package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.Category;
import com.dim.RestaurantManager.repository.CategoryRepository;
import com.dim.RestaurantManager.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void init() {
        if(categoryRepository.count() == 0){
            categoryRepository.saveAllAndFlush(List.of(new Category().setName("Бургери"), new Category().setName("Салати")));
        }
    }
}
