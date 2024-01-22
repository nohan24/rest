package itu.services;

import itu.entity.Statistique;
import itu.entity.sql.StatAnnonce;
import itu.repository.StatAnnonceRepo;
import itu.repository.UtilisateurRepository;
import itu.repository.VenteRepo;
import itu.repository.VoitureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<StatAnnonce> stats = statAnnonceRepo.findAll();
        double total = StatAnnonce.somme(stats);
        for(StatAnnonce s : stats){
            s.setTotal(s.getTotal() * 100 / total);
        }
        ret.setAnnonces(stats);
        return ret;
    }
}
