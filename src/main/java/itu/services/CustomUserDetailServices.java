package itu.services;


import itu.entity.sql.Utilisateur;
import itu.repository.UtilisateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailServices implements UserDetailsService {
    private final UtilisateurRepository userRepository;

    public CustomUserDetailServices(UtilisateurRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByEmail(email);
        if(user == null)throw new UsernameNotFoundException("L'utilisateur n'a pas été trouvé.");
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .roles(user.getRoles())
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .authorities(convertRolesToAuthorities(user.getRoles()))
                        .build();
        return userDetails;
    }

    public static Collection<? extends GrantedAuthority> convertRolesToAuthorities(String roles) {
        List<String> roleList = new ArrayList<>();
        roleList.add(roles);

        return roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.trim()))
                .collect(Collectors.toList());
    }
}