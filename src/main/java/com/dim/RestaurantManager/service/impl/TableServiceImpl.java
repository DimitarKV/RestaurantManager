package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.Bill;
import com.dim.RestaurantManager.model.entity.FoodTable;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.view.FoodTableView;
import com.dim.RestaurantManager.repository.BillRepository;
import com.dim.RestaurantManager.repository.TableRepository;
import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.service.TableService;
import com.dim.RestaurantManager.service.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;
    private final UserRepository userRepository;
    private final BillRepository billRepository;
    private final UserService userService;

    public TableServiceImpl(TableRepository tableRepository, UserRepository userRepository, BillRepository billRepository, UserService userService) {
        this.tableRepository = tableRepository;
        this.userRepository = userRepository;
        this.billRepository = billRepository;
        this.userService = userService;
    }


    @Override
    public void init() {
        if (tableRepository.count() == 0) {
            tableRepository.saveAllAndFlush(List.of(
                    new FoodTable().setNumber(1).setDescription("На балкона"),
                    new FoodTable().setNumber(2).setDescription("На първия етаж"),
                    new FoodTable().setNumber(3).setDescription("Панорамна гледка"),
                    new FoodTable().setNumber(4).setDescription("Панорамна гледка"),
                    new FoodTable().setNumber(5).setDescription("Панорамна гледка"),
                    new FoodTable().setNumber(6).setDescription("На балкона"),
                    new FoodTable().setNumber(7).setDescription("На балкона"),
                    new FoodTable().setNumber(8).setDescription("Вътре"),
                    new FoodTable().setNumber(9).setDescription("Вътре")
            ));
        }
    }

    @Override
    public List<FoodTableView> getFreeTable() {
        return tableRepository
                .findAll()
                .stream()
                .filter(t -> t.getBill() == null)
                .map(this::mapToFoodTableView)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodTableView> getOccupiedTables() {
        return tableRepository
                .findAll()
                .stream()
                .filter(t -> t.getBill() != null)
                .map(this::mapToFoodTableView)
                .collect(Collectors.toList());
    }

    @Override
    public void occupy(Integer number, RestaurantUser restaurantUser) {
        FoodTable table = tableRepository.findByNumber(number).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByUsername(restaurantUser.getUsername()).orElseThrow(EntityNotFoundException::new);
        Bill bill = new Bill().setTable(table);
        bill.getUsers().add(user);
        bill = billRepository.saveAndFlush(bill);
        user.setBill(bill);
        user = userRepository.saveAndFlush(user);
        userService.updatePrincipal();
    }

    private FoodTableView mapToFoodTableView(FoodTable foodTable) {
        return new FoodTableView().setNumber(foodTable.getNumber()).setDescription(foodTable.getDescription());
    }
}
