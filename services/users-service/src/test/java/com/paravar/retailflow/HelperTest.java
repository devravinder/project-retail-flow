package com.paravar.retailflow;

import com.paravar.retailflow.util.AesGcmEncryptionUtil;
import com.paravar.retailflow.util.HashingUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class HelperTest {

    private final AesGcmEncryptionUtil encryptor;
    private final ApplicationProperties properties;
    private final HashingUtil hashUtil;
//    private  final Environment environment;


    String getKey(int length){
        byte[] key = new byte[length];
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
    @Test
    @EnabledIf(
            expression = "#{environment.acceptsProfiles('local')}",
            loadContext = true
    )
    void generateKeys(){

        /*
        String[] profiles = environment.getActiveProfiles();
        System.out.println("Active profiles: " + Arrays.toString(profiles));
        */

        String base64Key = getKey(32);
        System.out.println("Encryption Secret Key - 256-bit key (Base64): " + base64Key);
        System.out.println("Length after decode: " + Base64.getDecoder().decode(base64Key).length); // always 32


        String hashSecret = getKey(32);
        System.out.println("Hsh Secret - 256-bit key (Base64): " + hashSecret);
        System.out.println("Length after decode: " + Base64.getDecoder().decode(hashSecret).length); // always 32


    }

    @Test
    @EnabledIf(
            expression = "#{environment.acceptsProfiles('local')}",
            loadContext = true
    )
    void generateEncryptedSeedValues() {

        String plainEmail = "test.user@example.com";
        String encryptedEmail = encryptor.encrypt(plainEmail);
        System.out.println("encryptedEmail: " + encryptedEmail);
        String hashedEmail = hashUtil.blindIndex(plainEmail);
        System.out.println("hashedEmail: " + hashedEmail);


        String plainPhone = "(+91)9876543210";
        String encryptedPhone = encryptor.encrypt(plainPhone);
        System.out.println("encryptedPhone: " + encryptedPhone);
        String hashedPhone = hashUtil.blindIndex(plainPhone);
        System.out.println("hashedPhone: " + hashedPhone);

        // Copy-paste this output into your Liquibase YAML
    }
}