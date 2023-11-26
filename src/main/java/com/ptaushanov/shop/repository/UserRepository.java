package com.ptaushanov.shop.repository;

import com.ptaushanov.shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String email);

    boolean existsByUsername(String admin);


    Page<User> findAll(Specification<User> specification, Pageable pageable);
}
