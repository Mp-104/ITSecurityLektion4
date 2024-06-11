package com.example.demo.web;

import com.example.demo.UserDTO;
import com.example.demo.h2.H2User;
import com.example.demo.h2.H2UserRepository;
import com.example.demo.h2.UserServiceImpl;
import com.example.demo.mask.Masking;
import com.example.demo.twofactor.QRCode;
import com.example.demo.twofactor.User;
import com.example.demo.twofactor.UserRepository;
import jakarta.validation.Valid;
import org.jboss.aerogear.security.otp.api.Base32;
import org.slf4j.LoggerFactory;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ThymeleafController {

    //com.example.demo.twofactor.User user = new com.example.demo.twofactor.User();

    // Funkar för att dessa är annoterade med @Bean
    private final PasswordEncoder encoder;

    //private final InMemoryUserDetailsManager manager; //Ingen manager..

    private final UserRepository userRepository;

    private final H2UserRepository repository;

    private final QRCode qrCode;

    private final UserServiceImpl userService;

    private static final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);

    public ThymeleafController (UserServiceImpl userService, PasswordEncoder encoder /*InMemoryUserDetailsManager manager*/, QRCode qrCode, UserRepository userRepository, H2UserRepository repository) {
        this.encoder = encoder;
        //this.manager = manager; // Still used? Not in pdf.. not used.
        this.qrCode = qrCode;
        this.userRepository = userRepository;
        this.repository = repository;
        this.userService=userService;
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



            user.setEmail(Masking.maskEmail(userDTO.getEmail()));
            user.setPassword(encoder.encode(userDTO.getPassword()));
            user.setSecret(Base32.random());
            user.setRole("USER");
            userRepository.save(user);

            H2User user1 = new H2User();
            user1.setUsername(user.getEmail());
            user1.setPassword(user.getPassword());

            userService.saveUser(user1);
//            H2User user = new H2User();
//            user.setUsername(userDTO.getEmail());
//            user.setPassword(encoder.encode(userDTO.getPassword()));
//            user.setRole("USER");
//
//            repository.save(user);

            //userRepository.findByEmail(user.getEmail());

            //model.addAttribute("qrcode", qrCode.dataUrl(user));

            model.addAttribute("users", user);

            System.out.println("user: " + userDTO.getEmail() + userDTO.getPassword());

            return "register_success";
            //return "qrcode";
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
