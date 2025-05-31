package com.example.gptreviewtest.user.query.service;

public interface UserQueryService {

    String findLatestUserCode(String userType, String codePrefix);

    boolean isEmailExist(String email);

    boolean isPhoneExist(String phone);
}
