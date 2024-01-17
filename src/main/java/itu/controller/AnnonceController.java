package itu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import itu.config.SecurityConfig;
import itu.entity.Annonce;
import itu.entity.nosql.Detail_annonce;
import itu.entity.nosql.Detailelectrique;
import itu.services.AnnonceServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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
        Detail_annonce objet = null;
        Detailelectrique electrique = null;
        try {
            objet = objectMapper.readValue(detail, Detail_annonce.class);
            electrique = detailelectrique != null ? objectMapper.readValue(detailelectrique, Detailelectrique.class) : null;
            return ResponseEntity.status(HttpStatus.OK).body(annonceServices.insertAnnonce(objet, electrique,images, prix));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/annonces")
    public List<Annonce> getAnnonces(){
        return annonceServices.getAnnonces();
    }

    @GetMapping("/user/{id}/annonces")
    public ResponseEntity<?> userAnnonce(@PathVariable int id){
        if(id != (Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilisateur non correspondant.");
        return ResponseEntity.ok(annonceServices.userAnnonce(id));
    }

    @GetMapping("/validation/annonces")
    public List<Annonce> getAnnonceNotValidate(){
        return annonceServices.annonceNotValidate();
    }
}
