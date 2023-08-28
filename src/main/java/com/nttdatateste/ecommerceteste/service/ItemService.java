package com.nttdatateste.ecommerceteste.service;


import com.nttdatateste.ecommerceteste.entity.Brand;
import com.nttdatateste.ecommerceteste.entity.Category;
import com.nttdatateste.ecommerceteste.entity.Item;
import com.nttdatateste.ecommerceteste.entity.exception.BrandNotFoundException;
import com.nttdatateste.ecommerceteste.entity.exception.ItemNotFoundException;
import com.nttdatateste.ecommerceteste.repository.BrandRepository;
import com.nttdatateste.ecommerceteste.repository.CategoryRepository;
import com.nttdatateste.ecommerceteste.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    public List<Item> getItemsByCategoryIds(List<Long> categoryIds) {
        return itemRepository.findItemsByCategories(categoryIds);
    }
    public List<Item> getItemsByBrandId(Long brandId) {
        return itemRepository.findItemsByBrand(brandId);
    }




    @Transactional
    public Item addItem(Item item, List<Long> categoryIds, Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new BrandNotFoundException(brandId));
        item.setBrand(brand);

        Item addedItem = itemRepository.save(item);

        List<Category> categories = StreamSupport.stream(categoryRepository.findAllById(categoryIds).spliterator(), false)
                .collect(Collectors.toList());

        addedItem.setCategories(categories);





        return itemRepository.save(addedItem);
    }


    public List<Item> getItems() {
        return StreamSupport
                .stream(itemRepository.findAll().spliterator(), false )
                .collect(Collectors.toList());
    }

    public Item getItem(Long id) {
        return itemRepository.findById(id).orElseThrow( () -> new ItemNotFoundException(id));
    }

    public Item deleteItem(Long id) {
        Item item = getItem(id);
        itemRepository.delete(item);
        return item;
    }

    @Transactional
    public Item editItem(Long id, Item item) {
        Item itemToEdit = getItem(id);



        if (item.getName() != null) {
            itemToEdit.setName(item.getName());
        }
        if (item.getShortDescription() != null) {
            itemToEdit.setShortDescription(item.getShortDescription());
        }
        if (item.getLongDescription() != null) {
            itemToEdit.setLongDescription(item.getLongDescription());
        }
        if (item.getImageUrl() != null) {
            itemToEdit.setImageUrl(item.getImageUrl());
        }
        if (item.getStockQuantity() > 0) {
            itemToEdit.setStockQuantity(item.getStockQuantity());
        }
        if (item.getSalePrice() > 0) {
            itemToEdit.setSalePrice(item.getSalePrice());
        }
        if (item.getCostPrice() > 0) {
            itemToEdit.setCostPrice(item.getCostPrice());
        }
        if (item.getDiscount() > 0.0) {
            itemToEdit.setDiscount(item.getDiscount());
        }

        return itemToEdit;
    }
}
