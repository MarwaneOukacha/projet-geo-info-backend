package com.geoinfo.TransactionApp.servicesImp;

import com.geoinfo.TransactionApp.dtos.AnnonceDemandesResponse;
import com.geoinfo.TransactionApp.entities.Demande;
import com.geoinfo.TransactionApp.entities.User_role.Citoyen;
import com.geoinfo.TransactionApp.entities.User_role.Intermediaire;
import com.geoinfo.TransactionApp.entities.Utilisateur;
import com.geoinfo.TransactionApp.reposirory.User_repository.CitoyenRepository;
import com.geoinfo.TransactionApp.reposirory.User_repository.IntermediaireRepository;
import com.geoinfo.TransactionApp.reposirory.UtilisateurRepository;
import com.geoinfo.TransactionApp.services.DemandeService;
import com.geoinfo.TransactionApp.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class UtilisateurServiceImp implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private IntermediaireRepository intermediaireRepository;

    @Autowired
    private CitoyenRepository citoyenRepository;

    @Autowired
    private DemandeService demandeService;
    @Override
    public void ajouter_utilisateur(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void supprimer_utilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
    @Override
    public void modifier_utilisateur(Utilisateur utilisateur, Long id) {

    }

    @Override
    public Utilisateur get_utilisateur(Long id) {
        return utilisateurRepository.findById(id).get();
    }

    @Override
    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurRepository.findAll();
    }

    @Override
    public List<Intermediaire> getAllIntermediaire() {
        return intermediaireRepository.findAll();
    }

    @Override
    public void ajouter_intermediaire(Intermediaire intermediaire) {
        intermediaireRepository.save(intermediaire);
    }


    @Override
    public Utilisateur login(String email, String password) {
       Utilisateur utilisateur =utilisateurRepository.findUtilisateurByEmailAndPassword(email,password);
       return utilisateur;
    }

    @Override
    public List<AnnonceDemandesResponse> citoyens_demandeur(long annonce_id) {
        List<Demande> demande_for_annonces=demandeService.demandes_for_annonce(annonce_id);
        List<AnnonceDemandesResponse> response=new LinkedList<>();
        for (Demande dem : demande_for_annonces ) {
            // Citoyen utilisateur = citoyenRepository.findCitoyenById(demande_for_annonces.get(i).getDemandeur().getId());
            response.add(new AnnonceDemandesResponse(dem.getDate_demande(),dem.getDemandeur()) );;
        }

        return response ;
    }



    @Override
    public Utilisateur ajouter_utilisateur_mobile(Utilisateur utilisateur) {
        Citoyen citoyen = new Citoyen();
        citoyen.setEmail(utilisateur.getEmail());
        citoyen.setPassword(utilisateur.getPassword());
        citoyen.setNom(utilisateur.getNom());
        citoyen.setPrenom(utilisateur.getPrenom());
        return citoyenRepository.save(citoyen);
    }

    @Override
    public Utilisateur get_utilisateur_by_email_resetPassword(String email) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
        if(utilisateur!=null){
            utilisateur.setPassword(generateRandomPassword(8));
            return utilisateurRepository.save(utilisateur);
        }else{
            return null;
        }
    }

    private String generateRandomPassword(int length) {
        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characterSet.length());
            builder.append(characterSet.charAt(randomIndex));
        }

        return builder.toString();
    }

}
