package itu.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, ObjectMapper mapper) {
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        Map<String, Object> errorDetails = new HashMap<>();

        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null ) {
                filterChain.doFilter(request, response);
                return;
            }
            Claims claims = jwtUtil.resolveClaims(request);

            if(claims != null & jwtUtil.validateClaims(claims)){

                String email = claims.getSubject();
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email,null, convertRolesToAuthorities(claims.get("roles").toString()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }catch(ExpiredJwtException e){
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("expired",e.getMessage());
            try {
                mapper.writeValue(response.getWriter(), errorDetails);
            } catch (java.io.IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e){
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details",e.getMessage());
            errorDetails.put("error", 69);
            try {
                mapper.writeValue(response.getWriter(), errorDetails);
            } catch (java.io.IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    public static Collection<? extends GrantedAuthority> convertRolesToAuthorities(String roles) {
        List<String> roleList = new ArrayList<>();
        roleList.add(roles);

        return roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.trim()))
                .collect(Collectors.toList());
    }
}