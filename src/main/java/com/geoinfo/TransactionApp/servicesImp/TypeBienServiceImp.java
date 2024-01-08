package com.geoinfo.TransactionApp.servicesImp;

import com.geoinfo.TransactionApp.entities.TypeBien;
import com.geoinfo.TransactionApp.reposirory.TypeBienRepository;
import com.geoinfo.TransactionApp.services.TypeBienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TypeBienServiceImp implements TypeBienService {

    @Autowired
    private TypeBienRepository typeBienRepository;
    @Override
    public List<String> findDistinctBienTypes() {
        return typeBienRepository.findDistinctBienTypes();
    }

    @Override
    public void ajouterTypeBien(TypeBien typeBien) {
        typeBienRepository.save(typeBien);
    }

    @Override
    public void supprimerTypeBien(String type) {
        TypeBien typeBien=typeBienRepository.findTypeBienByType(type);
        typeBienRepository.delete(typeBien);

    }

    @Override
    public List<TypeBien> getAllTypeBien() {
        return typeBienRepository.findAll();
    }

    @Override
    public TypeBien getTypeBien(String type) {
        return typeBienRepository.findTypeBienByType(type);
    }

    @Override
    public void modifierTypeBien(TypeBien typeBien, String type) {


    }


}
