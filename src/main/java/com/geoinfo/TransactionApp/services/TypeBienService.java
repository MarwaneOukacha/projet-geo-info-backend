package com.geoinfo.TransactionApp.services;

import com.geoinfo.TransactionApp.entities.Annonce;
import com.geoinfo.TransactionApp.entities.TypeBien;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeBienService {

    public List<String> findDistinctBienTypes();

    public void ajouterTypeBien(TypeBien typeBien);

    public void supprimerTypeBien(String type);

    public List<TypeBien> getAllTypeBien();

    public TypeBien getTypeBien(String type);


    public void modifierTypeBien( TypeBien typeBien , String type);


}
