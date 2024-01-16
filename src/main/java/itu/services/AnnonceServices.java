package itu.services;

import itu.entity.Annonce;
import itu.entity.nosql.*;
import itu.entity.sql.Voiture;
import itu.repository.UtilisateurRepository;
import itu.repository.VoitureRepository;
import itu.repository.nosql.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class AnnonceServices {
    private final ModeleRepo modeleRepo;
    private final CarburantRepo carburantRepo;
    private final TransmissionRepo transmissionRepo;
    private final CategorieRepo categorieRepo;
    private final MarqueRepo marqueRepo;
    private final EquipementRepo equipementRepo;
    private final AnnonceRepository annonceRepository;
    private final ImageServices imageServices;
    private final VoitureRepository voitureRepository;
    private final UtilisateurRepository utilisateurRepository;

    public AnnonceServices(ModeleRepo modeleRepo, CarburantRepo carburantRepo, TransmissionRepo transmissionRepo, CategorieRepo categorieRepo, MarqueRepo marqueRepo, EquipementRepo equipementRepo, AnnonceRepository annonceRepository, ImageServices imageServices, VoitureRepository voitureRepository, UtilisateurRepository utilisateurRepository) {
        this.modeleRepo = modeleRepo;
        this.carburantRepo = carburantRepo;
        this.transmissionRepo = transmissionRepo;
        this.categorieRepo = categorieRepo;
        this.marqueRepo = marqueRepo;
        this.equipementRepo = equipementRepo;
        this.annonceRepository = annonceRepository;
        this.imageServices = imageServices;
        this.voitureRepository = voitureRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    private <T> T join(CrudRepository<T, String> repository, String id, String entityName) {
        T details = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(entityName + " not found for id: " + id));
        return details;
    }

    public String[] getEquipements(String[] equipements) {
        String[] ret = new String[equipements.length];
        for (int i = 0; i < equipements.length; i++) {
            ret[i] = join((CrudRepository<Equipement, String>)equipementRepo, equipements[i], "equipement").getDesignation();
        }
        return ret;
    }

    public Detail_annonce insertAnnonce(Detail_annonce detail, Detailelectrique detailelectrique, List<MultipartFile> images, double prix) throws IOException {
        Detail_annonce ret = detail;
        ret.setDetailelectrique(detailelectrique);
        ret.setMarque(join((CrudRepository<Marque, String>) marqueRepo, detail.getMarque(), "marque").getMarque());
        ret.setCarburant(join((CrudRepository<Carburant, String>) carburantRepo, detail.getCarburant(), "carburant").getCarburant());
        ret.setCategorie(join((CrudRepository<Categorie, String>)categorieRepo, detail.getCategorie(), "categorie").getCategorie());
        ret.setTransmission(join((CrudRepository<Transmission, String>)transmissionRepo, detail.getTransmission(), "transmission").getTransmission());
        ret.setModele(join((CrudRepository<ModeleBase, String>)modeleRepo, detail.getModele(),"modele").getModele());
        detail.setEquipement(Arrays.stream(detail.getEquipement())
                .distinct()
                .toArray(String[]::new));
        ret.setEquipement(getEquipements(detail.getEquipement()));
        ret.setImages(imageServices.uploadImage(images));
        ret = annonceRepository.save(ret);
        Voiture v = new Voiture();
        v.setCaracteristiqueID(ret.get_id());
        v.setPrix(prix);
        v.setOwner(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString()));
        voitureRepository.save(v);
        return ret;
    }

    public List<Annonce> getAnnonces(){
        List<Annonce> annonces = new ArrayList<>();
        for(Voiture v : voitureRepository.findAllByEtat(200)){
            var a = new Annonce();
            a.setDetailAnnonce(annonceRepository.findById(v.getCaracteristiqueID()).get());
            a.setVoiture(v);
            annonces.add(a);
        }
        return annonces;
    }

    public List<Annonce> userAnnonce(int id){
        List<Annonce> annonces = new ArrayList<>();
        for(Voiture v : voitureRepository.findAllByOwner(id)){
            var a = new Annonce();
            a.setDetailAnnonce(annonceRepository.findById(v.getCaracteristiqueID()).get());
            a.setVoiture(v);
            annonces.add(a);
        }
        return annonces;
    }
}
