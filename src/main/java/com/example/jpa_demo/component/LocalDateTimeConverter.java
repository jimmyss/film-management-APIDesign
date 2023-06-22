package com.example.jpa_demo.component;

import jakarta.persistence.AttributeConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @projectName: apidesign
 * @package: com.example.jpa_demo.component
 * @className: LocalDateTimeConverter
 * @author: Dushimao
 * @description: TODO
 * @date: 2023/6/22 12:25
 * @version: 1.0
 */
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp>
{
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
