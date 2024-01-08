package com.geoinfo.TransactionApp.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geoinfo.TransactionApp.entities.Commune;
import com.geoinfo.TransactionApp.entities.Demande;
import com.geoinfo.TransactionApp.entities.User_role.Citoyen;
import com.geoinfo.TransactionApp.enums.Etat;
import com.geoinfo.TransactionApp.enums.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FiltreAnnonceResponse {

    private long id;

    private int delai;

    private Double longitude ;

    private Double latitude;

    private Double surface;

    private String type_bien;

    private Double prix_bien;

    private Date date_annonce;

    private Statut statut;

    private String type_operation;

    private String description;

    private String motif_rejet;

    private Etat etat;

    private long intermediaire_id;

    private long annonceur_id;

    private String photo;

    private String justificatif;
}