package com.nttdatateste.ecommerceteste.entity.dto;

import com.nttdatateste.ecommerceteste.entity.Brand;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class BrandDto {
    private Long id;
    private String name;
    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    public static BrandDto from(Brand brand) {
        BrandDto brandDto = new BrandDto();
        brandDto.setId(brand.getId());
        brandDto.setName(brand.getName());
        brandDto.setCreatedDate(brand.getCreatedDate());
        brandDto.setUpdatedDate(brand.getUpdatedDate());

        return brandDto;
    }
}
