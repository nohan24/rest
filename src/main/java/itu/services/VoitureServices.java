package itu.services;

import itu.entity.nosql.*;
import itu.entity.sql.Commission;
import itu.repository.CommissionRepo;
import itu.repository.nosql.*;
import itu.repository.VoitureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoitureServices {
    private final VoitureRepository voitureRepository;
    private final DetailRepository detailRepository;
    private final ModeleRepo modeleRepo;
    private final CarburantRepo carburantRepo;
    private final TransmissionRepo transmissionRepo;
    private final CategorieRepo categorieRepo;
    private final MarqueRepo marqueRepo;
    private final EquipementRepo equipementRepo;
    private final CommissionRepo commissionRepo;
    public VoitureServices(VoitureRepository voitureRepository, DetailRepository detailRepository, ModeleRepo modeleRepo, CarburantRepo carburantRepo, TransmissionRepo transmissionRepo, CategorieRepo categorieRepo, MarqueRepo marqueRepo, EquipementRepo equipementRepo, CommissionRepo commissionRepo) {
        this.voitureRepository = voitureRepository;
        this.detailRepository = detailRepository;
        this.modeleRepo = modeleRepo;
        this.carburantRepo = carburantRepo;
        this.transmissionRepo = transmissionRepo;
        this.categorieRepo = categorieRepo;
        this.marqueRepo = marqueRepo;
        this.equipementRepo = equipementRepo;
        this.commissionRepo = commissionRepo;
    }

    public List<Marque> getMarques(){
        return marqueRepo.findAll();
    }

    public Marque insertMarque(Marque marque){
        return marqueRepo.insert(marque);
    }

    public Marque findMarque(String id) throws Exception {
        return marqueRepo.findById(id).orElseThrow(() -> new Exception("Marque non trouvé."));
    }

    public void deleteMarqueAll(){
        marqueRepo.deleteAll();
    }

    public void deleteMarque(String id){
        marqueRepo.deleteById(id);
    }

    public void deleteTransmissionAll(){
        transmissionRepo.deleteAll();
    }

    public void deleteTransmission(String id){
        transmissionRepo.deleteById(id);
    }

    public List<Transmission> getTransmissions(){
        return transmissionRepo.findAll();
    }
    public Transmission findTransmission(String id) throws Exception {
        return transmissionRepo.findById(id).orElseThrow(() -> new Exception("Valeur non trouvé."));
    }

    public Transmission insertTransmission(Transmission transmission){
        return transmissionRepo.insert(transmission);
    }


    public void deleteCarburantAll(){
        carburantRepo.deleteAll();
    }

    public void deleteCarburant(String id){
        carburantRepo.deleteById(id);
    }

    public List<Carburant> getCarburants(){
        return carburantRepo.findAll();
    }
    public Carburant findCarburant(String id) throws Exception {
        return carburantRepo.findById(id).orElseThrow(() -> new Exception("Valeur non trouvé."));
    }

    public Carburant insertCarburant(Carburant carburant){
        return carburantRepo.insert(carburant);
    }


    public void deleteCategorieAll(){
        categorieRepo.deleteAll();
    }

    public void deleteCategorie(String id){
        categorieRepo.deleteById(id);
    }

    public List<Categorie> getCategories(){
        return categorieRepo.findAll();
    }
    public Categorie findCategorie(String id) throws Exception {
        return categorieRepo.findById(id).orElseThrow(() -> new Exception("Valeur non trouvé."));
    }

    public Categorie insertCategorie(Categorie categorie){
        return categorieRepo.insert(categorie);
    }

    public void deleteEquipementAll(){
        equipementRepo.deleteAll();
    }

    public void deleteEquipement(String id){
        equipementRepo.deleteById(id);
    }

    public List<Equipement> getEquipements(){
        return equipementRepo.findAll();
    }
    public Equipement findEquipement(String id) throws Exception {
        return equipementRepo.findById(id).orElseThrow(() -> new Exception("Valeur non trouvé."));
    }

    public Equipement insertEquipement(Equipement equipement){
        return equipementRepo.insert(equipement);
    }

    public void deleteModeleAll(){
        modeleRepo.deleteAll();
    }

    public void deleteModele(String id){
        modeleRepo.deleteById(id);
    }

    public List<ModeleBase> getModeles(){
        return modeleRepo.findAll();
    }
    public ModeleBase findModele(String id) throws Exception {
        return modeleRepo.findById(id).orElseThrow(() -> new Exception("Valeur non trouvé."));
    }

    public ModeleBase insertModele(ModeleBase modele){
        return modeleRepo.insert(modele);
    }

    public void updateCommission(double valeur) throws Exception {
        Commission c = commissionRepo.findAll().get(0);
        c.setValeur(valeur);
        commissionRepo.save(c);
    }

    public Commission getCommission(){
        return commissionRepo.findAll().get(0);
    }
}
