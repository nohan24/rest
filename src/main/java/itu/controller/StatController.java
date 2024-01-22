package itu.controller;

import itu.entity.Statistique;
import itu.entity.sql.VenteView;
import itu.services.StatServices;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/statistiques/ventes")
    public List<VenteView> ventes(@RequestParam(name = "year")int year){
        return statServices.getVentes(year);
    }
}
