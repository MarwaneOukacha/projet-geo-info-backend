package com.geoinfo.TransactionApp.servicesImp;

import com.geoinfo.TransactionApp.entities.TypeOperation;
import com.geoinfo.TransactionApp.reposirory.TypeBienRepository;
import com.geoinfo.TransactionApp.reposirory.TypeOperationRepository;
import com.geoinfo.TransactionApp.services.TypeBienService;
import com.geoinfo.TransactionApp.services.TypeOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TypeOperationServiceImp implements TypeOperationService {

    @Autowired
    private TypeOperationRepository typeOperationRepository;

    @Override
    public List<String> findDistinctOperationTypes() {
        return typeOperationRepository.findDistinctOperationTypes();
    }

    @Override
    public void ajouterTypeOperation(TypeOperation typeOperation) {
        typeOperationRepository.save(typeOperation);
    }

    @Override
    public void supprimerTypeOperation(String type) {
        TypeOperation typeOperation=typeOperationRepository.findTypeOperationByType(type);
        typeOperationRepository.delete(typeOperation);

    }

    @Override
    public List<TypeOperation> getAllTypeOperation() {
        return typeOperationRepository.findAll();
    }

    @Override
    public TypeOperation getTypeOpertion(String type) {
        return typeOperationRepository.findTypeOperationByType(type);
    }
}
