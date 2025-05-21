package com.example.gptreviewtest.user.controller;

import com.example.gptreviewtest.user.service.UserService;
import com.example.gptreviewtest.user.vo.ReqCreateUserVO;
import com.example.gptreviewtest.user.vo.ResCreateUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResCreateUserVO> createUser(@RequestBody ReqCreateUserVO reqCreateUserVO) {
        ResCreateUserVO resCreateUserVO = new ResCreateUserVO();
        userService.createUser(reqCreateUserVO, resCreateUserVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(resCreateUserVO);
    }
}
