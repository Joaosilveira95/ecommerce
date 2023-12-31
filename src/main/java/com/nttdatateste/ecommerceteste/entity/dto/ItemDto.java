package com.nttdatateste.ecommerceteste.entity.dto;

import com.nttdatateste.ecommerceteste.entity.Item;
import com.nttdatateste.ecommerceteste.entity.Category;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ItemDto {
    private Long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;
    private int stockQuantity;
    private double salePrice;
    private double costPrice;
    private double discount;

    private BrandDto brand;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    private List<Long> categoryIds;






    public static ItemDto from(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setShortDescription(item.getShortDescription());
        itemDto.setLongDescription(item.getLongDescription());
        itemDto.setImageUrl(item.getImageUrl());
        itemDto.setStockQuantity(item.getStockQuantity());
        itemDto.setSalePrice(item.getSalePrice());
        itemDto.setCostPrice(item.getCostPrice());
        itemDto.setDiscount(item.getDiscount());
        itemDto.setCategoryIds(item.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
        itemDto.setBrand(BrandDto.from(item.getBrand()));
        itemDto.setCreatedDate(item.getCreatedDate());
        itemDto.setUpdatedDate(item.getUpdatedDate());

        return itemDto;
    }
}