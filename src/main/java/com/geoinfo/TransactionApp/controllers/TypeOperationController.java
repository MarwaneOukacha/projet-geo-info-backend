package com.geoinfo.TransactionApp.controllers;

import com.geoinfo.TransactionApp.entities.TypeBien;
import com.geoinfo.TransactionApp.entities.TypeOperation;
import com.geoinfo.TransactionApp.services.TypeBienService;
import com.geoinfo.TransactionApp.services.TypeOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class TypeOperationController {
    @Autowired
  private TypeOperationService typeOperationService;

    @GetMapping("type")
    public List<String> GetDistincsTypeOperation(){
        return typeOperationService.findDistinctOperationTypes();
    }

    @PostMapping()
    public void Ajouter_type_operation(@RequestBody TypeOperation typeOperation){
        typeOperationService.ajouterTypeOperation(typeOperation);
    }

    @DeleteMapping("/{type}")
    public void Supprimer_type_operation(@PathVariable String type){
      typeOperationService.supprimerTypeOperation(type);
    }

    @GetMapping
    List<TypeOperation> GetALlTypeOperation(){
        return typeOperationService.getAllTypeOperation();
    }

    @GetMapping("/{type}")
    TypeOperation GetTypeOperation(@PathVariable String type){
        return typeOperationService.getTypeOpertion(type);
    }

}
