package com.geoinfo.TransactionApp.controllers;

import com.geoinfo.TransactionApp.dtos.CommuneDto;
import com.geoinfo.TransactionApp.entities.Commune;
import com.geoinfo.TransactionApp.reposirory.CommuneRepository;
import com.geoinfo.TransactionApp.services.CommuneService;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/communes")
@RestController
public class CommuneController {

    @Autowired
    private CommuneService communeService;

    @Autowired
    private CommuneRepository communeRepository;

    @GetMapping
    List<Commune> communeList(){
        return communeRepository.findAll();
    }

    @GetMapping("/region")
    List<String> Region(){
     return   communeService.findDistinctRegions();
    }

    @GetMapping("/region/{region}")
    List<String> ProvinceForRegion(@PathVariable String region){
        return   communeService.findDistinctProvincesByRegion(region);
    }

    @GetMapping("/region/province/{province}")
    List<String> CommuneForProvince(@PathVariable String province){
        return   communeService.findDistinctCommunesByProvince(province);
    }

    @GetMapping("/search/{debutNom}")
    List<CommuneDto> SearchCommune10(@PathVariable String debutNom){
        return   communeService.findFirst10ByCommuneContaining(debutNom);
    }
}
