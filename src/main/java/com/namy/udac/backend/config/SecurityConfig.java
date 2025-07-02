package com.namy.udac.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private JWTFilter jwtFilter; 

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(Customizer.withDefaults())
            .csrf(customizer -> customizer.disable())
            .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/**", "/public/**").permitAll()  // Allow public access to /auth/** endpoints  "/lesson/**"
                                .requestMatchers("/material/video/stream/**").permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ADMIN") 
                                .requestMatchers("/student/**").hasAuthority("STUDENT")
                                .requestMatchers("/common/**").hasAnyAuthority("ADMIN", "STUDENT")
                                .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Creates a DAO-based auth provider (DAO = Data Access Object) and use userdetailsService to load user details from db
        provider.setPasswordEncoder(new BCryptPasswordEncoder(4)); // Set the password encoder to use BCrypt with a strength of 4 (4 rounds of hashing)
        provider.setUserDetailsService(userDetailsService); // Tells the provider to use your custom implementation of UserDetailsService to load user data.

        return provider;
    }
// Manage authentication to authenticate users during login (needed when have more than 1 auhtenctication flow. in this case basic auth and bearer tokern auth)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Returns the authentication manager from the provided configuration
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("https://udac-frontend.onrender.com");
        config.addAllowedOriginPattern("http://localhost:3000"); // Frontend origin
        config.addAllowedMethod("*"); // Allow all HTTP methods
        config.addAllowedHeader("*"); // Allow all headers
        config.setAllowCredentials(true); // Allow cookies and Authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply to all routes
        return source;
    }
}
