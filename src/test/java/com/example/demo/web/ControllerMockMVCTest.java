package com.example.demo.web;

import com.example.demo.MyBean;
import com.example.demo.MyComponent;
import com.example.demo.MyService;
import com.example.demo.SecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

@WebMvcTest(Controller.class)
public class ControllerMockMVCTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MyService myService;

    @MockBean
    private MyComponent myComponent;

    @MockBean
    private MyBean myBean;

    @MockBean
    private UserDetailsService userDetailsService;

    // Including below code will reverse test results...
//    @MockBean
//    private SecurityFilterChain securityFilterChain;

    @BeforeEach
    public void setup() {
        UserDetails user = User.withUsername("user")
                .password("{noop}password") // {noop} indicates no password encoding
                .roles("USER")
                .build();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(user);
        // Mock the userDetailsService to use the in-memory user manager
        org.mockito.BDDMockito.given(userDetailsService.loadUserByUsername("user"))
                .willReturn(user);
    }

    @Test
    public void test1 () throws Exception {

        // No authenication, can't access to home page, get error 401
        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/"))
                .andExpect(status().is(401));
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
    public void test3 () throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/").with(httpBasic("user","password")))
                .andExpect(status().isOk()).andExpect(content().string(containsString("Welcome to my site!"))).andReturn();
        //.andExpect(content().string(containsString("Welcome to my site")));

    }



}
