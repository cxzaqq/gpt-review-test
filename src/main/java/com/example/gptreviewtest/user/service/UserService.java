package com.example.gptreviewtest.user.service;

import com.example.gptreviewtest.user.vo.CreateUserRequestVO;
import com.example.gptreviewtest.user.vo.CreateUserResponseVO;

public interface UserService {
    CreateUserResponseVO createUser(CreateUserRequestVO createUserRequestVO);
}
