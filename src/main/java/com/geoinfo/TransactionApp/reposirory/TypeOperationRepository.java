package com.geoinfo.TransactionApp.reposirory;

import com.geoinfo.TransactionApp.entities.TypeBien;
import com.geoinfo.TransactionApp.entities.TypeOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeOperationRepository extends JpaRepository<TypeOperation,Long> {

    @Query("SELECT DISTINCT o.type FROM TypeOperation o")
    List<String> findDistinctOperationTypes();

    public TypeOperation findTypeOperationByType(String string);


}
