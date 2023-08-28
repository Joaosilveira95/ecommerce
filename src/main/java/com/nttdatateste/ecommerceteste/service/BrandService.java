package com.nttdatateste.ecommerceteste.service;

import com.nttdatateste.ecommerceteste.entity.Brand;
import com.nttdatateste.ecommerceteste.entity.exception.BrandNotFoundException;
import com.nttdatateste.ecommerceteste.repository.BrandRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> getBrands() {
        return StreamSupport
                .stream(brandRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Brand getBrand(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException(id));
    }

    public Brand deleteBrand(Long id) {
        Brand brand = getBrand(id);
        brandRepository.delete(brand);
        return brand;
    }

    @Transactional
    public Brand editBrand(Long id, Brand brand) {
        Brand brandToEdit = getBrand(id);

        if (brand.getName() != null) {
            brandToEdit.setName(brand.getName());
        }

        LocalDateTime currentUpdateDate = brandToEdit.getUpdatedDate();
        if (currentUpdateDate == null) {
            brandToEdit.setUpdatedDate(brandToEdit.getCreatedDate());
        } else {
            brandToEdit.setUpdatedDate(brand.getUpdatedDate());
        }


        return brandToEdit;
    }
}

