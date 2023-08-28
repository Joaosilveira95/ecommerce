package com.nttdatateste.ecommerceteste.controller;

import com.nttdatateste.ecommerceteste.entity.Brand;
import com.nttdatateste.ecommerceteste.entity.dto.BrandDto;
import com.nttdatateste.ecommerceteste.repository.BrandRepository;
import com.nttdatateste.ecommerceteste.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/brands")
public class BrandController {

	private final BrandService brandService;
	private final BrandRepository brandRepository;

	@Autowired
	public BrandController(BrandService brandService, BrandRepository brandRepository) {this.brandService = brandService;
		this.brandRepository = brandRepository;
	}

	@Operation(summary = "Add New Brand", description = "Add a new Brand to DB")
	@PostMapping
	public ResponseEntity<BrandDto> addBrand(@RequestBody final BrandDto brandDto) {
		Brand brand = brandService.addBrand(Brand.from(brandDto));
		return new ResponseEntity<>(BrandDto.from(brand), HttpStatus.CREATED);
	}

	@Operation(summary = "Get All Brands", description = "Get All Brands in DB")
	@GetMapping
	public ResponseEntity<List<BrandDto>> getBrands() {
		List<Brand> brands = brandService.getBrands();
		List<BrandDto> brandsDto = brands.stream().map(BrandDto::from).collect(Collectors.toList());
		return new ResponseEntity<>(brandsDto, HttpStatus.OK);
	}

	@Operation(summary = "Get Brand By ID", description = "Get Brand from ID in DB")
	@GetMapping(value = "{id}")
	public ResponseEntity<BrandDto> getBrand(@PathVariable final Long id) {
		Brand brand = brandService.getBrand(id);
		return new ResponseEntity<>(BrandDto.from(brand), HttpStatus.OK);
	}

	@Operation(summary = "Get Brands By Name", description = "Get Brands By Name or Partial Name")
	@GetMapping("/name/{name}")
	public ResponseEntity<List<BrandDto>> searchBrandsByName(@PathVariable String name) {
		List<Brand> brands = (List<Brand>) brandRepository.searchByNameLike(name);
		List<BrandDto> brandDtos = brands.stream().map(BrandDto::from).collect(Collectors.toList());
		return new ResponseEntity<>(brandDtos, HttpStatus.OK);
	}
	@Operation(summary = "Delete Brand by ID", description = "Delete the Brand from ID in DB")
	@DeleteMapping(value = "{id}")
	public ResponseEntity<BrandDto> deleteBrand(@PathVariable final Long id) {
		Brand brand = brandService.deleteBrand(id);
		return new ResponseEntity<>(BrandDto.from(brand), HttpStatus.OK);
	}
	@Operation(summary = "Update Brand by ID", description = "Update the Brand in DB")
	@PutMapping(value = "{id}")
	public ResponseEntity<BrandDto> editBrand(@PathVariable final Long id, @RequestBody final BrandDto brandDto) {
		Brand editedBrand = brandService.editBrand(id, Brand.from(brandDto));
		return new ResponseEntity<>(BrandDto.from(editedBrand), HttpStatus.OK);

	}


}