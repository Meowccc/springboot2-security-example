package com.example.demo.repository.converter;

import com.example.demo.enums.GenericStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author meow
 */
@Converter(autoApply = true)
public class GenericStatusConverter implements AttributeConverter<GenericStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(GenericStatusEnum attribute) {
        return attribute.getValue();
    }

    @Override
    public GenericStatusEnum convertToEntityAttribute(String dbData) {
        return GenericStatusEnum.findByValue(dbData).orElse(null);
    }
}
