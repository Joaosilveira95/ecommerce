package com.nttdatateste.ecommerceteste.entity.dto;

import com.nttdatateste.ecommerceteste.entity.Category;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class CategoryDto {
        private Long id;
        private String name;
        @CreationTimestamp
        private LocalDateTime createdDate;

        @UpdateTimestamp
        private LocalDateTime updatedDate;

        public static com.nttdatateste.ecommerceteste.entity.dto.CategoryDto from(Category category) {
            com.nttdatateste.ecommerceteste.entity.dto.CategoryDto categoryDto = new com.nttdatateste.ecommerceteste.entity.dto.CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setCreatedDate(category.getCreatedDate());
            categoryDto.setUpdatedDate(category.getUpdatedDate());

            return categoryDto;
        }
    }

