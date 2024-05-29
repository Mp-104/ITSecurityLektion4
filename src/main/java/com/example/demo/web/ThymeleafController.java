package com.example.demo.web;

import com.example.demo.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ThymeleafController {

    /*private final*/ PasswordEncoder encoder;

    /*private final*/ InMemoryUserDetailsManager manager;


    public ThymeleafController (PasswordEncoder encoder, InMemoryUserDetailsManager manager) {
        this.encoder = encoder;
        this.manager = manager;
    }

//    public ThymeleafController () {
//
//    }

    @GetMapping("/")
    public String homePage () {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage (Model model) {

        model.addAttribute("user", new UserDTO());

        List<String> listProfession = Arrays.asList("default","Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);

        model.addAttribute("profession", "profession");

        return "registration";

    }

    @PostMapping("/register")
    public String submitForm (@Valid @ModelAttribute("user") UserDTO user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registration";
        } else {

            UserDetails toRegister = User.builder()
                    .password(encoder.encode(user.getPassword()))
                    .username(user.getEmail())
                    .roles("USER")
                    .build();

            manager.createUser(toRegister);

            System.out.println("user: " + user.getProfession() + user.getEmail() + user.getPassword());

            return "register_success";


        }

//        this.encoder = passwordEncoder;
//        this.manager = inMemoryUserDetailsManager;



    }

}
