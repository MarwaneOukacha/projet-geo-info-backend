package com.geoinfo.TransactionApp.controllers;

import com.geoinfo.TransactionApp.entities.Annonce;
import com.geoinfo.TransactionApp.entities.TypeBien;
import com.geoinfo.TransactionApp.services.TypeBienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biens")
public class TypeBienController {
    @Autowired
    private  TypeBienService typeBienService;

    @GetMapping("type")
    public List<String> GetDistincsTypeBien(){
        return typeBienService.findDistinctBienTypes();
    }

    @PostMapping()
    public void Ajouter_type_bien(@RequestBody TypeBien typeBien){
        typeBienService.ajouterTypeBien(typeBien);
    }

    @DeleteMapping("/{type}")
    public void Supprimer_type_bien(@PathVariable String type){
        typeBienService.supprimerTypeBien(type);
    }

    @GetMapping
    List<TypeBien> GetALlTypeBien(){
        return typeBienService.getAllTypeBien();
    }

    @GetMapping("/{type}")
    TypeBien GetTypeBien(@PathVariable String type){
        return typeBienService.getTypeBien(type);
    }




}
