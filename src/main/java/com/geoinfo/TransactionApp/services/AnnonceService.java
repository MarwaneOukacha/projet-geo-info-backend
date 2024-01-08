package com.geoinfo.TransactionApp.services;

import com.geoinfo.TransactionApp.dtos.*;
import com.geoinfo.TransactionApp.entities.Annonce;
import com.geoinfo.TransactionApp.enums.BienType;
import com.geoinfo.TransactionApp.enums.Etat;
import com.geoinfo.TransactionApp.enums.OperationType;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnnonceService {

    public void ajouterAnnonce(Annonce annonce,long id);

    public void supprimerAnnonce(long id);

    public void modifierAnnonce(Annonce annonce ,long id);

    List<Annonce> getAllAnnonce();

    Annonce getAnnonce(long id);

    public void reserverAnnonce(long Id_intermediaire,long Id_annonce);

    public void libererAnnonce(long Id_annonce);

    public void archiverAnnonce(long Id_annonce);

    List<Integer> findAllYears();



    List<Annonce> getAlAnnoncesReseverByIntermediaire(long Id_intermediare) ;

    public List<AnnonceurAnnonceResponse> getAlAnnoncesReseverByIntermediaire_2(long Id_intermediare);

    public List<AnnonceurAnnonceResponse> getAlAnnoncesRejeterByIntermediaire_2(long Id_intermediare);

    public List<AnnonceurAnnonceResponse> getAlAnnoncesArchiverByIntermediaire(long id);

    List<Annonce> getAlAnnoncesRejeterByIntermediaire(long Id_intermediare) ;



    List<Annonce> getAllAnnoncesRejeteForCitoyen(long Id_citoyen);

    public void PublierAnnonce(long AnnonceID);

    public void RejeterAnnonce(long AnnonceID,String motifRejet);

    List<Annonce> getAllAnnoncesPasEncoreReserve();

    public List<AnnonceurAnnonceResponse> getAllAnnoncesPasEncoreReserve_2();

    List<Annonce> getAllAnnoncesPublie();

    public List<List<Annonce>> getAnnoncesByPriceCategories();

    public List<List<Annonce>> getAnnoncesBySurfaceCategories();

    List<Annonce> getAnnonceOperation(String operationType);

    List<Annonce> getAnnonceTypeBien(String bienType);


    //modifier celle ci
    public List<Annonce> filtrerAnnonces(String typeBien, String operationType, String minSurface, String maxSurface, String minPrix, String maxPrix,Double commune_id);
    public List<Annonce> filtrerAnnoncesIntermediaire(String typeBien, String operationType, String minSurface, String maxSurface, String minPrix, String maxPrix,Double commune_id);
    public List<FiltreAnnonceResponse> filtrerAnnoncesAnnonceurMobile(String typeBien, String operationType, String minSurface, String maxSurface, String minPrix, String maxPrix, Double commune_id);
    List<Annonce> findAnnoncesforCommune(String communeName);

    List<StatistiqueRegionDto> findAnnoncesAndDemandesByRegion(int annee);

    List<StatistiqueMoisDto> getStatistiqueByMonth(int annee);

    List<BienStatistiqueDto> getNombreTotalParTypeBien();

    public int findLastYearAvailable();








    List<Annonce> getAllAnnoncesByAnnonceur(long annonceur_id);


}
