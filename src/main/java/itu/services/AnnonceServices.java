package itu.services;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import itu.entity.Annonce;
import itu.entity.Recherche;
import itu.entity.nosql.*;
import itu.entity.sql.Favoris;
import itu.entity.sql.Utilisateur;
import itu.entity.sql.Vente;
import itu.entity.sql.Voiture;
import itu.repository.*;
import itu.repository.nosql.*;
import jakarta.persistence.EntityNotFoundException;
import org.bson.Document;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnnonceServices {
    final MongoClient client;
    final FavorisRepo favorisRepo;
    final MongoConverter converter;
    private final ModeleRepo modeleRepo;
    private final CarburantRepo carburantRepo;
    private final TransmissionRepo transmissionRepo;
    private final CategorieRepo categorieRepo;
    private final MarqueRepo marqueRepo;
    private final EquipementRepo equipementRepo;
    private final AnnonceRepository annonceRepository;
    private final ImageServices imageServices;
    private final VoitureRepository voitureRepository;
    final UtilisateurRepository utilisateurRepository;
    final CommissionRepo commissionRepo;
    final VenteRepo venteRepo;
    public AnnonceServices(MongoClient client, FavorisRepo favorisRepo, MongoConverter converter, ModeleRepo modeleRepo, CarburantRepo carburantRepo, TransmissionRepo transmissionRepo, CategorieRepo categorieRepo, MarqueRepo marqueRepo, EquipementRepo equipementRepo, AnnonceRepository annonceRepository, ImageServices imageServices, VoitureRepository voitureRepository, UtilisateurRepository utilisateurRepository, CommissionRepo commissionRepo, VenteRepo venteRepo) {
        this.client = client;
        this.favorisRepo = favorisRepo;
        this.converter = converter;
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
        this.commissionRepo = commissionRepo;
        this.venteRepo = venteRepo;
    }

    private <T> T join(CrudRepository<T, String> repository, String id, String entityName) throws Exception {
        if(id == null)throw new Exception(entityName + " requis.");
        T details = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(entityName + " not found for id: " + id));
        return details;
    }

    public String[] getEquipements(String[] equipements) throws Exception {
        String[] ret = new String[equipements.length];
        for (int i = 0; i < equipements.length; i++) {
            ret[i] = join((CrudRepository<Equipement, String>)equipementRepo, equipements[i], "Equipement").getDesignation();
        }
        return ret;
    }

    void chekcModele(String modele, String marque) throws Exception {
        if(!modeleRepo.existsByMarqueAndModele(marque, modele))throw new Exception("Le modèle ne correspond pas à la marque.");
    }

    public Detail_annonce insertAnnonce(Detail_annonce detail, Detailelectrique detailelectrique, List<MultipartFile> images, double prix) throws Exception {
        if(images.isEmpty())throw new Exception("Images requis.");
        for(MultipartFile f : images){
            System.out.println();
            if(!Objects.requireNonNull(f.getContentType()).contains("image"))throw new Exception("Images requis.");
        }
        if(detail.getPlaces() == null || detail.getPlaces() == 0)throw new Exception("Nombre de place requis.");
        Detail_annonce ret = detail;
        if(detail.getTitre_voiture() == null)throw new Exception("Titre annonce requis.");
        if(detail.getKilometrage() == null)throw new Exception("Kilométrage requis.");
        ret.setDetailelectrique(detailelectrique);
        ret.setMarque(join((CrudRepository<Marque, String>) marqueRepo, detail.getMarque(), "Marque").getMarque());
        ret.setCarburant(join((CrudRepository<Carburant, String>) carburantRepo, detail.getCarburant(), "Carburant").getCarburant());
        ret.setCategorie(join((CrudRepository<Categorie, String>)categorieRepo, detail.getCategorie(), "Categorie").getCategorie());
        ret.setTransmission(join((CrudRepository<Transmission, String>)transmissionRepo, detail.getTransmission(), "Transmission").getTransmission());
        ret.setModele(join((CrudRepository<ModeleBase, String>)modeleRepo, detail.getModele(),"Modele").getModele());
        chekcModele(ret.getModele(), ret.getMarque());
        if(detail.getEquipement() != null) {
            detail.setEquipement(Arrays.stream(detail.getEquipement())
                    .distinct()
                    .toArray(String[]::new));
        } else{throw new Exception("Equipements nécessaires.");}
        ret.setEquipement(getEquipements(detail.getEquipement()));
        ret.setImages(imageServices.uploadImage(images));
        ret = annonceRepository.save(ret);
        Voiture v = new Voiture();
        v.setCaracteristiqueID(ret.get_id());
        v.setPrix(prix);
        v.setOwner(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString()));
        v.setUsername(utilisateurRepository.findById(v.getOwner()).get().getUsername());
        voitureRepository.save(v);
        return ret;
    }

    public List<Annonce> getAnnonces(){
        List<Voiture> voitures = new ArrayList<>();
        List<Annonce> annonces = new ArrayList<>();
        if(SecurityContextHolder.getContext().getAuthentication().getCredentials() instanceof Integer){
            voitures = voitureRepository.findAllByOwnerIsNotAndEtat((Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials(), 200);
            Utilisateur current = utilisateurRepository.findById((Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials()).get();
            for(Voiture v : voitures){
                var a = new Annonce();
                a = buildFromV(v);
                a.setFavorite(favorisRepo.existsByUtilisateurAndVoiture(current, v));
                annonces.add(a);
            }
        }else{
            voitures = voitureRepository.findAllByEtatOrderByDateCreationDesc(200);
            for(Voiture v : voitures){
                var a = new Annonce();
                a = buildFromV(v);
                annonces.add(a);
            }
        }

        return annonces;
    }

    public List<Annonce> annonceNotValidate(){
        List<Annonce> annonces = new ArrayList<>();
        for(Voiture v : voitureRepository.findAllByEtatOrderByDateCreationDesc(100)){
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

    public Annonce findAnnonce(int id) {
        Voiture v = voitureRepository.findById(id).get();
        Annonce ret = new Annonce();
        ret.setVoiture(v);
        ret.setDetailAnnonce(annonceRepository.findById(v.getCaracteristiqueID()).get());
        return ret;
    }

    public void validation(int id){
        Voiture v = voitureRepository.findById(id).get();
        v.setEtat(200);
        LocalDateTime localDateTime = LocalDateTime.now();
        v.setDateValidation(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        voitureRepository.save(v);
    }

    public void refuser(int id){
        Voiture v = voitureRepository.findById(id).get();
        v.setEtat(210);
        LocalDateTime localDateTime = LocalDateTime.now();
        v.setDateValidation(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        voitureRepository.save(v);
    }

    private Annonce build(Detail_annonce d){
        Annonce ret = new Annonce();
        ret.setDetailAnnonce(d);
        ret.setVoiture(voitureRepository.findByCaracteristiqueID(d.get_id()));
        return ret;
    }

    private Annonce buildFromV(Voiture v){
        Annonce ret = new Annonce();
        ret.setDetailAnnonce(annonceRepository.findById(v.getCaracteristiqueID()).get());
        ret.setVoiture(v);
        return ret;
    }

    public List<Annonce> recherche(String i){
        List<Annonce> r = new ArrayList<>();
        MongoDatabase database = client.getDatabase("voiture");
        MongoCollection<Document> collection = database.getCollection("detail");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                new Document("text",
                        new Document("query", i)
                                .append("path", Arrays.asList("marque","description_supplementaire", "modele", "categorie", "carburant", "transmission", "equipement"))))));
        result.forEach(doc -> r.add(build(converter.read(Detail_annonce.class, doc))));
        return r;
    }

    private List<Annonce> searchNosql(Recherche r){
        List<Annonce> a = new ArrayList<>();
        Recherche recherche = new Recherche(carburantRepo, categorieRepo, transmissionRepo);
        if(r.getCategorie() == null || r.getCategorie().length == 0)r.setCategorie(recherche.getCategorie());
        if(r.getCarburant() == null ||r.getCarburant().length == 0)r.setCarburant(recherche.getCarburant());
        if(r.getTransmission() == null ||r.getTransmission().length == 0)r.setTransmission(recherche.getTransmission());
        for(Detail_annonce d : annonceRepository.findAllByMarqueLikeIgnoreCaseAndModeleLikeIgnoreCaseAndKilometrageBetweenAndPlacesGreaterThanEqualAndCategorieInAndCarburantInAndTransmissionIn(r.getMarque(), r.getModele(), r.getKmin(), r.getKmax(), r.getPlace(), r.getCategorie(), r.getCarburant(), r.getTransmission())){
            a.add(build(d));
        }
        return a;
    }

    private List<Annonce> searchSql(Recherche recherche){
        List<Annonce> a = new ArrayList<>();
        for(Voiture v : voitureRepository.findAllByPrixBetween(recherche.getPrixmin(), recherche.getPrixmax())){
            a.add(buildFromV(v));
        }
        return a;
    }

    public List<Annonce> filtrer(Recherche r){
        List<Annonce> a1 = searchNosql(r);
        List<Annonce> a2 = searchSql(r);
        List<Annonce> intersectionModels = a1.stream()
                .filter(model -> a2.stream().anyMatch(car -> car.getVoiture().getId().equals(model.getVoiture().getId())))
                .collect(Collectors.toList());
        return intersectionModels;
    }

    public void addFavoris(int id) throws Exception {
        Voiture v = voitureRepository.findById(id).orElseThrow(() -> new Exception("Annonce non trouvé."));
        Utilisateur u = utilisateurRepository.findById((Integer) SecurityContextHolder.getContext().getAuthentication().getCredentials()).get();
        Favoris f = new Favoris();
        f.setUtilisateur(u);
        f.setVoiture(v);
        favorisRepo.save(f);
    }

    public List<Annonce> getFavoris(){
        List<Annonce> ret = new ArrayList<>();
        for(Favoris favoris : favorisRepo.findAllByUtilisateur(utilisateurRepository.findById((Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials()).get())){
            ret.add(buildFromV(favoris.getVoiture()));
        }
        return ret;
    }

    @Transactional
    public void deleteFavoris(int idAnnonce){
        favorisRepo.deleteByUtilisateurAndVoiture(utilisateurRepository.findById((Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials()).get(), voitureRepository.findById(idAnnonce).get());
    }

    double calculCommission(double prix){
        return prix * commissionRepo.findAll().get(0).getValeur() / 100;
    }

    @Transactional
    public void vendre(int id) throws Exception {
        Voiture v = voitureRepository.findById(id).get();
        if(v.getEtat() != 200)throw new Exception("Cette voiture ne peut pas être vendue.");
        if(v.getOwner() != (Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials()) throw new Exception("Ceci n'est pas votre voiture.");
        v.setEtat(300);
        Vente vente = new Vente();
        vente.setVoiture(v);
        vente.setCommission(calculCommission(v.getPrix()));
        vente.setUtilisateur(utilisateurRepository.findById((Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials()).get());
        venteRepo.save(vente);
    }
}
