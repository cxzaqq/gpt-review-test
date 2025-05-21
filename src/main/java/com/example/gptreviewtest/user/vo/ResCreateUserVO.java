package com.example.gptreviewtest.user.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResCreateUserVO {
    private String userCode;
    private String name;
    private String password;
    private String userType;
    private String department;
}
