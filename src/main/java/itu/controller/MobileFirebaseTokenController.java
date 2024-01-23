package itu.controller;

import itu.entity.sql.Mobile;
import itu.repository.MobileRepo;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void saveToken(String mobiletoken){
        Mobile m = new Mobile();
        m.setUserid((Integer) SecurityContextHolder.getContext().getAuthentication().getCredentials());
        m.setToken(mobiletoken);
        mobileRepo.save(m);
    }

    @DeleteMapping("/token/{token}")
    public void deleteToken(@PathVariable(name = "token")String token){
        mobileRepo.deleteByToken(token);
    }
}
