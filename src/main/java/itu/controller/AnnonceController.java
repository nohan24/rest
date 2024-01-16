package itu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import itu.entity.nosql.Annonce;
import itu.entity.nosql.Detailelectrique;
import itu.services.AnnonceServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
public class AnnonceController {
    private final AnnonceServices annonceServices;

    public AnnonceController(AnnonceServices annonceServices) {
        this.annonceServices = annonceServices;
    }

    @PostMapping("/annonces")
    public ResponseEntity<?> insertAnnonce(@RequestPart(name = "images") List<MultipartFile> images, String detail, @RequestPart(name = "detailelectrique", required = false)String detailelectrique, double prix){
        ObjectMapper objectMapper = new ObjectMapper();
        Annonce objet = null;
        Detailelectrique electrique = null;
        try {
            objet = objectMapper.readValue(detail, Annonce.class);
            electrique = detailelectrique != null ? objectMapper.readValue(detailelectrique, Detailelectrique.class) : null;
            annonceServices.insertAnnonce(objet, electrique,images, prix);
            return ResponseEntity.status(HttpStatus.OK).body("Annonce ajouté avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/annonces")
    public void getAnnonces(){

    }
}
