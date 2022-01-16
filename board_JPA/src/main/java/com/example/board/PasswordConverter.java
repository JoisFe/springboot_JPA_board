package com.example.board;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.print.attribute.Attribute;

@Convert
public class PasswordConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        return Seed.encrypt(attribute);
    }
    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return Seed.decrypt(dbData);
    }
}
