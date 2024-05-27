package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityChain (HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests.requestMatchers("/admin").hasRole("ADMIN").anyRequest().authenticated()).httpBasic(Customizer.withDefaults()
        ).formLogin(formLogin ->
                formLogin
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
        ).httpBasic(Customizer.withDefaults());

//        http.authorizeHttpRequests(
//                authorizeRequests -> authorizeRequests
//                        .anyRequest().permitAll());

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService () {

        var userDetailsService = new InMemoryUserDetailsManager();
        var user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        var admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        userDetailsService.createUser(user);
        userDetailsService.createUser(admin);

        return userDetailsService;

    }

}
