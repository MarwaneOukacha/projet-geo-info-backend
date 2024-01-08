package com.geoinfo.TransactionApp.dtos;

import com.geoinfo.TransactionApp.enums.BienType;
import com.geoinfo.TransactionApp.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FiltreAnnonceRequest {

    private String typeBien;
    private String operationType;
    private String minSurface;
    private String maxSurface;
    private String minPrix;
    private String maxPrix;
    private Double communeId;
}
