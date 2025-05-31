package com.example.gptreviewtest.email.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCredentialsDTO {
    private String to;
    private String name;
    private String userCode;
    private String rawPassword;
}
