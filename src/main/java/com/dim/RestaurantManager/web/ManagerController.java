package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.binding.ManagerAddItemBindingModel;
import com.dim.RestaurantManager.model.binding.ManagerEditItemBindingModel;
import com.dim.RestaurantManager.model.entity.Category;
import com.dim.RestaurantManager.service.CategoryService;
import com.dim.RestaurantManager.service.MenuService;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ManagerController {

    private final CategoryService categoryService;
    private final MenuService menuService;
    private final ClassMapper classMapper;

    public ManagerController(CategoryService categoryService, MenuService itemService, ClassMapper classMapper) {
        this.categoryService = categoryService;
        this.menuService = itemService;
        this.classMapper = classMapper;
    }

    @ModelAttribute("addItemBindingModel")
    public ManagerAddItemBindingModel addItemBindingModel() {
        return new ManagerAddItemBindingModel();
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.findAll();
    }

    @PreAuthorize("isManager()")
    @PostMapping("/manager/category/add")
    public String addCategory(@RequestParam(name = "categoryName") String name,
                              RedirectAttributes redirectAttributes) {
        if (categoryService.hasCategory(name)) {
            redirectAttributes.addFlashAttribute("categoryAlreadyExists", true);
            return "redirect:/menu";
        }
        categoryService.addCategory(name);
        return "redirect:/menu";
    }

    @PreAuthorize("isManager()")
    @GetMapping("/manager/item/add")
    public String addItemPage() {
        return "add-item";
    }

    @PreAuthorize("isManager()")
    @PostMapping("/manager/item/add")
    public String addItem(@Valid ManagerAddItemBindingModel bindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addItemBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("addItemBindingModel", bindingModel);

            return "redirect:/manager/item/add";
        }

        menuService.addMenuItem(classMapper.toManagerAddItemServiceModel(bindingModel));

        return "redirect:/menu";
    }

    @PreAuthorize("isManager()")
    @DeleteMapping("/manager/item/{id}/delete")
    public String deleteItem(@PathVariable(name = "id") Long itemId) {
        menuService.deleteMenuItem(itemId);
        return "redirect:/menu";
    }

    @PreAuthorize("isManager()")
    @GetMapping("/menu/item/{itemId}/edit")
    public String getEditPage(@PathVariable(name = "itemId") Long itemId,
                              Model model) {
        if(model.getAttribute("editItemBindingModel") == null)
            model.addAttribute("editItemBindingModel", menuService.getEditMenuItem(itemId));
        return "edit-item";
    }

    @PreAuthorize("isManager()")
    @PatchMapping("/manager/item/{id}/edit")
    public String editItem(@PathVariable(name = "id") Long itemId,
                           @Valid ManagerEditItemBindingModel bindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editItemBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("editItemBindingModel", bindingModel);

            return "redirect:/menu/item/" + itemId + "/edit";
        }

        menuService.editItem(classMapper.toManagerEditItemServiceModel(bindingModel, itemId));

        return "redirect:/menu";
    }

    @PreAuthorize("isManager()")
    @PostMapping("/manager/category/{categoryId}/edit")
    public String editCategoryName(@PathVariable(name = "categoryId") Long categoryId,
                                   @RequestParam(name = "categoryName") String categoryName,
                                   RedirectAttributes redirectAttributes) {
        if (categoryService.hasCategory(categoryName)) {
            redirectAttributes.addFlashAttribute("categoryAlreadyExists", true);
            return "redirect:/menu";
        }
        categoryService.editCategoryName(categoryName, categoryId);
        return "redirect:/menu";
    }

    @Transactional
    @PreAuthorize("isManager()")
    @DeleteMapping("/manager/category/{categoryId}/delete")
    public String deleteCategory(@PathVariable(name = "categoryId") Long categoryId,
                                 RedirectAttributes redirectAttributes) {
        if (!categoryService.hasCategory(categoryId)) {
            redirectAttributes.addFlashAttribute("categoryDoesNotExist", true);
            return "redirect:/menu";
        }

        categoryService.deleteCategory(categoryId);

        return "redirect:/menu";
    }
}
