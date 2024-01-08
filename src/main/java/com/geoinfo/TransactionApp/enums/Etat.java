package com.geoinfo.TransactionApp.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public enum Etat {

    PUBLIEE,

    REJETER,

    PAS_ENCORE_TRAITEE,
    ARCHIVEE
}
