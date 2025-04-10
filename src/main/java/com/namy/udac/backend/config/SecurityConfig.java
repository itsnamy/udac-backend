package com.namy.udac.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(customizer -> customizer.disable())
            .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/login").permitAll()  // Allow public access to /auth/login
                                .requestMatchers("/createUser").permitAll()  // Allow public access to /createUser
                                .requestMatchers("/getAllUser").permitAll()  
                                //.requestMatchers("/getAllUser").hasRole("ADMIN") // Allow only ADMIN to access /
                                .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .formLogin(formLogin -> formLogin.disable())
            //.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
            //.httpBasic(Customizer.withDefaults())
            //.sessionManagement(session -> session
                                //.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            ;
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        
        UserDetails user1 = User
            .withUsername("student1")
            .password(passwordEncoder().encode("student123")) // Use password encoder
            .roles("STUDENT")
            .build();

        UserDetails user2 = User
            .withUsername("admin1")
            .password(passwordEncoder().encode("admin123"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Secure password encoding
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
