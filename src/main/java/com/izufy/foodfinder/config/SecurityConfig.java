//package com.izufy.foodfinder.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .anyRequest().authenticated()  // Require authentication for all requests
//                )
//                .formLogin(withDefaults())         // Enable default form login
//                .httpBasic(withDefaults());        // Enable default HTTP basic authentication
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        // Define multiple users with usernames, passwords, and roles
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder.encode("user"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//        // Store users in an in-memory user details manager
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // Use BCrypt to securely hash passwords
//        return new BCryptPasswordEncoder();
//    }
//}
