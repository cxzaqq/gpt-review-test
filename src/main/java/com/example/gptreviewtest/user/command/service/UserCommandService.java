package com.example.gptreviewtest.user.command.service;

import com.example.gptreviewtest.user.command.vo.CreateUserRequestVO;
import com.example.gptreviewtest.user.command.vo.CreateUserResponseVO;

public interface UserCommandService {
    CreateUserResponseVO createUser(CreateUserRequestVO createUserRequestVO);
}
