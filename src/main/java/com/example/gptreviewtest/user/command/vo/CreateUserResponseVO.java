package com.example.gptreviewtest.user.command.vo;

public record CreateUserResponseVO(
    String userCode,
    String name,
    String userType,
    Integer departmentId,
    Integer positionId,
    Integer dutyId) {}
