package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.MenuItem;
import com.dim.RestaurantManager.model.view.MenuView;
import com.dim.RestaurantManager.repository.CategoryRepository;
import com.dim.RestaurantManager.repository.ItemRepository;
import com.dim.RestaurantManager.repository.MenuRepository;
import com.dim.RestaurantManager.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public MenuServiceImpl(MenuRepository menuRepository, ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void init() {
        menuRepository.saveAndFlush(new MenuItem().setItem(itemRepository.findByName("ШЕФС БУРГЕР").get()).setCategory(categoryRepository.findByName("Бургери").get()));
        menuRepository.saveAndFlush(new MenuItem().setItem(itemRepository.findByName("БУРГЕР С ПУЕШКО").get()).setCategory(categoryRepository.findByName("Бургери").get()));
        menuRepository.saveAndFlush(new MenuItem().setItem(itemRepository.findByName("КЛАСИЧЕСКИ ЧИЙЗБУРГЕР").get()).setCategory(categoryRepository.findByName("Бургери").get()));
        menuRepository.saveAndFlush(new MenuItem().setItem(itemRepository.findByName("ВЕДЖИ БУРГЕР").get()).setCategory(categoryRepository.findByName("Бургери").get()));
        menuRepository.saveAndFlush(new MenuItem().setItem(itemRepository.findByName("STREET DOGZ САНДВИЧ").get()).setCategory(categoryRepository.findByName("Бургери").get()));
        menuRepository.saveAndFlush(new MenuItem().setItem(itemRepository.findByName("PULLED PORK САНДВИЧ").get()).setCategory(categoryRepository.findByName("Бургери").get()));
        menuRepository.saveAndFlush(new MenuItem().setItem(itemRepository.findByName("БУРГЕР БОКС МЕНЮ").get()).setCategory(categoryRepository.findByName("Бургери").get()));
        menuRepository.saveAndFlush(new MenuItem().setItem(itemRepository.findByName("САЛАТА “КАЛИНА“  320гр.").get()).setCategory(categoryRepository.findByName("Салати").get()));
    }

    @Override
    public MenuView getMenuView() {
        MenuView menuView = new MenuView();
        menuRepository.findAll()
                .forEach(menuView::putMenuItem);
        return menuView;
    }
}
