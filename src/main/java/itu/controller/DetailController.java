package itu.controller;

import itu.entity.nosql.*;
import itu.entity.sql.Commission;
import itu.services.VoitureServices;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DetailController {
    private final VoitureServices voitureServices;
    public DetailController(VoitureServices voitureServices) {
        this.voitureServices = voitureServices;
    }
    @PostMapping("/marques")
    public ResponseEntity<?> insertMarque(String marque){
        Marque m = new Marque();
        try {
            m.setMarque(marque);
            Marque r = voitureServices.insertMarque(m);
            return ResponseEntity.status(HttpStatus.OK).body(r);
        }catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (Exception e) {
            if(e.getMessage().contains("11000")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cette marque existe déjà.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/marques")
    public List<Marque> allMarques(){
        return voitureServices.getMarques();
    }

    @GetMapping("/marques/{id}")
    public ResponseEntity<?> findMarques(@PathVariable("id")String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(voitureServices.findMarque(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/marques")
    public void deleteMarques(){
        voitureServices.deleteMarqueAll();
    }

    @DeleteMapping("/marques/{id}")
    public void deleteMarque(@PathVariable("id")String id){
        voitureServices.deleteMarque(id);
    }

    @DeleteMapping("/categories")
    public void deleteCategories(){
        voitureServices.deleteCategorieAll();
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategorie(@PathVariable("id")String id){
        voitureServices.deleteCategorie(id);
    }

    @DeleteMapping("/transmissions")
    public void deleteTransmissions(){
        voitureServices.deleteTransmissionAll();
    }

    @DeleteMapping("/transmissions/{id}")
    public void deleteTransmission(@PathVariable("id")String id){
        voitureServices.deleteTransmission(id);
    }


    @DeleteMapping("/modeles")
    public void deleteModeles(){
        voitureServices.deleteModeleAll();
    }

    @DeleteMapping("/equipements")
    public void deleteEquipements(){
        voitureServices.deleteEquipementAll();
    }


    @DeleteMapping("/modeles/{id}")
    public void deleteModele(@PathVariable("id")String id){
        voitureServices.deleteModele(id);
    }

    @DeleteMapping("/equipements/{id}")
    public void deleteEquipement(@PathVariable("id")String id){
        voitureServices.deleteEquipement(id);
    }


    @DeleteMapping("/carburants/{id}")
    public void deleteCarburant(@PathVariable("id")String id){
        voitureServices.deleteCarburant(id);
    }


    @DeleteMapping("/carburants")
    public void deleteCarburants(){
        voitureServices.deleteCarburantAll();
    }


    @PostMapping("/categories")
    public ResponseEntity<?> insertCategorie(String categorie){
        Categorie m = new Categorie();
        try {
            m.setCategorie(categorie);
            Categorie r = voitureServices.insertCategorie(m);
            return ResponseEntity.status(HttpStatus.OK).body(r);
        } catch (Exception e) {
            if(e.getMessage().contains("11000")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La valeur existe déjà.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/carburants")
    public ResponseEntity<?> insertCarburant(String carburant){
        Carburant m = new Carburant();
        try {
            m.setCarburant(carburant);
            Carburant r = voitureServices.insertCarburant(m);
            return ResponseEntity.status(HttpStatus.OK).body(r);
        } catch (Exception e) {
            if(e.getMessage().contains("11000")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La valeur existe déjà.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/modeles")
    public ResponseEntity<?> insertTransmission(String idmarque, String modele){
        ModeleBase m = new ModeleBase();
        try {
            Marque marque = voitureServices.findMarque(idmarque);
            m.setMarque(marque.getMarque());
            m.setModele(modele);
            ModeleBase r = voitureServices.insertModele(m);
            return ResponseEntity.status(HttpStatus.OK).body(r);
        } catch (Exception e) {
            if(e.getMessage().contains("11000")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La valeur existe déjà.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/transmissions")
    public ResponseEntity<?> insertTransmission(String transmission){
        Transmission m = new Transmission();
        try {
            m.setTransmission(transmission);
            Transmission r = voitureServices.insertTransmission(m);
            return ResponseEntity.status(HttpStatus.OK).body(r);
        } catch (Exception e) {
            if(e.getMessage().contains("11000")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La valeur existe déjà.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/equipements")
    public ResponseEntity<?> insertEquipement(String equipement){
        try {
            Equipement m = new Equipement();
            m.setDesignation(equipement);
            Equipement r = voitureServices.insertEquipement(m);
            return ResponseEntity.status(HttpStatus.OK).body(r);
        } catch (Exception e) {
            if(e.getMessage().contains("11000")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La valeur existe déjà.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/equipements")
    public List<Equipement> allEquipements(){
        return voitureServices.getEquipements();
    }

    @GetMapping("/equipements/{id}")
    public ResponseEntity<?> findEquipement(@PathVariable("id")String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(voitureServices.findEquipement(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/modeles")
    public List<ModeleBase> allModeles(){
        return voitureServices.getModeles();
    }

    @GetMapping("/modeles/{id}")
    public ResponseEntity<?> findModele(@PathVariable("id")String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(voitureServices.findModele(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/categories")
    public List<Categorie> allCategories(){
        return voitureServices.getCategories();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> findCategorie(@PathVariable("id")String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(voitureServices.findCategorie(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/carburants")
    public List<Carburant> allCarburants(){
        return voitureServices.getCarburants();
    }

    @GetMapping("/carburants/{id}")
    public ResponseEntity<?> findCarburant(@PathVariable("id")String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(voitureServices.findCarburant(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/transmissions")
    public List<Transmission> allTransmissions(){
        return voitureServices.getTransmissions();
    }

    @GetMapping("/transmissions/{id}")
    public ResponseEntity<?> findTransmission(@PathVariable("id")String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(voitureServices.findTransmission(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/commissions")
    public ResponseEntity<String> updateCommission(double valeur){
        try {
            voitureServices.updateCommission(valeur);
            return ResponseEntity.status(HttpStatus.OK).body("Valeur modifiée.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/commissions")
    public Commission getCommission(){
        return voitureServices.getCommission();
    }

    @PutMapping("/voitures/{id}")
    public ResponseEntity vendue(@PathVariable(name = "id") int id){
        try {
            voitureServices.changeStatus(id);
            return ResponseEntity.ok("Statut changé.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
