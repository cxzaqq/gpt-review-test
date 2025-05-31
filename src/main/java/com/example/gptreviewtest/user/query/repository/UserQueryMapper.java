package com.example.gptreviewtest.user.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserQueryMapper {

    String findLatestUserCode(@Param("userType") String userType,
                                @Param("codePrefix") String codePrefix);


    boolean isEmailExist(@Param("email") String email);


    boolean isPhoneExist(@Param("phone") String phone);
}
