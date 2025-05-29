package com.example.gptreviewtest.email;

public interface EmailService {

    void sendUserCredentials(String to, String name, String userCode, String rawPassword);
}
