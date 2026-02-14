package com.paravar.retailflow.util;

import com.paravar.retailflow.config.SpringContextHolder;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;


@Converter(autoApply = false)  // ← important: apply only where needed
public class CryptoConverter implements AttributeConverter<String, String> {

    // @RequiredArgsConstructor won't work -> JPA uses reflection to create object ( this is not spring bean )
    private AesGcmEncryptionUtil encryptor;// = SpringContextHolder.getBean(AesGcmEncryptionUtil.class);;

    @Autowired
    public void setEncryptor(AesGcmEncryptionUtil encryptor) {
        this.encryptor = encryptor;
    }
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return encryptor.decrypt(dbData);
    }
}