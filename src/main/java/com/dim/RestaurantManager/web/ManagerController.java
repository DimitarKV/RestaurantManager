package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.binding.ManagerAddItemBindingModel;
import com.dim.RestaurantManager.service.CategoryService;
import com.dim.RestaurantManager.service.ItemService;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ManagerController {

    private final CategoryService categoryService;
    private final ItemService itemService;
    private final ClassMapper classMapper;

    public ManagerController(CategoryService categoryService, ItemService itemService, ClassMapper classMapper) {
        this.categoryService = categoryService;
        this.itemService = itemService;
        this.classMapper = classMapper;
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
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("bindingModel", bindingModel);

            return "redirect:/manager/item/add";
        }

        itemService.addItem(classMapper.toManagerAddItemServiceModel(bindingModel));



        return "redirect:/menu";
    }
}
