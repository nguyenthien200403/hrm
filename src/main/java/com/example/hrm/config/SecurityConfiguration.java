package com.example.hrm.config;

import com.example.hrm.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         http
            .csrf(AbstractHttpConfigurer::disable)
             .authorizeHttpRequests(auth -> auth
                 // Public endpoints
                 .requestMatchers("/login","/employee/fill-info/**","/email-verify", "/admin/employees/new", "/admin/employee/detail").permitAll()

                 // Role-based endpoints
                 .requestMatchers("/admin/recruitment").hasRole("admin")
                 .requestMatchers("/auth/admin/**").hasRole("employee")

                 // All other endpoints require authentication
                 .anyRequest().authenticated()
             )
            .sessionManagement(sees -> sees.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
    }
}
