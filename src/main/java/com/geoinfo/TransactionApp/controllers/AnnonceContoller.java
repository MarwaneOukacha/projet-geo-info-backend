package com.geoinfo.TransactionApp.controllers;

import com.geoinfo.TransactionApp.dtos.*;
import com.geoinfo.TransactionApp.entities.Annonce;
import com.geoinfo.TransactionApp.enums.BienType;
import com.geoinfo.TransactionApp.enums.OperationType;
import com.geoinfo.TransactionApp.services.AnnonceService;
import com.geoinfo.TransactionApp.servicesImp.AnnonceServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/annonces")
public class AnnonceContoller {

    @Autowired private AnnonceService annonceService;

    @PostMapping("/{id}")
    public void Ajouter_annonce(@RequestBody Annonce annonce,@PathVariable long id){
        annonceService.ajouterAnnonce(annonce,id);
    }

    @DeleteMapping("/{id}")
    public void Supprimer_annonce(@PathVariable long id){
        annonceService.supprimerAnnonce(id);
    }

    @PutMapping("/{id}")
    public void Modifier_annonce(@RequestBody Annonce annonce,@PathVariable long id){
        annonceService.modifierAnnonce(annonce,id);
    }

    @GetMapping
    List<Annonce> GetALlAnnonces(){
       return annonceService.getAllAnnonce();
    }

    @GetMapping("/{id}")
    Annonce GetAnnonce(@PathVariable long id){
        return annonceService.getAnnonce(id);
    }

    @PutMapping("/Reserve_annonce/{id_annonce}/{id_intermediaire}")
    public void Reserver_annonce(@PathVariable Long id_annonce,@PathVariable Long id_intermediaire){
        annonceService.reserverAnnonce(id_intermediaire,id_annonce);
    }

    @GetMapping("/Annonces_reservee_pour_intermediaire/{id}")
    List<Annonce> Get_annonces_reserve_for_intermediaire(@PathVariable long id){
        return  annonceService.getAlAnnoncesReseverByIntermediaire(id);
    }

    @GetMapping("/Annonces_reservee_pour_intermediaire_2/{id}")
    List<AnnonceurAnnonceResponse> Get_annonces_reserve_for_intermediaire_2(@PathVariable long id){
        return  annonceService.getAlAnnoncesReseverByIntermediaire_2(id);
    }

    @GetMapping("/Annonces_rejeter_par_intermediaire/{id}")
    List<Annonce> Get_annonces_rejeter_for_intermediaire(@PathVariable long id){
        return  annonceService.getAlAnnoncesRejeterByIntermediaire(id);
    }

    @GetMapping("/Annonces_rejeter_par_intermediaire_2/{id}")
    List<AnnonceurAnnonceResponse> Get_annonces_rejeter_for_intermediaire_2(@PathVariable long id){
        return  annonceService.getAlAnnoncesRejeterByIntermediaire_2(id);
    }

    @GetMapping("/Annonces_archiver_par_intermediaire/{id}")
    List<AnnonceurAnnonceResponse> Get_annonces_archiver_for_intermediaire(@PathVariable long id){
        return  annonceService.getAlAnnoncesArchiverByIntermediaire(id);
    }



    @GetMapping("/Annonces_pas_encore_reserve")
    List<Annonce> Get_annonces_pas_encore_reservee(){
        return  annonceService.getAllAnnoncesPasEncoreReserve();
    }

    @GetMapping("/Annonces_pas_encore_reserve_2")
    List<AnnonceurAnnonceResponse> Get_annonces_pas_encore_reservee_2(){
        return  annonceService.getAllAnnoncesPasEncoreReserve_2();
    }

    @GetMapping("/Annonces_publie")
    List<Annonce> Get_annonces_publie(){
        return  annonceService.getAllAnnoncesPublie();
    }

    @PutMapping("/Publier_annonce/{id_annonce}")
    public void Publier_annonce(@PathVariable Long id_annonce){
        annonceService.PublierAnnonce(id_annonce);
    }


    @PutMapping("/Rejeter_annonce/{id_annonce}")
    public void Rejeter_annonce(@PathVariable Long id_annonce, @RequestBody Map<String, String> body) {
        String motifRejet = body.get("motifRejet");
        annonceService.RejeterAnnonce(id_annonce, motifRejet);
    }


    @PutMapping("/Liberer_annonce/{id_annonce}")
    public void Liberer_annonce(@PathVariable Long id_annonce){
        annonceService.libererAnnonce(id_annonce);
    }



    @PutMapping("/archiver_annonce/{id_annonce}")
    public void Archiver_annonce(@PathVariable Long id_annonce){
        annonceService.archiverAnnonce(id_annonce);
    }


    // Categorie par prix

    @GetMapping("/prix/categorie1")
    public List<Annonce> getAnnoncesByPriceCategory1() {
        return annonceService.getAnnoncesByPriceCategories().get(0);
    }
    @GetMapping("/prix/categorie2")
    public List<Annonce> getAnnoncesByPriceCategory2() {
        return annonceService.getAnnoncesByPriceCategories().get(1);
    }
    @GetMapping("/prix/categorie3")
    public List<Annonce> getAnnoncesByPriceCategory3() {
        return annonceService.getAnnoncesByPriceCategories().get(2);
    }
    @GetMapping("/prix/categorie4")
    public List<Annonce> getAnnoncesByPriceCategory4() {
        return annonceService.getAnnoncesByPriceCategories().get(3);
    }
    @GetMapping("/prix/categorie5")
    public List<Annonce> getAnnoncesByPriceCategory5() {
        return annonceService.getAnnoncesByPriceCategories().get(4);
    }

