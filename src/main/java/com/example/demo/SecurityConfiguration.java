package com.example.demo;


import com.example.demo.twofactor.CustomWebAuthenticationDetailsSource;
import com.example.demo.twofactor.TwoFactorAuthenticationProvider;
import com.example.demo.twofactor.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    public SecurityConfiguration (CustomWebAuthenticationDetailsSource authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    @Bean
    public SecurityFilterChain securityChain (HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/register")) // Added to pass test6
                .authorizeHttpRequests(
                authorizeRequests -> authorizeRequests.requestMatchers("/admin")
                        .hasRole("ADMIN").requestMatchers("/")
                        .permitAll()
                        .requestMatchers("/register")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                /*.httpBasic(Customizer.withDefaults()
        )*/.formLogin(formLogin ->
                formLogin.loginPage("/login")
                        .authenticationDetailsSource(authenticationDetailsSource)
                        .defaultSuccessUrl("/success", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
        )/*.httpBasic(Customizer.withDefaults())*/;

//        http.authorizeHttpRequests(
//                authorizeRequests -> authorizeRequests
//                        .anyRequest().permitAll());

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

//    @Bean  // Ta bort när vi har implementerat en ny UserDetailService lektion6
//    public InMemoryUserDetailsManager userDetailsService () {
//
//        var userDetailsService = new InMemoryUserDetailsManager();
//        var user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//
//        var admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        userDetailsService.createUser(user);
//        userDetailsService.createUser(admin);
//
//        return userDetailsService;
//
//    }

    // Skapa två till @Bean.. AuthenticationManager o DaoAuthenticationProvider

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider (UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserRepository userRepository) {

        final TwoFactorAuthenticationProvider authProvider = new TwoFactorAuthenticationProvider(userRepository, userDetailsService, passwordEncoder);

        return authProvider;

    }

    @Bean
    public AuthenticationManager authManager (HttpSecurity http, DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {

        AuthenticationManager manager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(daoAuthenticationProvider)
                .build();

        return manager;
    }

}
