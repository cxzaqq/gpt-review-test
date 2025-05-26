package com.example.gptreviewtest.user.repository;

import com.example.gptreviewtest.user.aggregate.UserEntity;
import com.example.gptreviewtest.user.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 전화번호 존재 여부
    boolean existsByPhone(String phone);

    // 이메일 존재 여부
    boolean existsByEmail(String email);

    // userType과 userCodePrefix로 시작하는 코드 중 가장 최신 코드 1건 조회
    Optional<UserEntity> findTopByTypeAndCodeStartingWithOrderByCodeDesc(UserType userType, String userCodePrefix);
}
