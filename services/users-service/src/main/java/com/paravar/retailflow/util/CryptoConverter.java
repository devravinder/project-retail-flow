package com.paravar.retailflow.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Converter(autoApply = false)  // ← important: apply only where needed else all fields
public class CryptoConverter implements AttributeConverter<String, String> {

    private final AesGcmEncryptionUtil encryptor;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return encryptor.decrypt(dbData);
    }
}