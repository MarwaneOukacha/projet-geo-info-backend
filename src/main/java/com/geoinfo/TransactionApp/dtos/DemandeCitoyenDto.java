package com.geoinfo.TransactionApp.dtos;

import com.geoinfo.TransactionApp.entities.Annonce;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DemandeCitoyenDto {

    private Long id;
    private Date date_demande;
    private Annonce annonce;
}
