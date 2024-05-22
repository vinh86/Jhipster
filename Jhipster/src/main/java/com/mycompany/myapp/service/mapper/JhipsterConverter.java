package com.mycompany.myapp.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.domain.JhipsterSetting;

import jakarta.persistence.AttributeConverter;

public class JhipsterConverter implements AttributeConverter<JhipsterSetting, String>{

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JhipsterSetting attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException jpe) {
            System.err.println(jpe.getMessage());
            return null;
        }
    }

    @Override
    public JhipsterSetting convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, JhipsterSetting.class);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
