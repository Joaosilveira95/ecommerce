package com.nttdatateste.ecommerceteste.repository;

import com.nttdatateste.ecommerceteste.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    @Query("SELECT DISTINCT i FROM Item i " +
            "JOIN i.categories c " +
            "WHERE c.id IN :categoryIds")
    List<Item> findItemsByCategories(@Param("categoryIds") List<Long> categoryIds);

    @Query("SELECT i FROM Item i WHERE i.brand.id = :brandId")
    List<Item> findItemsByBrand(@Param("brandId") Long brandId);

    @Query("SELECT p FROM Item p WHERE p.name LIKE %:name%")
    public Iterable<Item> searchByNameLike(@Param("name") String name);

    List<Item> findByNameContainingOrShortDescriptionContainingOrLongDescriptionContaining(String nameKeyword, String shortDescKeyword, String longDescKeyword);
}






