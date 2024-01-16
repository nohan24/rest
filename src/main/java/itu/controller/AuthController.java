package itu.controller;

import itu.auth.ErrorRes;
import itu.auth.JwtUtil;
import itu.auth.LoginRes;
import itu.entity.sql.Utilisateur;
import itu.services.RegisterServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RegisterServices registerServices;
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, RegisterServices registerServices) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.registerServices = registerServices;
    }

    @PostMapping("/auth/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity login(String email, String password){
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()).get(0);
            String mail = authentication.getName();
            Utilisateur user = new Utilisateur();
            user.setEmail(mail);
            user.setRoles(role);
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(email,token);
            return ResponseEntity.ok(loginRes);
        } catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity register(String email, String password, int genre, String date_naissance, String username){
        date_naissance = date_naissance.replaceAll("-","/");
        Utilisateur u = registerServices.register(email, password, date_naissance, genre, username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtUtil.createToken(u);
        LoginRes loginRes = new LoginRes(email,token);
        return ResponseEntity.ok(loginRes);
    }
}
