package itu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
 import com.mongodb.client.MongoClient;
import itu.entity.Annonce;
import itu.entity.Recherche;
import itu.entity.nosql.Detail_annonce;
import itu.entity.nosql.Detailelectrique;
import itu.repository.nosql.AnnonceRepository;
import itu.services.AnnonceServices;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@RestController
public class AnnonceController {
    private final AnnonceRepository annonceRepository;
    private final AnnonceServices annonceServices;

    public AnnonceController(AnnonceRepository annonceRepository, AnnonceServices annonceServices, MongoClient client, MongoConverter converter) {
        this.annonceRepository = annonceRepository;
        this.annonceServices = annonceServices;
        this.client = client;
        this.converter = converter;
    }
    final MongoClient client;
    final MongoConverter converter;

    @PostMapping("/annonces")
    public ResponseEntity<?> insertAnnonce(@RequestPart(name = "images") List<MultipartFile> images, String detail, @RequestPart(name = "detailelectrique", required = false)String detailelectrique, double prix){
        ObjectMapper objectMapper = new ObjectMapper();
        Detail_annonce objet = null;
        Detailelectrique electrique = null;
        try {
            objet = objectMapper.readValue(detail, Detail_annonce.class);
            electrique = detailelectrique != null ? objectMapper.readValue(detailelectrique, Detailelectrique.class) : null;
            return ResponseEntity.status(HttpStatus.OK).body(annonceServices.insertAnnonce(objet, electrique,images, prix));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/annonces")
    public List<Annonce> getAnnonces(){
        return annonceServices.getAnnonces();
    }

    @GetMapping("/user/annonces")
    public ResponseEntity<?> userAnnonce(){
        int id = (Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials()   ;
        return ResponseEntity.ok(annonceServices.userAnnonce(id));
    }

    @GetMapping("/validation/annonces")
    public List<Annonce> getAnnonceNotValidate(){
        return annonceServices.annonceNotValidate();
    }

    @PutMapping("/validation/annonces/{id}")
    public ResponseEntity validation(@PathVariable(name = "id") int id){
        annonceServices.validation(id);
        return ResponseEntity.ok("Validé");
    }

    @DeleteMapping("/validation/annonces/{id}")
    public ResponseEntity refuse(@PathVariable(name = "id") int id){
        annonceServices.refuser(id);
        return ResponseEntity.ok("Annonce refusé.");
    }

    @GetMapping("/annonces/{id}")
    public Annonce getAnnonce(@PathVariable(name = "id") int id){
        return annonceServices.findAnnonce(id);
    }

    @GetMapping("/annonces/search")
    public ResponseEntity searchAnnonce(@RequestParam(name = "i") String i){
        //return ResponseEntity.ok(annonceRepository.findAllByMarqueLike(""));
        //return ResponseEntity.ok(annonceRepository.findAllByMarqueIsIn(new String[]{""}));
        return ResponseEntity.ok(annonceServices.recherche(i));
    }

    @PostMapping("/annonces/filtrer")
    public ResponseEntity filtre(@RequestBody Recherche recherche){
        //return ResponseEntity.ok(recherche);
        return ResponseEntity.ok(annonceServices.filtrer(recherche));
        //return ResponseEntity.ok(annonceRepository.findAllByCategorieInAndCarburantInAndTransmissionIn(new String[]{"SUV"}, new String[]{"test"}, new String[]{"Manuelle"}));
    }

    @PostMapping("/annonces/{id}/favoris")
    public ResponseEntity setFavoris(@PathVariable(name = "id") int id){
        try {
            annonceServices.addFavoris(id);
        } catch (Exception e) {
            if(e.getMessage().contains("duplicate")) return ResponseEntity.status(409).body("Annonce déjà mis en favoris.");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Favoris ajouté.");
    }

    @GetMapping("/annonces/favoris")
    public ResponseEntity getFavoris(){
        return ResponseEntity.ok(annonceServices.getFavoris());
    }

    @DeleteMapping("/annonces/{id}/favoris")
    public ResponseEntity deleteFav(@PathVariable(name = "id")int id){
        annonceServices.deleteFavoris(id);
        return ResponseEntity.ok("Annonce retirée des favoris.");
    }

    @PutMapping("/annonces/{id}/vendu")
    public ResponseEntity vendue(@PathVariable(name = "id") int id){
        try {
            annonceServices.vendre(id);
            return ResponseEntity.ok("Voiture vendue.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/tokene")
    public void getToken(String token){
        System.out.println(token);
    }

}
