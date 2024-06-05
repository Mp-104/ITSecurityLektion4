package com.example.demo.web;

import com.example.demo.UserDTO;
import com.example.demo.twofactor.QRCode;
import com.example.demo.twofactor.User;
import com.example.demo.twofactor.UserRepository;
import jakarta.validation.Valid;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class ThymeleafController {

    //com.example.demo.twofactor.User user = new com.example.demo.twofactor.User();

    // Funkar för att dessa är annoterade med @Bean
    private final PasswordEncoder encoder;

    //private final InMemoryUserDetailsManager manager; //Ingen manager..

    private final UserRepository userRepository;

    private final QRCode qrCode;

    public ThymeleafController (PasswordEncoder encoder /*InMemoryUserDetailsManager manager*/, QRCode qrCode, UserRepository userRepository) {
        this.encoder = encoder;
        //this.manager = manager; // Still used? Not in pdf.. not used.
        this.qrCode = qrCode;
        this.userRepository = userRepository;
    }

//    public ThymeleafController () {
//
//    }

    @GetMapping("/")
    public String homePage () {
        return "index";
    }

    @GetMapping("/register" /*"/login"*/)
    public String registerPage (Model model) {

        model.addAttribute("user", new UserDTO());

        //com.example.demo.twofactor.User user = new com.example.demo.twofactor.User();

//        List<String> listProfession = Arrays.asList("default","Developer", "Tester", "Architect");
//        model.addAttribute("listProfession", listProfession);
//
//        model.addAttribute("profession", "profession");

        return "registration";

    }

    @PostMapping("/register" /*"/login"*/)
    public String submitForm (@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult , Model model) {



        if (bindingResult.hasErrors()) {
            return "registration";
        } else {

//            UserDetails toRegister = User.builder()
//                    .password(encoder.encode(HtmlUtils.htmlEscape(userDTO.getPassword())))
//                    .username(userDTO.getEmail())
//                    .roles("USER")
//                    .build();
//
//            manager.createUser(toRegister);


            com.example.demo.twofactor.User user = new com.example.demo.twofactor.User();

            user.setEmail(userDTO.getEmail());
            user.setPassword(encoder.encode(userDTO.getPassword()));
            user.setSecret(Base32.random());
            user.setRole("USER");

            userRepository.save(user);

            //userRepository.findByEmail(user.getEmail());

            model.addAttribute("qrcode", qrCode.dataUrl(user));

            model.addAttribute("user", user);

            System.out.println("user: " + userDTO.getEmail() + userDTO.getPassword());

            return "qrcode";
            //return "register_success";


        }
//        this.encoder = passwordEncoder;
//        this.manager = inMemoryUserDetailsManager;

    }

    @GetMapping("/login")
    public String loginPage () {

        return "login";
    }

//    @PostMapping("/login")
//    public String login (User user, Model model) {
//
//        model.addAttribute("user", user);
//
//        return "success";
//
//    }

    @GetMapping("/success")
    public String successLogin (Model model/*Model model, UserDTO user*/) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // If you need more details about the user

        User userDetails = (User) authentication.getPrincipal();

        model.addAttribute("user", userDetails);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = "";
//
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            email = userDetails.getUsername();
//        }
//
////        System.out.println("success user.email " + user.getEmail());
//
//        User user = userRepository.findByEmail(email);
//        System.out.println(user.getEmail());
//
       model.addAttribute("user", userDetails);

        return "success";
    }

}
