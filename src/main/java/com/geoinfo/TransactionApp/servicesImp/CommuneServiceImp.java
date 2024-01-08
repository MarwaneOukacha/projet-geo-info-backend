package com.geoinfo.TransactionApp.servicesImp;

import com.geoinfo.TransactionApp.dtos.CommuneDto;
import com.geoinfo.TransactionApp.entities.Commune;
import com.geoinfo.TransactionApp.reposirory.CommuneRepository;
import com.geoinfo.TransactionApp.services.CommuneService;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommuneServiceImp implements CommuneService {
    @Autowired
    private CommuneRepository communeRepository;
    @Override
    public List<String> findDistinctRegions() {
        return communeRepository.findDistinctRegions();
    }

    @Override
    public List<String> findDistinctProvincesByRegion(String regionName) {
        return communeRepository.findDistinctProvincesByRegion(regionName);
    }

    @Override
    public List<String> findDistinctCommunesByProvince(String provinceName) {
        return communeRepository.findDistinctCommunesByProvince(provinceName);
    }

    @Override
    public List<CommuneDto> findFirst10ByCommuneContaining(String debutNom) {
        return communeRepository.findFirst10ByCommuneContaining(debutNom);
    }
}
