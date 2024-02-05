package itu.controller;

import itu.entity.sql.Mobile;
import itu.repository.MobileRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MobileFirebaseTokenController {
    private final MobileRepo mobileRepo;

    public MobileFirebaseTokenController(MobileRepo mobileRepo) {
        this.mobileRepo = mobileRepo;
    }

    @PostMapping("/token")
    public void saveToken(String token){
        Mobile m = new Mobile();
        m.setUserid((Integer) SecurityContextHolder.getContext().getAuthentication().getCredentials());
        m.setToken(token);
        mobileRepo.save(m);
    }

    @DeleteMapping("/token/{token}")
    @Transactional
    public void deleteToken(@PathVariable(name = "token")String token){
        mobileRepo.deleteByToken(token);
    }
}
