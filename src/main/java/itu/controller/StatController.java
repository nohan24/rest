package itu.controller;

import itu.entity.Statistique;
import itu.services.StatServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatController {
    private final StatServices statServices;

    public StatController(StatServices statServices) {
        this.statServices = statServices;
    }

    @GetMapping("/statistiques")
    public Statistique stat(){
        return statServices.getStat();
    }
}
