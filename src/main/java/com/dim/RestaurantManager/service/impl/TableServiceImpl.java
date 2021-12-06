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
import com.dim.RestaurantManager.service.exceptions.common.CommonErrorMessages;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;
    private final UserRepository userRepository;
    private final BillRepository billRepository;
    private final UserService userService;
    private final ClassMapper classMapper;

    public TableServiceImpl(TableRepository tableRepository, UserRepository userRepository, BillRepository billRepository, UserService userService, ClassMapper classMapper) {
        this.tableRepository = tableRepository;
        this.userRepository = userRepository;
        this.billRepository = billRepository;
        this.userService = userService;
        this.classMapper = classMapper;
    }

    @Override
    public List<FoodTableView> getFreeTable() {
        return tableRepository
                .findAll()
                .stream()
                .filter(t -> t.getBill() == null)
                .map(classMapper::toFoodTableView)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodTableView> getOccupiedTables() {
        return tableRepository
                .findAll()
                .stream()
                .filter(t -> t.getBill() != null)
                .map(classMapper::toFoodTableView)
                .collect(Collectors.toList());
    }

    @Override
    public void occupy(Integer number, RestaurantUser restaurantUser) {
        FoodTable table = tableRepository.findByNumber(number)
                .orElseThrow(() -> CommonErrorMessages.table(number));
        User user = userRepository.findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        Bill bill = new Bill()
                .setTable(table)
                .setCreationDate(LocalDateTime.now());
        bill.getUsers().add(user);
        bill = billRepository.saveAndFlush(bill);
        user.setBill(bill);
        user = userRepository.saveAndFlush(user);
    }

    @Override
    public void join(Integer number, RestaurantUser restaurantUser) {
        FoodTable table = tableRepository.findByNumber(number)
                .orElseThrow(() -> CommonErrorMessages.table(number));
        User user = userRepository.findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        Bill bill = table.getBill();
        bill.getUsers().add(user);
        bill = billRepository.saveAndFlush(bill);
        user.setBill(bill);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void init() {
        if (tableRepository.count() == 0) {
            tableRepository.saveAllAndFlush(List.of(
                    new FoodTable()
                            .setTitle("Вътре")
                            .setNumber(1)
                            .setDescription("Насладете се на приятната атмосфера сред изисканата аристокрация на Филипополис акомпанирана с невероятната гледка към Стария град.")
                            .setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1638828570/table5_tkzwv3.jpg"),
                    new FoodTable()
                            .setTitle("На залеза")
                            .setNumber(2)
                            .setDescription("Хранете очите си с невероятната гледка към вулкана Сахат тепе, докато изтънченият Ви вкус се наслаждава на нашите гурме ястия.")
                            .setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1638828569/table2_lmcm9u.jpg"),
                    new FoodTable()
                            .setTitle("Гледка към центъра")
                            .setNumber(3)
                            .setDescription("Гледката ни към центъра на града немислимо е едно от най-добрите места да прекарате времето си докато сте наши клиенти.")
                            .setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1638828569/table1_oqltda.jpg"),
                    new FoodTable()
                            .setTitle("Изглед към Айфеловата кула")
                            .setNumber(4)
                            .setDescription("Определено тук са едни от първокласните ни маси с невероятна гледка към символа на града - Айфеловата кула.")
                            .setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1638828569/table3_r6kpwh.jpg"),
                    new FoodTable()
                            .setTitle("Отдалечен изглед")
                            .setNumber(5)
                            .setDescription("Тази маса е за типа хора, които искат да се насладят на атмосферата на вечерта в отдалечено местенце, необезпокоени от шумотевицата на града.")
                            .setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1638828569/table4_hdanvp.jpg")

            ));
        }
    }
}