    // Categorie par surface

    @GetMapping("/surface/categorie1")
    public List<Annonce> getAnnoncesBySurfaceCategory1() {
        return annonceService.getAnnoncesBySurfaceCategories().get(0);
    }
    @GetMapping("/surface/categorie2")
    public List<Annonce> getAnnoncesBySurfaceCategory2() {
        return annonceService.getAnnoncesBySurfaceCategories().get(1);
    }
    @GetMapping("/surface/categorie3")
    public List<Annonce> getAnnoncesBySurfaceCategory3() {
        return annonceService.getAnnoncesBySurfaceCategories().get(2);
    }
    @GetMapping("/surface/categorie4")
    public List<Annonce> getAnnoncesBySurfaceCategory4() {
        return annonceService.getAnnoncesBySurfaceCategories().get(3);
    }
    @GetMapping("/surface/categorie5")
    public List<Annonce> getAnnoncesBySurfaceCategory5() {
        return annonceService.getAnnoncesBySurfaceCategories().get(4);
    }


    @GetMapping("/annonces_vendre")
    public List<Annonce> getAnnoncesAvendres() {
        return annonceService.getAnnonceOperation("VENDRE");
    }
    @GetMapping("/annonces_louer")
    public List<Annonce> getAnnoncesAlouer() {
        return annonceService.getAnnonceOperation("LOUER");
    }

    @GetMapping("/annonces_maison")
    public List<Annonce> getAnnoncesMaison() {
        return annonceService.getAnnonceTypeBien("MAISON");
    }

    @GetMapping("/annonces_villa")
    public List<Annonce> getAnnoncesVilla() {
        return annonceService.getAnnonceTypeBien("VILLA");
    }

    @GetMapping("/annonces_apartement")
    public List<Annonce> getAnnoncesApartement() {
        return annonceService.getAnnonceTypeBien("APPARTEMENT");
    }

    @PostMapping("/filtre")
    public List<Annonce> filtrerAnnonces(@RequestBody FiltreAnnonceRequest filtreRequest) {
        return annonceService.filtrerAnnonces(
                filtreRequest.getTypeBien(),
                filtreRequest.getOperationType(),
                filtreRequest.getMinSurface(),
                filtreRequest.getMaxSurface(),
                filtreRequest.getMinPrix(),
                filtreRequest.getMaxPrix(),
                filtreRequest.getCommuneId()
        );
    }

    @PostMapping("/filtre_intermediaire")
    public List<Annonce> filtrerAnnoncesIntermediaire(@RequestBody FiltreAnnonceRequest filtreRequest) {
        return annonceService.filtrerAnnoncesIntermediaire(
                filtreRequest.getTypeBien(),
                filtreRequest.getOperationType(),
                filtreRequest.getMinSurface(),
                filtreRequest.getMaxSurface(),
                filtreRequest.getMinPrix(),
                filtreRequest.getMaxPrix(),
                filtreRequest.getCommuneId()
        );
    }

    @PostMapping("/filtre_Annonceur_mobile")
    public List<FiltreAnnonceResponse> filtrerAnnoncesAnnonceurMobile(@RequestBody FiltreAnnonceRequest filtreRequest) {
        return annonceService.filtrerAnnoncesAnnonceurMobile(
                filtreRequest.getTypeBien(),
                filtreRequest.getOperationType(),
                filtreRequest.getMinSurface(),
                filtreRequest.getMaxSurface(),
                filtreRequest.getMinPrix(),
                filtreRequest.getMaxPrix(),
                filtreRequest.getCommuneId()
        );
    }


    @GetMapping("/communes/{commune}")
    List<Annonce> Get_annonces_for_commune(@PathVariable String commune){
        return  annonceService.findAnnoncesforCommune(commune);
    }
/*
    @GetMapping("/statistique_region")
    public List<StatistiqueRegionDto> Get_AnnoncesAndDemandesByRegion(){
        return  annonceService.findAnnoncesAndDemandesByRegion();
    }

    @GetMapping("/statistique_month")
    public List<StatistiqueMoisDto> Get_AnnoncesAndDemandesByMonth(){
        return  annonceService.getStatistiqueByMonth();
    }

 */
@GetMapping("/statistique_region")
public List<StatistiqueRegionDto> Get_AnnoncesAndDemandesByRegion(@RequestParam(name = "annee", required = false) Integer annee) {
    if (annee != null) {
        return annonceService.findAnnoncesAndDemandesByRegion(annee);
    } else {
        return annonceService.findAnnoncesAndDemandesByRegion(annonceService.findLastYearAvailable()); // If no year provided, fetch for default logic
    }
}

    @GetMapping("/statistique_month")
    public List<StatistiqueMoisDto> Get_AnnoncesAndDemandesByMonth(@RequestParam(name = "annee", required = false) Integer annee) {
        if (annee != null) {
            return annonceService.getStatistiqueByMonth(annee);
        } else {
            return annonceService.getStatistiqueByMonth(annonceService.findLastYearAvailable()); // If no year provided, fetch for default logic
        }
    }

    @GetMapping("/Nb_typeDeBien")
    public List<BienStatistiqueDto> GetNombreTotalParTypeBien(){
        return  annonceService.getNombreTotalParTypeBien();
    }

    @GetMapping("/allYears")
    public List<Integer> findAllYears (){
        return  annonceService.findAllYears();
    }




    // get Annonce by annonceur
    @GetMapping("/Annonceur/{annonceur_id}")
    List<Annonce> Get_annonces_by_annonceur(@PathVariable long annonceur_id){
        return  annonceService.getAllAnnoncesByAnnonceur(annonceur_id);
    }

}
