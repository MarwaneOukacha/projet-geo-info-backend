package com.geoinfo.TransactionApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.geoinfo.TransactionApp.serialisation.CustomGeoJsonSerializer;
import org.locationtech.jts.geom.Geometry;
import org.springframework.context.annotation.Bean;

public class CommuneConfig {

        @Bean
        public ObjectMapper getObjectMapper() {
                ObjectMapper objectMapper = new ObjectMapper();
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addSerializer(Geometry.class, new CustomGeoJsonSerializer());
                objectMapper.registerModule(simpleModule);
                return objectMapper;
        }
}
