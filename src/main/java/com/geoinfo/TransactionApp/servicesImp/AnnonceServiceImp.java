package com.geoinfo.TransactionApp.servicesImp;

import com.geoinfo.TransactionApp.dtos.*;
import com.geoinfo.TransactionApp.entities.Annonce;
import com.geoinfo.TransactionApp.entities.Commune;
import com.geoinfo.TransactionApp.entities.Demande;
import com.geoinfo.TransactionApp.entities.User_role.Citoyen;
import com.geoinfo.TransactionApp.enums.BienType;
import com.geoinfo.TransactionApp.enums.Etat;
import com.geoinfo.TransactionApp.enums.OperationType;
import com.geoinfo.TransactionApp.enums.Statut;
import com.geoinfo.TransactionApp.reposirory.AnnonceRepository;
import com.geoinfo.TransactionApp.reposirory.CommuneRepository;
import com.geoinfo.TransactionApp.reposirory.User_repository.CitoyenRepository;
import com.geoinfo.TransactionApp.services.AnnonceService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.beans.FeatureDescriptor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AnnonceServiceImp implements AnnonceService {

    @Autowired()
    AnnonceRepository annonceRepository;

    @Autowired()
    CommuneRepository communeRepository;


    @Autowired()
    CitoyenRepository citoyenRepository;


    @Override
    public void ajouterAnnonce(Annonce annonce,long id) {

        Citoyen citoyen=citoyenRepository.findCitoyenById(id);
        annonce.setDate_annonce(new Date());
        annonce.setAnnonceur(citoyen);

        annonce.setStatut(Statut.EN_ATTENTE);
        annonce.setEtat(Etat.PAS_ENCORE_TRAITEE);

        Point point = new GeometryFactory().createPoint(new Coordinate(annonce.getLongitude(), annonce.getLatitude()));
        point.setSRID(4326);

        Commune commune = communeRepository.findCommuneByGeometry(point);

        System.out.println(commune);

        if (commune != null) {
            annonce.setCommune(commune);
        } else {
            System.out.println("Aucune commune trouvée pour ces coordonnées.");
        }

        annonceRepository.save(annonce);
    }

    @Override
    public void supprimerAnnonce(long id) {
        annonceRepository.deleteById(id);

    }
/*
    @Override
    public void modifierAnnonce(Annonce annonce, long id) {

    }

 */

    @Override
    public void modifierAnnonce(Annonce annonce, long id) {
        Annonce existingAnnonce = annonceRepository.findById(id).orElse(null);

        if (existingAnnonce != null) {
            BeanUtils.copyProperties(annonce, existingAnnonce, getNullPropertyNames(annonce));
            annonceRepository.save(existingAnnonce);
        }
    }

    private String[] getNullPropertyNames(Annonce source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    @Override
    public List<Annonce> getAllAnnonce() {
        return annonceRepository.findAll();
    }

    @Override
    public Annonce getAnnonce(long id) {
        return annonceRepository.findById(id).get();
    }

    @Override
    public void reserverAnnonce(long Id_intermediaire, long Id_annonce) {
        Annonce annonce=getAnnonce(Id_annonce);
        annonce.setIntermediaire_id(Id_intermediaire);
        annonce.setStatut(Statut.RESERVEE);
        annonceRepository.save(annonce);
    }

    @Override
    public void libererAnnonce( long Id_annonce) {
        Annonce annonce =getAnnonce(Id_annonce);
        annonce.setStatut(Statut.EN_ATTENTE);
        annonce.setEtat(Etat.PAS_ENCORE_TRAITEE);
        annonce.setMotif_rejet(null);
        annonce.setIntermediaire_id(-1);
        annonceRepository.save(annonce);
    }

    @Override
    public void archiverAnnonce(long Id_annonce) {
        Annonce annonce=getAnnonce(Id_annonce);
        annonce.setEtat(Etat.ARCHIVEE);
        annonceRepository.save(annonce);
    }

    @Override
    public List<Integer> findAllYears() {
        return annonceRepository.findAllYears();
    }

    @Override
    public List<Annonce> getAlAnnoncesReseverByIntermediaire(long id) {
        List<Annonce> A_Inter =annonceRepository.findByIntermediaireId(id);
        List<Annonce> Annonce_inter_reserve=new ArrayList<>();
        for(int i=0;i<A_Inter.size();i++){
            if(A_Inter.get(i).getStatut().equals(Statut.RESERVEE) && !(A_Inter.get(i).getEtat().equals(Etat.REJETER))){
                Annonce_inter_reserve.add(A_Inter.get(i));
            }
        }
        return Annonce_inter_reserve ;
    }

    @Override
    public List<AnnonceurAnnonceResponse> getAlAnnoncesReseverByIntermediaire_2(long id) {
        List<Annonce> A_Inter =annonceRepository.findByIntermediaireId(id);
        List<AnnonceurAnnonceResponse> response=new LinkedList<>();
        for (Annonce annonce : A_Inter ) {
            if(annonce.getStatut().equals(Statut.RESERVEE) && !annonce.getEtat().equals(Etat.REJETER) && !annonce.getEtat().equals(Etat.ARCHIVEE)) {
                response.add(new AnnonceurAnnonceResponse(annonce.getAnnonceur(), annonce));
                ;
            }
        }
        return response;

    }

    @Override
    public List<Annonce> getAlAnnoncesRejeterByIntermediaire(long Id_intermediare) {
        List<Annonce> A_Inter =annonceRepository.findByIntermediaireId(Id_intermediare);
        List<Annonce> Annonce_inter_rejeter=new ArrayList<>();
        for(int i=0;i<A_Inter.size();i++){
            if(A_Inter.get(i).getStatut().equals(Statut.RESERVEE) && A_Inter.get(i).getEtat().equals(Etat.REJETER)){
                Annonce_inter_rejeter.add(A_Inter.get(i));
            }
        }
        return Annonce_inter_rejeter ;
    }

    @Override
    public List<AnnonceurAnnonceResponse> getAlAnnoncesRejeterByIntermediaire_2(long Id_intermediare) {
        List<Annonce> A_Inter =annonceRepository.findByIntermediaireId(Id_intermediare);
        List<AnnonceurAnnonceResponse> response=new LinkedList<>();
        for (Annonce annonce : A_Inter ) {
            if(annonce.getStatut().equals(Statut.RESERVEE) && (annonce.getEtat().equals(Etat.REJETER))) {
                response.add(new AnnonceurAnnonceResponse(annonce.getAnnonceur(), annonce));
                ;
            }
        }
        return response;
    }

    @Override
    public List<AnnonceurAnnonceResponse> getAlAnnoncesArchiverByIntermediaire(long id) {
        List<Annonce> A_Inter =annonceRepository.findByIntermediaireId(id);
        List<AnnonceurAnnonceResponse> response=new LinkedList<>();
        for (Annonce annonce : A_Inter ) {
            if(annonce.getStatut().equals(Statut.RESERVEE) && (annonce.getEtat().equals(Etat.ARCHIVEE))) {
                response.add(new AnnonceurAnnonceResponse(annonce.getAnnonceur(), annonce));
                ;
            }
        }
        return response;
    }

    @Override
    public List<Annonce> getAllAnnoncesRejeteForCitoyen(long Id_citoyen) {
        return null;
    }

    @Override
    public void PublierAnnonce(long AnnonceID) {
        Annonce annonce=getAnnonce(AnnonceID);
        if(annonce.getStatut().equals(Statut.RESERVEE)){
            annonce.setEtat(Etat.PUBLIEE);
            annonceRepository.save(annonce);
        }
    }
    /*
    @Override
    public void RejeterAnnonce(long AnnonceID) {
        Annonce annonce=getAnnonce(AnnonceID);
        if(annonce.getStatut().equals(Statut.RESERVEE)){
            annonce.setEtat(Etat.REJETER);
            annonceRepository.save(annonce);
        }

    }

     */

    @Override
    public void RejeterAnnonce(long AnnonceID, String motifRejet) {
        Annonce annonce = getAnnonce(AnnonceID);
        if (annonce.getStatut().equals(Statut.RESERVEE)) {
            annonce.setEtat(Etat.REJETER);
            annonce.setMotif_rejet(motifRejet); // Mettre à jour le motif de rejet
            annonceRepository.save(annonce);
        }
    }

    @Override
    public List<Annonce> getAllAnnoncesPasEncoreReserve() {
        List<Annonce> annoces=annonceRepository.findAll();
        List<Annonce> Annonce_not_reserve=new ArrayList<>();
        for(int i=0;i<annoces.size();i++) {
            if (annoces.get(i).getStatut().equals(Statut.EN_ATTENTE)) {
                Annonce_not_reserve.add(annoces.get(i));
            }
        }
        return Annonce_not_reserve;
    }

    @Override
    public List<AnnonceurAnnonceResponse> getAllAnnoncesPasEncoreReserve_2() {
        List<Annonce> annonces_libres =annonceRepository.findAll();
        List<AnnonceurAnnonceResponse> response=new LinkedList<>();
        for (Annonce annonce : annonces_libres ) {
            if(annonce.getStatut().equals(Statut.EN_ATTENTE)) {
                response.add(new AnnonceurAnnonceResponse(annonce.getAnnonceur(), annonce));
            }
        }
        return response;
    }

    @Override
    public List<Annonce> getAllAnnoncesPublie() {
        List<Annonce> annoces=annonceRepository.findAll();
        List<Annonce> Annonce_publie=new ArrayList<>();
        for(int i=0;i<annoces.size();i++) {
            if (annoces.get(i).getEtat().equals(Etat.PUBLIEE)) {
                Annonce_publie.add(annoces.get(i));
            }
        }
        return Annonce_publie;
    }

    @Override
    public List<List<Annonce>> getAnnoncesByPriceCategories() {

        Double minPrice = annonceRepository.findMinPrice();
        Double maxPrice = annonceRepository.findMaxPrice();

        // Calcul des intervalles de prix
        double range = (maxPrice - minPrice) / 5;
        double currentMin = minPrice;
        double currentMax = minPrice + range;

        // Création de cinq catégories
        List<List<Annonce>> priceCategories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Annonce> category = new ArrayList<>();
            for (Annonce annonce : annonceRepository.findAll()) {
                double price = annonce.getPrix_bien();
                if (annonce.getEtat().equals(Etat.PUBLIEE) && price >= currentMin && price <= currentMax) {
                    category.add(annonce);
                }
            }
            priceCategories.add(category);
            currentMin = currentMax;
            currentMax += range;
        }

        return priceCategories;
    }

    @Override
    public List<List<Annonce>> getAnnoncesBySurfaceCategories() {
        Double minSurface = annonceRepository.findMinSurface();
        Double maxSurface = annonceRepository.findMaxSurface();

        // Calcul des intervalles de prix
        double range = (maxSurface - minSurface) / 5;
        double currentMin = minSurface;
        double currentMax = minSurface + range;

        // Création de cinq catégories
        List<List<Annonce>> SurfaceCategories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Annonce> category = new ArrayList<>();
            for (Annonce annonce : annonceRepository.findAll()) {
                double surface = annonce.getSurface();
                if (annonce.getEtat().equals(Etat.PUBLIEE) && surface >= currentMin && surface <= currentMax) {
                    category.add(annonce);
                }
            }
            SurfaceCategories.add(category);
            currentMin = currentMax;
            currentMax += range;
        }

        return SurfaceCategories;
    }

    @Override
    public List<Annonce> getAnnonceOperation(String operationType) {
        return annonceRepository.AnnoncePublieTypeOperation(operationType,Etat.PUBLIEE);
    }


    @Override
    public List<Annonce> getAnnonceTypeBien(String bienType) {
        return annonceRepository.AnnoncePublieTypeBien(bienType,Etat.PUBLIEE);
    }

    /*
    @Override
    public List<Annonce> filtrerAnnonces(String typeBien, String operationType, Double minSurface, Double maxSurface, Double minPrix, Double maxPrix) {
        List<Annonce> annoncesFiltrees = new ArrayList<>();

        // Récupérer toutes les annonces correspondant au typeBien et operationType
        List<Annonce> annoncesParTypeEtOperation = annonceRepository.AnnoncePublieTypeBienAndOperation(typeBien, operationType, Etat.PUBLIEE);

        // Appliquer les filtres de surface et de prix en une seule boucle
        for (Annonce annonce : annoncesParTypeEtOperation) {
            Double surface = annonce.getSurface();
            Double prix = annonce.getPrix_bien();

            // Vérifier si les valeurs des paramètres sont nulles (non renseignées)
            boolean surfaceInRange = (minSurface == null || (surface >= minSurface)) && (maxSurface == null || surface <= maxSurface);
            boolean prixInRange = (minPrix == null || (prix >= minPrix)) && (maxPrix == null || prix <= maxPrix);

            // Filtrer par surface et prix uniquement si les valeurs ne sont pas nulles (non renseignées)
            if (surfaceInRange && prixInRange) {
                annoncesFiltrees.add(annonce);
            }
        }

        return annoncesFiltrees;
    }

     */

    /*
    @Override
    public List<Annonce> filtrerAnnonces(String typeBien, String operationType, Double minSurface, Double maxSurface, Double minPrix, Double maxPrix) {
        List<Annonce> annoncesFiltrees = annonceRepository.getAllPublishedAnnonces();


        if (typeBien != null) {
            annoncesFiltrees = annonceRepository.AnnoncePublieTypeBien(typeBien, Etat.PUBLIEE);
        } else if (operationType != null) {
            annoncesFiltrees = annonceRepository.AnnoncePublieTypeOperation(operationType, Etat.PUBLIEE);
        } else {
            if (minSurface != null || maxSurface != null) {
                annoncesFiltrees = filtrerParSurface(annoncesFiltrees, minSurface, maxSurface);
            }
            if (minPrix != null || maxPrix != null) {
                annoncesFiltrees = filtrerParPrix(annoncesFiltrees, minPrix, maxPrix);
            }
        }



        return annoncesFiltrees;
    }

    public List<Annonce> filtrerParSurface(List<Annonce> annonces, Double minSurface, Double maxSurface) {
        List<Annonce> annoncesFiltrees = new ArrayList<>();

        for (Annonce annonce : annonces) {
            Double surface = annonce.getSurface();
            boolean surfaceInRange = (minSurface == null || (surface >= minSurface)) && (maxSurface == null || surface <= maxSurface);
            if (surfaceInRange) {
                annoncesFiltrees.add(annonce);
            }
        }

        return annoncesFiltrees;
    }

    public List<Annonce> filtrerParPrix(List<Annonce> annonces, Double minPrix, Double maxPrix) {
        List<Annonce> annoncesFiltrees = new ArrayList<>();

        for (Annonce annonce : annonces) {
            Double prix = annonce.getPrix_bien();
            boolean prixInRange = (minPrix == null || (prix >= minPrix)) && (maxPrix == null || prix <= maxPrix);
            if (prixInRange) {
                annoncesFiltrees.add(annonce);
            }
        }

        return annoncesFiltrees;
    }

     */

    public List<Annonce> filtrerAnnonces(String typeBien, String operationType, String minSurface, String maxSurface, String minPrix, String maxPrix,Double commune_id) {
      return  annonceRepository.filtrerAnnoncesPublic(typeBien,operationType,minSurface,maxSurface,minPrix,maxPrix,commune_id,Etat.PUBLIEE);
    }

    @Override
    public List<Annonce> filtrerAnnoncesIntermediaire(String typeBien, String operationType, String minSurface, String maxSurface, String minPrix, String maxPrix, Double commune_id) {
        return annonceRepository.filtrerAnnoncesIntermediaire(typeBien,operationType,minSurface,maxSurface,minPrix,maxPrix,commune_id);
    }

    @Override
    public List<FiltreAnnonceResponse> filtrerAnnoncesAnnonceurMobile(String typeBien, String operationType, String minSurface, String maxSurface, String minPrix, String maxPrix, Double commune_id) {
        List<Annonce> annonces = annonceRepository.filtrerAnnoncesIntermediaire(typeBien,operationType,minSurface,maxSurface,minPrix,maxPrix,commune_id);
        List<FiltreAnnonceResponse> filtreAnnoncesResponseList = new ArrayList<>();

        for (Annonce annonce : annonces) {
            FiltreAnnonceResponse filtreAnnonceResponse = new FiltreAnnonceResponse();

            filtreAnnonceResponse.setLongitude(annonce.getLongitude());
            filtreAnnonceResponse.setLatitude(annonce.getLatitude());
            filtreAnnonceResponse.setSurface(annonce.getSurface());
            filtreAnnonceResponse.setType_bien(annonce.getType_bien());
            filtreAnnonceResponse.setPrix_bien(annonce.getPrix_bien());
            filtreAnnonceResponse.setDate_annonce(annonce.getDate_annonce());
            filtreAnnonceResponse.setStatut(annonce.getStatut());
            filtreAnnonceResponse.setType_operation(annonce.getType_operation());
            filtreAnnonceResponse.setDescription(annonce.getDescription());
            filtreAnnonceResponse.setMotif_rejet(annonce.getMotif_rejet());
            filtreAnnonceResponse.setEtat(annonce.getEtat());
            filtreAnnonceResponse.setIntermediaire_id(annonce.getIntermediaire_id());


            filtreAnnonceResponse.setAnnonceur_id(annonce.getAnnonceur().getId());

            filtreAnnonceResponse.setPhoto(annonce.getPhoto());
            filtreAnnonceResponse.setJustificatif(annonce.getJustificatif());

            filtreAnnonceResponse.setId(annonce.getId());
            filtreAnnonceResponse.setDelai(annonce.getDelai());


            filtreAnnoncesResponseList.add(filtreAnnonceResponse);
        }

        return filtreAnnoncesResponseList;
    }


    @Override
    public List<Annonce> findAnnoncesforCommune(String communeName) {
        return annonceRepository.findAnnoncesInCommune(communeName);
    }
/*
    @Override
    public List<StatistiqueRegionDto> findAnnoncesAndDemandesByRegion() {
        return annonceRepository.getStatistiqueByRegion();
    }

    @Override
    public List<StatistiqueMoisDto> getStatistiqueByMonth() {
        return annonceRepository.getStatistiqueByMonth();
    }

 */

    @Override
    public List<BienStatistiqueDto> getNombreTotalParTypeBien() {
        return annonceRepository.getNombreTotalParTypeBien();
    }

    @Override
    public int findLastYearAvailable() {
        return annonceRepository.findLastYearAvailable();
    }


    // Méthode pour obtenir les statistiques par région pour une année donnée
    public List<StatistiqueRegionDto> findAnnoncesAndDemandesByRegion(int annee) {
        return annonceRepository.getStatistiqueByRegion(annee);
    }

    // Méthode pour obtenir les statistiques par mois pour une année donnée
    public List<StatistiqueMoisDto> getStatistiqueByMonth(int annee) {
        return annonceRepository.getStatistiqueByMonth(annee);
    }



    @Override
    public List<Annonce> getAllAnnoncesByAnnonceur(long annonceur_id) {
        return annonceRepository.findByAnnonceur_Id(annonceur_id);
    }

}
