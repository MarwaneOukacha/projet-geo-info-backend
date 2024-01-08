package com.geoinfo.TransactionApp.services;

import com.geoinfo.TransactionApp.dtos.CommuneDto;
import com.geoinfo.TransactionApp.entities.Commune;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommuneService {

    public List<String> findDistinctRegions();

    public List<String> findDistinctProvincesByRegion(String regionName);

    public List<String> findDistinctCommunesByProvince(String provinceName);

    public List<CommuneDto> findFirst10ByCommuneContaining(String debutNom);



}
