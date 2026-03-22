package com.paravar.retailflow;

import com.paravar.retailflow.util.AesGcmEncryptionUtil;
import com.paravar.retailflow.util.HashingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class HelperTest {

    private final AesGcmEncryptionUtil encryptor;
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

    void generateEmailAndPhone(String username, String email, String phone){
        log.info("===========username : {}=======================", username);

        String encryptedEmail = encryptor.encrypt(email);
        String hashedEmail = hashUtil.blindIndex(email);
        log.info("email:{}, encryptedEmail:{}, hashedEmail:{}",email, encryptedEmail, hashedEmail);


        String encryptedPhone = encryptor.encrypt(phone);
        String hashedPhone = hashUtil.blindIndex(phone);
        log.info("phone:{}, encryptedPhone:{}, hashedPhone:{}",phone, encryptedPhone, hashedPhone);

        log.info("===========================================");


    }

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

        generateEmailAndPhone("SUPER_ADMIN", "super.admin@retailflow.com","(+91)9876543210");
//        generateEmailAndPhone("ADMIN", "admin@retailflow.com","(+91)9876543211");
//        generateEmailAndPhone("RETAIL_CUSTOMER", "retail.customer@retailflow.com","(+91)9876543212");
//        generateEmailAndPhone("WHOLESALE_CUSTOMER", "wholsale.customer@retailflow.com","(+91)9876543213");
//        generateEmailAndPhone("STORE_MANAGER", "storemaneger.customer@retailflow.com","(+91)9876543214");


        // Copy-paste this output into your Liquibase YAML
    }
}