package itu.services;

import itu.entity.Statistique;
import itu.repository.StatAnnonceRepo;
import itu.repository.UtilisateurRepository;
import itu.repository.VenteRepo;
import itu.repository.VoitureRepository;
import org.springframework.stereotype.Service;
@Service
public class StatServices {
    private final VenteRepo venteRepo;
    private final UtilisateurRepository utilisateurRepository;
    private final VoitureRepository voitureRepository;
    private final StatAnnonceRepo statAnnonceRepo;

    public StatServices(VenteRepo venteRepo, UtilisateurRepository utilisateurRepository, VoitureRepository voitureRepository, StatAnnonceRepo statAnnonceRepo) {
        this.venteRepo = venteRepo;
        this.utilisateurRepository = utilisateurRepository;
        this.voitureRepository = voitureRepository;
        this.statAnnonceRepo = statAnnonceRepo;
    }

    public Statistique getStat(){
        int vente = (int) venteRepo.count();
        Statistique ret = new Statistique();
        ret.setUtilisateur((int) utilisateurRepository.count());
        ret.setAnnonce(voitureRepository.countByEtat());
        ret.setVente(vente);
        ret.setAnnonce_vente((double) (vente * 100) / voitureRepository.count());
        ret.setAnnonces(statAnnonceRepo.findAll());
        return ret;
    }
}
