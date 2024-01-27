package itu.config;

import itu.auth.JwtAuthorizationFilter;
import itu.services.CustomUserDetailServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;

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
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.PUT, "/voitures/**").authenticated())
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST,"/marques/**", "/equipements/**", "/modeles/**", "/transmissions/**", "/carburants/**","/categories/**","/commissions/**").hasRole("ADMIN"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.DELETE,"/marques/**", "/equipements/**", "/modeles/**", "/transmissions/**", "/carburants/**","/categories/**").hasRole("ADMIN"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.PUT, "/commissions").hasRole("ADMIN"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, "/commissions").hasRole("ADMIN"))
                    .authorizeHttpRequests(request -> request.requestMatchers("/validation/**").hasRole("ADMIN"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, "/marques/**", "/equipements/**", "/modeles/**", "/transmissions/**", "/carburants/**","/categories/**").permitAll())
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST,"/user/**").hasRole("USER"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.DELETE,"/user/**").hasRole("USER"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET,"/user/**").hasRole("USER"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET,"/annonces/**").permitAll())
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST,"/annonces/filtrer").permitAll())
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, "/annonces/favoris").hasRole("USER"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.PUT, "/annonces/**").hasRole("USER"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST,"/annonces/**").hasRole("USER"))
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.DELETE,"/annonces/**").hasRole("USER"))
                    .authorizeHttpRequests(request -> request.requestMatchers("/statistiques/**").hasRole("ADMIN"))
                    .authorizeHttpRequests(request -> request.requestMatchers("/token").hasRole("USER"))
                    .authorizeHttpRequests(request -> request.requestMatchers("/images/**").permitAll())
                    .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, "/tokene").permitAll())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling((e) -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
            return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Authentication failed: " + exception.getMessage());
            response.getWriter().flush();
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access denied: " + accessDeniedException.getMessage());
            response.getWriter().flush();
        };
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