package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.binding.ManagerEditItemBindingModel;
import com.dim.RestaurantManager.model.entity.Item;
import com.dim.RestaurantManager.model.entity.MenuItem;
import com.dim.RestaurantManager.model.service.ManagerAddItemServiceModel;
import com.dim.RestaurantManager.repository.ItemRepository;
import com.dim.RestaurantManager.repository.CategoryRepository;
import com.dim.RestaurantManager.repository.MenuRepository;
import com.dim.RestaurantManager.service.ItemService;
import com.dim.RestaurantManager.service.exceptions.common.CommonErrorMessages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;

    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository itemTypeRepository, MenuRepository menuRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = itemTypeRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public void init() {
        if (this.itemRepository.count() == 0) {
            this.itemRepository.saveAllAndFlush(List.of(
                    new Item()
                            .setName("ШЕФС БУРГЕР")
                            .setPrice(12.9)
                            .setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1637593051/shefs_burger_rospbo.webp")
                            .setDescription("Бриош питка, 100% телешки бъргър, разтопен английски чедър, хрупкав бекон, халапеньо, карамелизиран лук, айсберг, Chefs сос.Всички бургери и стекове се приготвят средно изпечени, освен ако не пожелаете друго!"),
                    new Item().setName("БУРГЕР С ПУЕШКО").setPrice(13.9).setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1637594483/burger_pueshko_wss7ap.jpg").setDescription("Бриош питка, слайсове пуешко филе в азиатски сос, айсберг, сотиран фенел и хрупкав пържен лук. Всички бургери и стекове се приготвят средно изпечени, освен ако не пожелаете друго!"),
                    new Item().setName("КЛАСИЧЕСКИ ЧИЙЗБУРГЕР").setPrice(11.9).setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1637594634/burger_klasicheski_cheese_m4qpgx.jpg").setDescription("Бриош питка, 100% телешки бъргър, разтопен английски чедър, домашно приготвени кисели краставички, айсберг, Chefs сос. Всички бургери и стекове се приготвят средно изпечени, освен ако не пожелаете друго!"),
                    new Item().setName("ВЕДЖИ БУРГЕР").setPrice(10.9).setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1637594691/burger_vegie_fqui1m.jpg").setDescription("Бриош питка, домашно приготвен бъргър от карфиол, нахут, морков и тиквички, айсберг, фенел, хрупкав пържен лук и Miso Mayo сос. Всички бургери и стекове се приготвят средно изпечени, освен ако не пожелаете друго!"),
                    new Item().setName("STREET DOGZ САНДВИЧ").setPrice(9.9).setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1637594915/sandwich_street_dogz_tlljrg.jpg").setDescription("Млечна багета с две наденички от младо телешко, хрупкав пържен лук, глазиран бекон, айсберг"),
                    new Item().setName("PULLED PORK САНДВИЧ").setPrice(10.9).setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1637595006/sandwich_pulled_pork_dbicxs.jpg").setDescription("Хляб рустик, слайсове от свинско месо, печено на бавен огън, айсберг, хрупкав пържен лук, домашно приготвен BBQ сос. Всички бургери и стекове се приготвят средно изпечени, освен ако не пожелаете друго!"),
                    new Item().setName("БУРГЕР БОКС МЕНЮ").setPrice(11.9).setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1637598505/burger_box_menu_cg8olj.webp").setDescription("Зингер или филе бургер, парче пиле, голямо картофено пюре, малка царевица"),
                    new Item().setName("САЛАТА “КАЛИНА“").setPrice(10.9).setImageUrl("https://res.cloudinary.com/dwqf9zolg/image/upload/v1637599053/salad_domat_patladjan_o9ztgs.jpg").setDescription("Домат, печен патладжан, овче сирене, маслинова паста, пресен спанак и печени орехи")

            ));
        }

    }
}
