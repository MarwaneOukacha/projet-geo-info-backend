package com.geoinfo.TransactionApp.reposirory;

import com.geoinfo.TransactionApp.entities.TypeBien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeBienRepository extends JpaRepository<TypeBien,String> {

    @Query("SELECT DISTINCT b.type FROM TypeBien b")
    List<String> findDistinctBienTypes();

    public TypeBien findTypeBienByType(String string);

}
