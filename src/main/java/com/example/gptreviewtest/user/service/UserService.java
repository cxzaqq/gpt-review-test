package com.example.gptreviewtest.user.service;

import com.example.gptreviewtest.user.vo.ReqCreateUserVO;
import com.example.gptreviewtest.user.vo.ResCreateUserVO;

public interface UserService {
    void createUser(ReqCreateUserVO reqCreateUserVO, ResCreateUserVO resCreateUserVO);
}
