package com.nttdatateste.ecommerceteste.controller;


import com.nttdatateste.ecommerceteste.entity.Item;
import com.nttdatateste.ecommerceteste.entity.dto.ItemDto;
import com.nttdatateste.ecommerceteste.repository.ItemRepository;
import com.nttdatateste.ecommerceteste.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemService itemService, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }
    @Operation(summary = "Add New Item", description = "Add a new Item to DB")
    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestBody final ItemDto itemDto) {

        Item item = Item.from(itemDto);
        Item addedItem = itemService.addItem(item, itemDto.getCategoryIds(), itemDto.getBrand().getId());

        return new ResponseEntity<>(ItemDto.from(addedItem), HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Items", description = "Get All Items in DB")
    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems() {
        List<Item> items = itemService.getItems();
        List<ItemDto> itemsDto = items.stream().map(ItemDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(itemsDto, HttpStatus.OK);
    }

    @Operation(summary = "Get Item By ID", description = "Get Item from ID in DB")
    @GetMapping(value = "{id}")
    public ResponseEntity<ItemDto> getItem(@PathVariable final Long id) {
        Item item = itemService.getItem(id);
        return new ResponseEntity<>(ItemDto.from(item), HttpStatus.OK);
    }

    @Operation(summary = "Get Items By Name", description = "Get Items By Name or Partial Name")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ItemDto>> searchItemsByName(@PathVariable String name) {
        List<Item> items = (List<Item>) itemRepository.searchByNameLike(name);
        List<ItemDto> itemDtos = items.stream().map(ItemDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }

    @Operation(summary = "Get Items By Keyword", description = "Get Items By Keyword in name or shortDescription or longDescription")
    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItemsByKeyword(@RequestParam String keyword) {
        List<Item> items = (List<Item>) itemRepository.findByNameContainingOrShortDescriptionContainingOrLongDescriptionContaining(keyword, keyword, keyword);
        List<ItemDto> itemDtos = items.stream().map(ItemDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
        }


    @Operation(summary = "Get Items By Category", description = "Get Items By Category")
    @GetMapping("/category")
    public ResponseEntity<List<ItemDto>> getItemsByCategory(@RequestParam List<Long> categoryIds) {
        List<Item> items = itemService.getItemsByCategoryIds(categoryIds);
        List<ItemDto> itemDtos = items.stream().map(ItemDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }
    @Operation(summary = "Get Items By Brand", description = "Get Items By Brand")
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<ItemDto>> getItemsByBrand(@PathVariable Long brandId) {
        List<Item> items = itemService.getItemsByBrandId(brandId);
        List<ItemDto> itemDtos = items.stream().map(ItemDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }

    @Operation(summary = "Delete Item by ID", description = "Delete the Item from ID in DB")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<ItemDto> deleteItem(@PathVariable final Long id) {
        Item item = itemService.deleteItem(id);
        return new ResponseEntity<>(ItemDto.from(item), HttpStatus.OK);
    }

    @Operation(summary = "Update Item by ID", description = "Update the Item in DB")
    @PutMapping(value = "{id}")
    public ResponseEntity<ItemDto> editItem(@PathVariable final Long id, @RequestBody final ItemDto itemDto) {
        Item editedItem = itemService.editItem(id, Item.from(itemDto));
        return new ResponseEntity<>(ItemDto.from(editedItem), HttpStatus.OK);

    }
}
