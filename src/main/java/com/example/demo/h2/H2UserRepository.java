package com.example.demo.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface H2UserRepository extends JpaRepository<H2User, Long>, CrudRepository<H2User, Long> {
    //Denna query använder named parameters och är inte känslig för SQL-injection
    @Query(value = "SELECT * FROM users WHERE username = :username AND password = :password", nativeQuery = true)
    H2User loginUser(@Param("username") String username, @Param("password") String password);
}

