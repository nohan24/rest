package itu.controller;

import itu.services.ImageServices;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

@RestController
public class ImageController {
    private final ImageServices imageServices;

    public ImageController(ImageServices imageServices) {
        this.imageServices = imageServices;
    }

    @GetMapping("/images/{url}")
    public ResponseEntity<?> images(@PathVariable String url){
        try {
            return imageServices.downloadImage(url);
        }catch(NoSuchFileException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ressource introuvable.");
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
