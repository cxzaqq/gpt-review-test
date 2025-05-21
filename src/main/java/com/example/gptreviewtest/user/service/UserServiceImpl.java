package com.example.gptreviewtest.user.service;

import com.example.gptreviewtest.user.enums.UserType;
import com.example.gptreviewtest.user.repository.UserRepository;
import com.example.gptreviewtest.user.vo.ReqCreateUserVO;
import com.example.gptreviewtest.user.vo.ResCreateUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * userType에 맞게 새로운 userCode 생성
     * @param userType enum
     * @return String newUserCode
     */
    public String createNewUserCode(UserType userType) {

        // prefix 설정
        String prefix = switch (userType) {
            case ADMIN -> "AD";
            case HQ -> "HQ";
            case FRANCHISE -> "FR";
            case SUPPLIER -> "SU";
        };

        // yyyyMM 포맷
        String yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String codePrefix = prefix + yearMonth;

        // 가장 최근 코드 조회
        return userRepository.findTopByUserTypeAndUserCodeStartingWithOrderByUserCodeDesc(userType, codePrefix)
                .map(latestUser -> {
                    String latestCode = latestUser.getUser_code();
                    String last3Digits = latestCode.substring(latestCode.length() - 3);
                    int nextNumber = Integer.parseInt(last3Digits) + 1;
                    return codePrefix + String.format("%03d", nextNumber);
                })
                .orElse(codePrefix + "001");
    }


    @Override
    public void createUser(ReqCreateUserVO reqCreateUserVO, ResCreateUserVO resCreateUserVO) {

        // userType 확인 후 userCode 생성
        String userCode = createNewUserCode(UserType.valueOf(reqCreateUserVO.getUserType()));

        // 비밀번호 랜덤 문자열 생성
        
        // 생성된 비밀번호 암호화
        
        // DB에 저장
        
        // 정보 반환
    }
}
