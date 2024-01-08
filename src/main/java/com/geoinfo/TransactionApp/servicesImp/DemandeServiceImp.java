package com.geoinfo.TransactionApp.servicesImp;

import com.geoinfo.TransactionApp.dtos.DemandeCitoyenDto;
import com.geoinfo.TransactionApp.entities.Annonce;
import com.geoinfo.TransactionApp.entities.Demande;
import com.geoinfo.TransactionApp.entities.User_role.Citoyen;
import com.geoinfo.TransactionApp.enums.Etat;
import com.geoinfo.TransactionApp.reposirory.DemandeRepository;
import com.geoinfo.TransactionApp.reposirory.User_repository.CitoyenRepository;
import com.geoinfo.TransactionApp.reposirory.UtilisateurRepository;
import com.geoinfo.TransactionApp.services.AnnonceService;
import com.geoinfo.TransactionApp.services.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DemandeServiceImp implements DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private AnnonceService  annonceService;

    @Autowired
    private CitoyenRepository citoyenRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public void ajouter_demande(Demande demande){
        demande.setDate_demande(new Date());
        demandeRepository.save(demande);
    }

    @Override
    public void supprimer_demande(Long id) {
        demandeRepository.deleteById(id);
    }
    @Override
    public void modifier_demande(Demande demande, Long id) {

    }

    @Override
    public List<Demande> getAllDemandes(Annonce annonce) {
        return null;
    }

    @Override
    public Demande getDemande(Long id) {
        return demandeRepository.findById(id).get();
    }

    /*
    @Override
    public void faire_demande_pour_annonce(long id_annonce, Citoyen utilisateur_demandeur) {
        Annonce annonce=annonceService.getAnnonce(id_annonce);
        Demande demande=new Demande();
        demande.setAnnonce(annonce);
        demande.setDemandeur(utilisateur_demandeur);
        demandeRepository.save(demande);

    }

     */

    @Override
    public void faire_demande_pour_annonce(long id_annonce, long id_demandeur) {
        Annonce annonce=annonceService.getAnnonce(id_annonce);
        if(annonce.getEtat().equals(Etat.PUBLIEE)){
            Citoyen citoyen=citoyenRepository.findCitoyenById(id_demandeur);
            Demande demande=new Demande();
            demande.setAnnonce(annonce);
            demande.setDemandeur(citoyen);
            demande.setDate_demande(new Date());
            demandeRepository.save(demande);
        }

    }

    @Override
    public List<Demande> demandes_for_annonce(long id_annonce) {
        Annonce annonce=annonceService.getAnnonce(id_annonce);
        return demandeRepository.findDemandesByAnnonce(annonce);
    }

    @Override
    public List<DemandeCitoyenDto> demandes_for_citoyen(long id) {
        Citoyen citoyen = citoyenRepository.findCitoyenById(id);
        List<Demande> demandes = demandeRepository.findDemandesByDemandeurWithAnnonce(citoyen);

        List<DemandeCitoyenDto> demandeDTOs = new ArrayList<>();

        for (Demande demande : demandes) {

            DemandeCitoyenDto demandeCitoyenDto_courant=new DemandeCitoyenDto();

            demandeCitoyenDto_courant.setId(demande.getId());
            demandeCitoyenDto_courant.setDate_demande(demande.getDate_demande());
            demandeCitoyenDto_courant.setAnnonce(demande.getAnnonce());

            demandeDTOs.add(demandeCitoyenDto_courant);
        }

        return demandeDTOs;
    }


}
