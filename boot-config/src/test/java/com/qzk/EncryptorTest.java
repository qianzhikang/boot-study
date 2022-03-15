package com.qzk;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @Description TODO
 * @Date 2022-03-15-15-25
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EncryptorTest {
    @Autowired
    private StringEncryptor encryptor;
    @Test
    public void getEncryptor() {
        String result = encryptor.encrypt("happy family");
        System.out.println(result);
    }
}
