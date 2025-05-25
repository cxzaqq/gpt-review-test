package com.example.gptreviewtest.user.vo;

public record CreateUserResponseVO(
    String userCode,
    String name,
    String userType,
    String department) {}
