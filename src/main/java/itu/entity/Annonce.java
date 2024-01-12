package itu.entity;

import itu.entity.nosql.Detail;
import itu.entity.sql.Voiture;
import itu.repository.nosql.DetailRepository;
import itu.repository.VoitureRepository;

import java.util.ArrayList;
import java.util.List;

public class Annonce {
    private Detail detail;
    private Voiture voiture;

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public List<Annonce> annonces(DetailRepository detailRepository, VoitureRepository voitureRepository){
        List<Annonce> ret = new ArrayList<>();
        List<Voiture> annonces = voitureRepository.findAllByEtat(100);
        for(Voiture v : annonces){
            var m = new Annonce();
            m.setVoiture(v);
            m.setDetail(detailRepository.findById(v.getCaracteristiqueID()).get());
            ret.add(m);
        }
        return ret;
    }
}
