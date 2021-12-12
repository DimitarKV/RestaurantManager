package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.binding.ManagerEditItemBindingModel;
import com.dim.RestaurantManager.model.entity.Item;
import com.dim.RestaurantManager.model.entity.MenuItem;
import com.dim.RestaurantManager.model.service.ManagerAddItemServiceModel;
import com.dim.RestaurantManager.model.service.ManagerEditItemServiceModel;
import com.dim.RestaurantManager.model.view.CategoryView;
import com.dim.RestaurantManager.repository.CategoryRepository;
import com.dim.RestaurantManager.repository.ItemRepository;
import com.dim.RestaurantManager.repository.MenuRepository;
import com.dim.RestaurantManager.service.MenuService;
import com.dim.RestaurantManager.service.exceptions.common.CommonErrorMessages;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ClassMapper classMapper;

    public MenuServiceImpl(MenuRepository menuRepository, ItemRepository itemRepository, CategoryRepository categoryRepository, ClassMapper classMapper) {
        this.menuRepository = menuRepository;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.classMapper = classMapper;
    }

    @Override
    public List<CategoryView> getMenuView() {
        return classMapper.toCategoryViewList(categoryRepository.findAll(), menuRepository.findAll());
    }

    @Override
    public void addMenuItem(ManagerAddItemServiceModel serviceModel) {
        Item item = new Item()
                .setName(serviceModel.getItemName())
                .setPrice(serviceModel.getItemPrice())
                .setDescription(serviceModel.getItemDescription())
                .setImageUrl(serviceModel.getImageUrl());
        item = itemRepository.saveAndFlush(item);
        MenuItem menuItem = new MenuItem()
                .setItem(item)
                .setCategory(categoryRepository.findById(serviceModel.getCategoryId())
                        .orElseThrow(() -> CommonErrorMessages.category(serviceModel.getCategoryId())));
        menuRepository.saveAndFlush(menuItem);
    }

    @Override
    public ManagerEditItemBindingModel getEditMenuItem(Long itemId) {
        MenuItem menuItem = menuRepository.findById(itemId)
                .orElseThrow(() -> CommonErrorMessages.item(itemId));
        return classMapper.toManagerEditItemBindingModel(menuItem);
    }

    @Override
    public void deleteMenuItem(Long menuItemId) {
        MenuItem menuItem = menuRepository.findById(menuItemId)
                .orElseThrow(() -> CommonErrorMessages.item(menuItemId));
        Long itemId = menuItem.getItem().getId();
        menuRepository.deleteById(menuItem.getId());
        itemRepository.deleteById(itemId);
    }

    @Override
    public void editItem(ManagerEditItemServiceModel serviceModel) {
        MenuItem menuItem = menuRepository.findById(serviceModel.getId())
                .orElseThrow(() -> CommonErrorMessages.item(serviceModel.getId()));

        menuItem.setCategory(categoryRepository.findById(serviceModel.getCategoryId())
                .orElseThrow(() -> CommonErrorMessages.category(serviceModel.getCategoryId())));
        menuItem = menuRepository.saveAndFlush(menuItem);

        Item item = menuItem.getItem();
        item
                .setImageUrl(serviceModel.getImageUrl())
                .setDescription(serviceModel.getDescription())
                .setMenuItem(menuItem)
                .setName(serviceModel.getName())
                .setPrice(serviceModel.getPrice());

        itemRepository.saveAndFlush(item);
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
        menuRepository.saveAndFlush(new MenuItem().setItem(itemRepository.findByName("САЛАТА “КАЛИНА“").get()).setCategory(categoryRepository.findByName("Салати").get()));
    }

}
