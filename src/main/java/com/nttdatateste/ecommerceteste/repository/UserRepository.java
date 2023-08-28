package com.nttdatateste.ecommerceteste.repository;

import com.nttdatateste.ecommerceteste.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

	@Query("SELECT p FROM User p WHERE p.name LIKE %:name%")
	public Iterable<User> searchByNameLike(@Param("name") String name);

	Optional<User> findByEmail(String email);

}