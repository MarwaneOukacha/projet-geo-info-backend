package com.geoinfo.TransactionApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class DemandeForAnnonceDto {
    private long id_annonce;
    private long id_demmandeur;
}
