package com.geoinfo.TransactionApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BienStatistiqueDto {

    private String type_bien;
    private long nombre;
}
