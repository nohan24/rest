package itu.controller;

import itu.repository.nosql.DetailRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnonceController {
    private final DetailRepository detailRepository;

    public AnnonceController(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }


}
