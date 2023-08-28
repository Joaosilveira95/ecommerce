package com.nttdatateste.ecommerceteste.repository;


import com.nttdatateste.ecommerceteste.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>, PagingAndSortingRepository<Category, Long> {


	@Query("SELECT p FROM Category p WHERE p.name LIKE %:name%")
	public Iterable<Category> searchByNameLike(@Param("name") String name);

}