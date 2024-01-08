package com.geoinfo.TransactionApp.dtos;

import com.geoinfo.TransactionApp.entities.Annonce;
import com.geoinfo.TransactionApp.entities.User_role.Citoyen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnnonceurAnnonceResponse {

    private Citoyen annonceur;

    private Annonce annonce;
}
