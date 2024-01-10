package itu.services;

import itu.repository.DetailRepository;
import itu.repository.VoitureRepository;
import org.springframework.stereotype.Service;

@Service
public class VoitureServices {
    private final VoitureRepository voitureRepository;
    private final DetailRepository detailRepository;

    public VoitureServices(VoitureRepository voitureRepository, DetailRepository detailRepository) {
        this.voitureRepository = voitureRepository;
        this.detailRepository = detailRepository;
    }

    public void getAnnonceAttente(){

    }
}
