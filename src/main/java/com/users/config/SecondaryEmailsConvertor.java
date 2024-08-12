package com.users.config;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SecondaryEmailsConvertor implements AttributeConverter<List<String>, String> {
        @Override
        public String convertToDatabaseColumn(List<String> attribute) {
            if (attribute == null){
                return null;
            }
            return attribute.stream().reduce((x, y) -> x + "," + y).orElse("");
        }

        @Override
        public List<String> convertToEntityAttribute(String dbColumnValue) {
            if (dbColumnValue == null){
                return null;
            }
            String[] typeArray = dbColumnValue.split(",");
            List<String> typeList = Arrays.stream(typeArray).collect(Collectors.toList());
            return typeList;
        }

}
