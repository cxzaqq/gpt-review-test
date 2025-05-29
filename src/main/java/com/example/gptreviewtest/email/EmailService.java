package com.example.gptreviewtest.email;

import com.example.gptreviewtest.email.dto.UserCredentialsDTO;

public interface EmailService {

    void sendUserCredentials(UserCredentialsDTO userCredentialsDTO);
}
