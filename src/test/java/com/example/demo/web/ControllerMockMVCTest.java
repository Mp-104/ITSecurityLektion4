package com.example.demo.web;

import com.example.demo.MyBean;
import com.example.demo.MyComponent;
import com.example.demo.MyService;
import com.example.demo.SecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(Controller.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerMockMVCTest {

    @Autowired
    MockMvc mockMvc;

//    @MockBean
//    private MyService myService;
//
//    @MockBean
//    private MyComponent myComponent;
//
//    @MockBean
//    private MyBean myBean;
    // No longer need to mock beans, but needed when used @WebMvcTest(Controller.class)



//    @MockBean
//    private UserDetailsService userDetailsService;

    // Including below code will reverse test results...
//    @MockBean
//    private SecurityFilterChain securityFilterChain;

//    @BeforeEach
//    public void setup() {
//        UserDetails user = User.withUsername("user")
//                .password("{noop}password") // {noop} indicates no password encoding
//                .roles("USER")
//                .build();
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(user);
//        // Mock the userDetailsService to use the in-memory user manager
//        org.mockito.BDDMockito.given(userDetailsService.loadUserByUsername("user"))
//                .willReturn(user);
//    }

    @Test
    public void test1a () throws Exception {

        // No authenication, can't access to home page, get error 401
        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/"))
                .andExpect(status().is(401));
                //.andExpect(content().string(containsString("Welcome to my site")));

    }

    @Test
    public void test1b () throws Exception {

        // With provided credentials, it works
        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/").with(httpBasic("user", "password")))
                .andExpect(status().isOk());
        //.andExpect(content().string(containsString("Welcome to my site")));

    }

    @Test
    public void test1c () throws Exception {

        // Even admin credentials
        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/").with(httpBasic("admin", "admin")))
                .andExpect(status().isOk());
        //.andExpect(content().string(containsString("Welcome to my site")));

    }

    @Test
    public void test2 () throws Exception {

        // Refers to /login page, which does not require credentials, .with(httpBasic("user", "password")) can be removed
        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/login").with(httpBasic("user","password")))
                .andExpect(status().isOk());
                //.andExpect(content().string(containsString("Welcome to my site")));

    }
    @Test
    public void test2b () throws Exception {

        // Refers to /login page, which does not require credentials..
        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/login"))
                .andExpect(status().isOk());
        //.andExpect(content().string(containsString("Welcome to my site")));

    }

    @Test
    public void test3 () throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/").with(httpBasic("user","password")))
                .andExpect(status().isOk()).andExpect(content().string(containsString("Welcome to my site!"))).andReturn();
        //.andExpect(content().string(containsString("Welcome to my site")));

    }

    @Test
    public void test3b () throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/").with(httpBasic("admin","admin")))
                .andExpect(status().isOk()).andExpect(content().string(containsString("Welcome to my site!"))).andReturn();
        //.andExpect(content().string(containsString("Welcome to my site")));

    }

    @Test
    public void test4 () throws Exception {

        // Trying to log in with user, should return 403, forbidden due to wrong credentials
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin").with(httpBasic("user","password")))
                .andExpect(status().is(403));
                //.andExpect(content().string(containsString("Welcome to my site!"))).andReturn();
        //.andExpect(content().string(containsString("Welcome to my site")));

    }

    @Test
    public void test4b () throws Exception {

        // Trying to log in with user, should return 401, not autherised due to no credentials
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(status().is(401));
        //.andExpect(content().string(containsString("Welcome to my site!"))).andReturn();
        //.andExpect(content().string(containsString("Welcome to my site")));

    }

    @Test
    public void test5 () throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin").with(httpBasic("admin","admin")))
                .andExpect(status().is(200)).andExpect(content().string(containsString("Admin")));
        //.andExpect(content().string(containsString("Welcome to my site!"))).andReturn();
        //.andExpect(content().string(containsString("Welcome to my site")));

    }



}
