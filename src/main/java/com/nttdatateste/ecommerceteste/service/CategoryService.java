package com.nttdatateste.ecommerceteste.service;

import com.nttdatateste.ecommerceteste.entity.Category;
import com.nttdatateste.ecommerceteste.entity.exception.CategoryNotFoundException;
import com.nttdatateste.ecommerceteste.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getCategories() {
        return StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public Category deleteCategory(Long id) {
        Category category = getCategory(id);
        categoryRepository.delete(category);
        return category;
    }

    @Transactional
    public Category editCategory(Long id, Category category) {
        Category categoryToEdit = getCategory(id);

        if (category.getName() != null) {
            categoryToEdit.setName(category.getName());
        }

        LocalDateTime currentUpdateDate = categoryToEdit.getUpdatedDate();
        if (currentUpdateDate == null) {
            categoryToEdit.setUpdatedDate(categoryToEdit.getCreatedDate());
        } else {
            categoryToEdit.setUpdatedDate(category.getUpdatedDate());
        }


        return categoryToEdit;
    }
}
