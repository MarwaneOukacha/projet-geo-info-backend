package com.geoinfo.TransactionApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.geoinfo.TransactionApp.serialisation.CustomGeoJsonSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.List;


@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder @Table(name = "communes")
public class Commune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonSerialize(using = CustomGeoJsonSerializer.class)
    private MultiPolygon geom;

    private String commune;

    private String province;

    private String region;

    @JsonIgnore
    @OneToMany(mappedBy = "commune", cascade = CascadeType.ALL)
    private List<Annonce> annonces;

}
