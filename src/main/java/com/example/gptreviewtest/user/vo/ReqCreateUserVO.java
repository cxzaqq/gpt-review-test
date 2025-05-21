package com.example.gptreviewtest.user.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReqCreateUserVO {
    private String name;
    private String email;
    private String phone;
    private String userType;
    private String dept_code;
}
