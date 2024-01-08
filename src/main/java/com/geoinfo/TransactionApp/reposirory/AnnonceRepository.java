package com.geoinfo.TransactionApp.reposirory;


import com.geoinfo.TransactionApp.dtos.BienStatistiqueDto;
import com.geoinfo.TransactionApp.dtos.StatistiqueMoisDto;
import com.geoinfo.TransactionApp.dtos.StatistiqueRegionDto;
import com.geoinfo.TransactionApp.entities.Annonce;
import com.geoinfo.TransactionApp.enums.BienType;
import com.geoinfo.TransactionApp.enums.Etat;
import com.geoinfo.TransactionApp.enums.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce,Long> {

    List<Annonce> findByDescriptionContaining(String mc);


    @Query("select a from Annonce a where a.description like :x")
    List<Annonce> search(@Param("x")String mc);

    @Query("SELECT COUNT(a) FROM Annonce a")
    int countTotalAnnonces();

    @Query("SELECT a FROM Annonce a WHERE a.intermediaire_id = :intermediaireId")
    List<Annonce> findByIntermediaireId(@Param("intermediaireId") long intermediaireId);

    @Query("SELECT MIN(a.prix_bien) FROM Annonce a")
    Double findMinPrice();

    @Query("SELECT MAX(a.prix_bien) FROM Annonce a")
    Double findMaxPrice();

    @Query("SELECT MIN(a.surface) FROM Annonce a")
    Double findMinSurface();

    @Query("SELECT MAX(a.surface) FROM Annonce a")
    Double findMaxSurface();

    @Query("SELECT a FROM Annonce a WHERE a.type_operation = :type_achat AND a.etat = :etat")
    List<Annonce> AnnoncePublieTypeOperation(@Param("type_achat") String type_achat, @Param("etat") Etat etat);

    @Query("SELECT a FROM Annonce a WHERE a.type_bien = :type_bien AND a.etat = :etat")
    List<Annonce> AnnoncePublieTypeBien(@Param("type_bien") String type_bien, @Param("etat") Etat etat);

    @Query("SELECT a FROM Annonce a WHERE a.type_bien = :typeBien AND a.type_operation = :operationType AND a.etat = :etat")
    List<Annonce> AnnoncePublieTypeBienAndOperation(@Param("typeBien") String typeBien,
                                                    @Param("operationType") String operationType,
                                                    @Param("etat") Etat etat);


    @Query("SELECT a FROM Annonce a WHERE ST_Within(ST_SetSRID(ST_MakePoint(a.longitude, a.latitude), 4326), " +
            "(SELECT c.geom FROM Commune c WHERE c.commune = :communeName)) = true")
    List<Annonce> findAnnoncesInCommune(@Param("communeName") String communeName);

    @Query("SELECT a FROM Annonce a WHERE a.etat = 'PUBLIEE'")
    List<Annonce> getAllPublishedAnnonces();

    /*
    @Query("SELECT a FROM Annonce a " +
            "WHERE (:typeBien IS NULL OR a.type_bien = :typeBien) " +
            "AND (:operationType IS NULL OR a.type_operation = :operationType) " +
            "AND (:minSurface IS NULL OR a.surface >= :minSurface) " +
            "AND (:maxSurface IS NULL OR a.surface <= :maxSurface) " +
            "AND (:minPrix IS NULL OR a.prix_bien >= :minPrix) " +
            "AND (:maxPrix IS NULL OR a.prix_bien <= :maxPrix) " +
            "AND a.etat = :etat")
    List<Annonce> filtrerAnnonces(
            @Param("typeBien") String typeBien,
            @Param("operationType") String operationType,
            @Param("minSurface") Double minSurface,
            @Param("maxSurface") Double maxSurface,
            @Param("minPrix") Double minPrix,
            @Param("maxPrix") Double maxPrix,
            @Param("etat") Etat etat
    );


    @Query("SELECT a FROM Annonce a " +
            "WHERE (:typeBien IS NULL OR a.type_bien = :typeBien) " +
            "AND (:operationType IS NULL OR a.type_operation = :operationType) " +
            "AND (:minSurface IS NULL OR a.surface >= :minSurface) " +
            "AND (:maxSurface IS NULL OR a.surface <= :maxSurface) " +
            "AND (:minPrix IS NULL OR a.prix_bien >= :minPrix) " +
            "AND (:maxPrix IS NULL OR a.prix_bien <= :maxPrix) " +
            "AND (:communeId IS NULL OR a.commune.id = :communeId) " + // Filtering by commune_id
            "AND a.etat = :etat")
    List<Annonce> filtrerAnnonces(
            @Param("typeBien") String typeBien,
            @Param("operationType") String operationType,
            @Param("minSurface") Double minSurface,
            @Param("maxSurface") Double maxSurface,
            @Param("minPrix") Double minPrix,
            @Param("maxPrix") Double maxPrix,
            @Param("communeId") Long communeId, // Assuming communeId is of type Long
            @Param("etat") Etat etat
    );

     */
    @Query("SELECT a FROM Annonce a " +
            "WHERE (COALESCE(NULLIF(TRIM(:typeBien), ''), NULL) IS NULL OR a.type_bien = :typeBien) " +
            "AND (COALESCE(NULLIF(TRIM(:operationType), ''), NULL) IS NULL OR a.type_operation = :operationType) " +
            "AND (COALESCE(CAST(NULLIF(TRIM(:minSurface), '') AS Double), -1.0) = -1.0 OR a.surface >= CAST(:minSurface AS Double)) " +
            "AND (COALESCE(CAST(NULLIF(TRIM(:maxSurface), '') AS Double), -1.0) = -1.0 OR a.surface <= CAST(:maxSurface AS Double)) " +
            "AND (COALESCE(CAST(NULLIF(TRIM(:minPrix), '') AS Double), -1.0) = -1.0 OR a.prix_bien >= CAST(:minPrix AS Double)) " +
            "AND (COALESCE(CAST(NULLIF(TRIM(:maxPrix), '') AS Double), -1.0) = -1.0 OR a.prix_bien <= CAST(:maxPrix AS Double)) " +
            "AND (:communeId IS NULL OR a.commune.id = :communeId) " + // Comparaison directe sans conversion en Long
            "AND a.etat = :etat")
    List<Annonce> filtrerAnnoncesPublic(
            @Param("typeBien") String typeBien,
            @Param("operationType") String operationType,
            @Param("minSurface") String minSurface,
            @Param("maxSurface") String maxSurface,
            @Param("minPrix") String minPrix,
            @Param("maxPrix") String maxPrix,
            @Param("communeId") Double communeId,
            @Param("etat") Etat etat
    );

    @Query("SELECT a FROM Annonce a " +
            "WHERE (COALESCE(NULLIF(TRIM(:typeBien), ''), NULL) IS NULL OR a.type_bien = :typeBien) " +
            "AND (COALESCE(NULLIF(TRIM(:operationType), ''), NULL) IS NULL OR a.type_operation = :operationType) " +
            "AND (COALESCE(CAST(NULLIF(TRIM(:minSurface), '') AS Double), -1.0) = -1.0 OR a.surface >= CAST(:minSurface AS Double)) " +
            "AND (COALESCE(CAST(NULLIF(TRIM(:maxSurface), '') AS Double), -1.0) = -1.0 OR a.surface <= CAST(:maxSurface AS Double)) " +
            "AND (COALESCE(CAST(NULLIF(TRIM(:minPrix), '') AS Double), -1.0) = -1.0 OR a.prix_bien >= CAST(:minPrix AS Double)) " +
            "AND (COALESCE(CAST(NULLIF(TRIM(:maxPrix), '') AS Double), -1.0) = -1.0 OR a.prix_bien <= CAST(:maxPrix AS Double)) " +
            "AND (:communeId IS NULL OR a.commune.id = :communeId) ")
    List<Annonce> filtrerAnnoncesIntermediaire(
            @Param("typeBien") String typeBien,
            @Param("operationType") String operationType,
            @Param("minSurface") String minSurface,
            @Param("maxSurface") String maxSurface,
            @Param("minPrix") String minPrix,
            @Param("maxPrix") String maxPrix,
            @Param("communeId") Double communeId

    );

/*
    @Query("SELECT NEW com.geoinfo.TransactionApp.dtos.StatistiqueRegionDto(c.region, COUNT(a), COUNT(d)) " +
            "FROM Annonce a " +
            "JOIN a.commune c " +
            "LEFT JOIN a.demandeForAnnonce d " +
            "GROUP BY c.region")
    List<StatistiqueRegionDto> getStatistiqueByRegion();


    @Query("SELECT NEW com.geoinfo.TransactionApp.dtos.StatistiqueMoisDto(MONTH(a.date_annonce), YEAR(a.date_annonce), COUNT(a), COUNT(d)) " +
            "FROM Annonce a " +
            "LEFT JOIN a.demandeForAnnonce d " +
            "GROUP BY MONTH(a.date_annonce), YEAR(a.date_annonce) " +
            "ORDER BY YEAR(a.date_annonce), MONTH(a.date_annonce)")
    List<StatistiqueMoisDto> getStatistiqueByMonth();

 */

    @Query("SELECT NEW com.geoinfo.TransactionApp.dtos.BienStatistiqueDto(a.type_bien, COUNT(a)) " +
            "FROM Annonce a " +
            "WHERE a.etat != com.geoinfo.TransactionApp.enums.Etat.ARCHIVEE " +
            "GROUP BY a.type_bien")
    List<BienStatistiqueDto> getNombreTotalParTypeBien();

/*

    @Query("SELECT NEW com.geoinfo.TransactionApp.dtos.StatistiqueRegionDto(c.region, COUNT(a), COUNT(d)) " +
            "FROM Annonce a " +
            "JOIN a.commune c " +
            "LEFT JOIN a.demandeForAnnonce d " +
            "WHERE YEAR(a.date_annonce) = :annee " + // Filtrer par année
            "GROUP BY c.region")
    List<StatistiqueRegionDto> getStatistiqueByRegion(@Param("annee") int annee);

 */
@Query("SELECT NEW com.geoinfo.TransactionApp.dtos.StatistiqueRegionDto(c.region, COUNT(a), COUNT(d)) " +
        "FROM Annonce a " +
        "JOIN a.commune c " +
        "LEFT JOIN a.demandeForAnnonce d " +
        "WHERE YEAR(a.date_annonce) = :annee " + // Filtrer par année
        "AND a.etat != com.geoinfo.TransactionApp.enums.Etat.ARCHIVEE " + // Ajouter la condition d'état
        "GROUP BY c.region")
List<StatistiqueRegionDto> getStatistiqueByRegion(@Param("annee") int annee);


    @Query("SELECT NEW com.geoinfo.TransactionApp.dtos.StatistiqueMoisDto(MONTH(a.date_annonce), YEAR(a.date_annonce), COUNT(a), COUNT(d)) " +
            "FROM Annonce a " +
            "LEFT JOIN a.demandeForAnnonce d " +
            "WHERE YEAR(a.date_annonce) = :annee " + // Filter by year
            "AND a.etat != com.geoinfo.TransactionApp.enums.Etat.ARCHIVEE " + // Ajouter la condition d'état
            "GROUP BY MONTH(a.date_annonce), YEAR(a.date_annonce) " +
            "ORDER BY YEAR(a.date_annonce), MONTH(a.date_annonce)")
    List<StatistiqueMoisDto> getStatistiqueByMonth(@Param("annee") int annee);

    @Query("SELECT MAX(YEAR(a.date_annonce)) FROM Annonce a")
    int findLastYearAvailable();

    @Query("SELECT DISTINCT YEAR(a.date_annonce) FROM Annonce a ORDER BY YEAR(a.date_annonce)")
    List<Integer> findAllYears();






    List<Annonce> findByAnnonceur_Id(@Param("id") long id);

}
