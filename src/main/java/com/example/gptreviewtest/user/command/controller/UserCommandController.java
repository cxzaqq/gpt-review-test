package com.example.gptreviewtest.user.command.controller;

import com.example.gptreviewtest.user.command.service.UserCommandService;
import com.example.gptreviewtest.user.command.vo.CreateUserRequestVO;
import com.example.gptreviewtest.user.command.vo.CreateUserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserCommandController {

    private final UserCommandService userService;

    @Autowired
    public UserCommandController(UserCommandService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseVO> createUser(@RequestBody CreateUserRequestVO createUserRequestVO) {
        CreateUserResponseVO createUserResponseVO = userService.createUser(createUserRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseVO);
    }
}
