package com.geoinfo.TransactionApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatistiqueRegionDto {
    private String nom_region;
    private Long NbAnnonce;
    private Long NbDemande;
}
