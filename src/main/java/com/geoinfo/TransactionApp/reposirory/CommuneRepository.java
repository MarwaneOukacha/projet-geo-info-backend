package com.geoinfo.TransactionApp.reposirory;

import com.geoinfo.TransactionApp.dtos.CommuneDto;
import com.geoinfo.TransactionApp.entities.Commune;

import jakarta.persistence.Tuple;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommuneRepository extends JpaRepository<Commune,Long> {

    @Query("SELECT c FROM Commune c WHERE ST_Contains(c.geom, :point) = true")
    Commune findCommuneByGeometry(@Param("point") Point point);

    @Query("SELECT DISTINCT c.region FROM Commune c")
    List<String> findDistinctRegions();


    @Query("SELECT DISTINCT c.province FROM Commune c WHERE c.region = :regionName")
    List<String> findDistinctProvincesByRegion(String regionName);

    @Query("SELECT DISTINCT c.commune FROM Commune c WHERE c.province = :provinceName")
    List<String> findDistinctCommunesByProvince(String provinceName);

    @Query("SELECT new com.geoinfo.TransactionApp.dtos.CommuneDto(c.commune,c.id) FROM Commune c WHERE c.commune LIKE %:mot% ORDER BY c.commune limit 10")
    List<CommuneDto> findFirst10ByCommuneContaining(String mot);

}


