# Notes

## Important
1. to generate encrypted keys ( hash secrets )
   - use: HelperTest.generateKeys
   
2. while seeding data to db wih liquibase migrations
   - pass encrypted & hashed data where needed
     - eg: user's phone & email are encrypted
     - solution: generate encrypted data using HelperTest.generateEncryptedSeedValues
     - ```yaml
          encryptedEmail: Xn4yKLtpvXfMtU+NSbtzHpyCrmGVf2h4ZvX3ZRwxBKWOjcLOlzD0fIFGyhOf29M4UQ==
          hashedEmail: 23e3465cc251d95f6139ba8204091ee49a0b05a8950ee2854f82c285047d9a2f
          encryptedPhone: 7GAiwOsgo7jQmwDNI2gR+ukcN+N3bSxngXbR5b2AfF84TeqASq5QTVQF3w==
          hashedPhone: c6ece5fc39082c809dd58edae57c3c4c7f8b92182497296f113b5f6bc81cdc5d
       ```

3. always store encrypted & hashed (both) values
   - encrypted values are not searchable ( or slow ), but recoverable
   - hashed values are searchable, but not recoverable (deterministic hashing / consistent hashing)
   - Solution: use both, eg: email_encrypted, email_hash

## Issues that are resolved
1. nested object chaining issue
    - eg: user has address -> and address has user
    - fix: using @JsonIgnore in Address entity

2. JPA Converter issue
   - by default JPA uses reflection to initialize objects(converters)...this fails, when we depend on spring managed beans
   - fix: inform JPA/Hibernate to use Spring's bean container to create and manage JPA converters by passing below env
   - ```yaml
         spring:
           jpa:
            properties:
              hibernate:
                type:
                 contributors: org.springframework.data.jpa.repository.config.BeanContainerBeanFactoryAdvisingBeanPostProcessor
     
     ```