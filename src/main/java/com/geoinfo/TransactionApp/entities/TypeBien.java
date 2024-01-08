package com.geoinfo.TransactionApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Builder @AllArgsConstructor @NoArgsConstructor @Data
public class TypeBien {

    @Id
    private String type;

}
