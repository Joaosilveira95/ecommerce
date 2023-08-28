package com.nttdatateste.ecommerceteste.repository;


import com.nttdatateste.ecommerceteste.entity.Brand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Long>, PagingAndSortingRepository<Brand, Long> {

	@Query("SELECT p FROM Brand p WHERE p.name LIKE %:name%")
	public Iterable<Brand> searchByNameLike(@Param("name") String name);

}