package com.geoinfo.TransactionApp.controllers;

import com.geoinfo.TransactionApp.dtos.AnnonceDemandesResponse;
import com.geoinfo.TransactionApp.dtos.LoginRequest;
import com.geoinfo.TransactionApp.entities.User_role.Intermediaire;
import com.geoinfo.TransactionApp.entities.Utilisateur;
import com.geoinfo.TransactionApp.reposirory.User_repository.IntermediaireRepository;
import com.geoinfo.TransactionApp.services.EmailService;
import com.geoinfo.TransactionApp.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private IntermediaireRepository intermediaireRepository;


    @PostMapping
    public void Ajouter_utilisateur(@RequestBody Utilisateur utilisateur){
        Intermediaire intermediaire =new Intermediaire();
        intermediaire.setEmail(utilisateur.getEmail());
        intermediaire.setPassword(utilisateur.getPassword());
        intermediaire.setNom(utilisateur.getNom());
        intermediaireRepository.save(intermediaire);

    }

    @DeleteMapping("/{id}")
    public void Supprimer_utilisateur(@PathVariable Long id){
        utilisateurService.supprimer_utilisateur(id);
    }

    @PutMapping("/{id}")
    public void Modifier_utilisateur(@RequestBody Utilisateur utilisateur,@PathVariable Long id){
        utilisateurService.modifier_utilisateur(utilisateur,id);
    }

    @GetMapping("/{id}")
    Utilisateur Get_utilisateur(@PathVariable Long id){
        return utilisateurService.get_utilisateur(id);
    }

    @GetMapping
    List<Utilisateur> GetAllUtilisateur(){
      return utilisateurService.getAllUtilisateur();
    }

    @GetMapping("/intermediaires")
    List<Intermediaire> GetAllIntermediaires(){
        return utilisateurService.getAllIntermediaire();
    }

    @PostMapping("/intermediaires")
    public void Ajouter_intermediaire( @RequestBody Intermediaire intermediaire){
        utilisateurService.ajouter_intermediaire(intermediaire);
    }

    @PostMapping("/login")
    public Utilisateur Login(@RequestBody LoginRequest request){
        return utilisateurService.login(request.getEmail(), request.getPassword());
    }

    @GetMapping("/citoyen_demandeur/{id}")
    public List<AnnonceDemandesResponse> Citoyen_demandeur(@PathVariable Long id){
        return utilisateurService.citoyens_demandeur(id);
    }



    @PostMapping("/create_account_mobile")
    public Utilisateur Ajouter_utilisateur_mobile(@RequestBody Utilisateur utilisateur){
        return utilisateurService.ajouter_utilisateur_mobile(utilisateur);
    }

    @PostMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable String email) {
        Utilisateur user = utilisateurService.get_utilisateur_by_email_resetPassword(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        String body = "Hey " + user.getNom() + " " + user.getPrenom() + ", your password has been reset. Your new password is : " + user.getPassword();
        emailService.sendEmail(email, "Reset Password", body);
        return ResponseEntity.ok("Email sent successfully");
    }
}
