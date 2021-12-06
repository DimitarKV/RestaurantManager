package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.binding.JoinTableBindingModel;
import com.dim.RestaurantManager.model.binding.OccupyTableBindingModel;
import com.dim.RestaurantManager.model.view.FoodTableView;
import com.dim.RestaurantManager.service.BillService;
import com.dim.RestaurantManager.service.TableService;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TableController {
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @ModelAttribute(name = "freeTables")
    public List<FoodTableView> freeTables() {
        return tableService.getFreeTable();
    }

    @ModelAttribute(name = "occupiedTables")
    public List<FoodTableView> occupiedTables() {
        return tableService.getOccupiedTables();
    }

    @ModelAttribute(name = "occupyBindingModel")
    public OccupyTableBindingModel occupyTableBindingModel() {
        return new OccupyTableBindingModel();
    }

    @ModelAttribute(name = "joinBindingModel")
    public JoinTableBindingModel joinTableBindingModel(){
        return new JoinTableBindingModel();
    }

    @GetMapping("/table/register")
    public String getTableRegisterPage() {
        return "table-free";
    }

    @PreAuthorize("!canOrder()")
    @PostMapping("/table/occupy")
    public String occupyTable(@Valid OccupyTableBindingModel occupyBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal RestaurantUser user) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.occupyBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("occupyBindingModel", occupyBindingModel);
            return "redirect:/table/register";
        }

        tableService.occupy(occupyBindingModel.getNumber(), user);
        return "redirect:/menu";
    }

    // TODO
    @PostMapping("/table/join")
    public String joinTable(@Valid JoinTableBindingModel joinBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.joinBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("joinBindingModel", joinBindingModel);

            return "redirect:/table/register";
        }

        return "redirect:/menu";
    }
}
