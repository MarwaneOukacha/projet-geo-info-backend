package com.geoinfo.TransactionApp.services;

import com.geoinfo.TransactionApp.entities.TypeBien;
import com.geoinfo.TransactionApp.entities.TypeOperation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeOperationService {


    public List<String> findDistinctOperationTypes();

    public void ajouterTypeOperation(TypeOperation typeOperation);

    public void supprimerTypeOperation(String type);

    public List<TypeOperation> getAllTypeOperation();

    public TypeOperation getTypeOpertion(String type);

}
