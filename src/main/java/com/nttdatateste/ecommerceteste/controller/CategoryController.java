package com.nttdatateste.ecommerceteste.controller;


import com.nttdatateste.ecommerceteste.entity.Category;
import com.nttdatateste.ecommerceteste.entity.dto.CategoryDto;
import com.nttdatateste.ecommerceteste.repository.CategoryRepository;
import com.nttdatateste.ecommerceteste.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;
	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {this.categoryService = categoryService;
		this.categoryRepository = categoryRepository;
	}
	@Operation(summary = "Add New Category", description = "Add a new Category to DB")
	@PostMapping
	public ResponseEntity<CategoryDto> addCategory(@RequestBody final CategoryDto categoryDto) {
		Category category = categoryService.addCategory(Category.from(categoryDto));
		return new ResponseEntity<>(CategoryDto.from(category), HttpStatus.CREATED);
	}
	@Operation(summary = "Get All Categories", description = "Get All Categories in DB")
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getCategories() {
		List<Category> categories = categoryService.getCategories();
		List<CategoryDto> categoriesDto = categories.stream().map(CategoryDto::from).collect(Collectors.toList());
		return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
	}
	@Operation(summary = "Get Category By ID", description = "Get Category from ID in DB")
	@GetMapping(value = "{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable final Long id) {
		Category category = categoryService.getCategory(id);
		return new ResponseEntity<>(CategoryDto.from(category), HttpStatus.OK);
	}
	@Operation(summary = "Get Categories By Name", description = "Get Categories By Name or Partial Name")
	@GetMapping("/name/{name}")
	public ResponseEntity<List<CategoryDto>> searchCategoriesByName(@PathVariable String name) {
		List<Category> categories = (List<Category>) categoryRepository.searchByNameLike(name);
		List<CategoryDto> categoryDtos = categories.stream().map(CategoryDto::from).collect(Collectors.toList());
		return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
	}
	@Operation(summary = "Delete Category by ID", description = "Delete the Category from ID in DB")
	@DeleteMapping(value = "{id}")
	public ResponseEntity<CategoryDto> deleteCategory(@PathVariable final Long id) {
		Category category = categoryService.deleteCategory(id);
		return new ResponseEntity<>(CategoryDto.from(category), HttpStatus.OK);
	}

	@Operation(summary = "Update Category by ID", description = "Update the Category in DB")
	@PutMapping(value = "{id}")
	public ResponseEntity<CategoryDto> editCategory(@PathVariable final Long id, @RequestBody final CategoryDto categoryDto) {
		Category editedCategory = categoryService.editCategory(id, Category.from(categoryDto));
		return new ResponseEntity<>(CategoryDto.from(editedCategory), HttpStatus.OK);

	}


}
