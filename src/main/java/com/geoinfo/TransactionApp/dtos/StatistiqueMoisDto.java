package com.geoinfo.TransactionApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatistiqueMoisDto {
    private int mois;
    private int annee;
    private long nombreAnnonces;
    private long nombreDemandes;
}
