package com.example.demo.h2;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitialiser {

    @Autowired
    private H2UserRepository repository;

    @PostConstruct
    public void init () {

        H2User admin = new H2User();

        admin.setAge(15);
        admin.setRole("ADMIN");
        admin.setUsername("Max");
        admin.setLastName("Power");
        admin.setPassword("test");

        repository.save(admin);

    }

}
