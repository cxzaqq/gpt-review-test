package com.example.gptreviewtest;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@Slf4j
public class HMACKKeyGenerator {
    public static void main(String[] args) {
        try {
            /* HS512를 위한 KeyGenerator 생성 */
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
            keyGen.init(512);

            /* 비밀 키 생성 */
            SecretKey secretKey = keyGen.generateKey();

            /* 키를 Base64로 인코딩하여 문자열로 변환 */
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            log.info(encodedKey);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
