package itu.services;

import itu.entity.Statistique;
import itu.entity.sql.StatAnnonce;
import itu.entity.sql.VenteView;
import itu.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StatServices {
    final VenteViewRepo venteViewRepo;
    private final VenteRepo venteRepo;
    private final UtilisateurRepository utilisateurRepository;
    private final VoitureRepository voitureRepository;
    private final StatAnnonceRepo statAnnonceRepo;

    public StatServices(VenteViewRepo venteViewRepo, VenteRepo venteRepo, UtilisateurRepository utilisateurRepository, VoitureRepository voitureRepository, StatAnnonceRepo statAnnonceRepo) {
        this.venteViewRepo = venteViewRepo;
        this.venteRepo = venteRepo;
        this.utilisateurRepository = utilisateurRepository;
        this.voitureRepository = voitureRepository;
        this.statAnnonceRepo = statAnnonceRepo;
    }

    public Statistique getStat(){
        int vente = (int) venteRepo.count();
        Statistique ret = new Statistique();
        ret.setUtilisateur(utilisateurRepository.countByRoles("USER"));
        ret.setAnnonce(voitureRepository.countByEtat());
        ret.setVente(vente);
        int countvoiture = (int) voitureRepository.count();
        if(countvoiture != 0){
            ret.setAnnonce_vente((double) (vente * 100) / voitureRepository.count());
        }else{
            ret.setAnnonce_vente(0);
        }
        List<StatAnnonce> stats = statAnnonceRepo.findAll();
        ret.setTotalprix(venteRepo.sum());
        double total = StatAnnonce.somme(stats);
        for(StatAnnonce s : stats){
            if(total != 0){
                s.setTotal(s.getTotal() * 100 / total);
            }else{
                s.setTotal(0);
            }
        }
        ret.setAnnonces(stats);
        return ret;
    }

    public List<VenteView> getVentes(int year){
        return venteViewRepo.findAllByYear(year);
    }
}
