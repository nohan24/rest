package itu.config;

import itu.auth.JwtAuthorizationFilter;
import itu.services.CustomUserDetailServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailServices userDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    public SecurityConfig(CustomUserDetailServices customUserDetailsService, JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.userDetailsService = customUserDetailsService;

        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(request -> request.requestMatchers("/auth/**").permitAll())
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST,"/details/**").hasRole("ADMIN"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.DELETE,"/details/**").hasRole("ADMIN"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, "/details/**").permitAll())
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET,"/user/annonces").permitAll())
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET,"/user/{id}/annonces").permitAll())
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST,"/user/**").authenticated())
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.DELETE,"/user/**").authenticated())
                    .authorizeHttpRequests(request -> request.requestMatchers("/images/**").permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}