package itu.services;

import itu.entity.Statistique;
import itu.repository.UtilisateurRepository;
import itu.repository.VoitureRepository;
import itu.repository.nosql.AnnonceRepository;
import org.springframework.stereotype.Service;

@Service
public class StatServices {
    private final UtilisateurRepository utilisateurRepository;
    private final VoitureRepository voitureRepository;

    public StatServices(UtilisateurRepository utilisateurRepository,VoitureRepository voitureRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.voitureRepository = voitureRepository;
    }

    public void getStat(){
        Statistique ret = new Statistique();
        ret.setUtilisateur(utilisateurRepository.count());
        ret.setAnnonce(voitureRepository.countByEtat());
    }
}
