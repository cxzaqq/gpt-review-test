package com.example.gptreviewtest.user.service;

import com.example.gptreviewtest.email.EmailService;
import com.example.gptreviewtest.CustomException.DuplicateException;
import com.example.gptreviewtest.user.aggregate.HqUserDetailEntity;
import com.example.gptreviewtest.user.aggregate.UserEntity;
import com.example.gptreviewtest.user.enums.UserType;
import com.example.gptreviewtest.user.repository.HqUserDetailRepository;
import com.example.gptreviewtest.user.repository.UserRepository;
import com.example.gptreviewtest.user.vo.CreateUserRequestVO;
import com.example.gptreviewtest.user.vo.CreateUserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final HqUserDetailRepository hqUserDetailRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           HqUserDetailRepository hqUserDetailRepository,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.hqUserDetailRepository = hqUserDetailRepository;
        this.emailService = emailService;

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
        return userRepository.findTopByTypeAndCodeStartingWithOrderByCodeDesc(userType, codePrefix)
                .map(latestUser -> {
                    String latestCode = latestUser.getCode();
                    String last4Digits = latestCode.substring(latestCode.length() - 4);
                    int nextNumber = Integer.parseInt(last4Digits) + 1;
                    return codePrefix + String.format("%04d", nextNumber);
                })
                .orElse(codePrefix + "0001");
    }

    public String generateRandomPassword() {
        String CHAR_POOL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int PW_LENGTH = 6;
        SecureRandom random = new SecureRandom();

        StringBuilder randomPassword = new StringBuilder();

        for (int i = 0; i < PW_LENGTH; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            randomPassword.append(CHAR_POOL.charAt(index));
        }

        return randomPassword.toString();
    }


    @Override
    public CreateUserResponseVO createUser(CreateUserRequestVO createUserRequestVO) {
        
        // 전화번호 중복 체크
        if (userRepository.existsByPhone(createUserRequestVO.getPhone())) {
            throw new DuplicateException("이미 등록된 전화번호입니다.");
        }
        
        // 이메일 중복 체크
        if (userRepository.existsByEmail(createUserRequestVO.getEmail())) {
            throw new DuplicateException("이미 등록된 이메일입니다.");
        }

        // userType 확인 후 userCode 생성
        UserType userTypeEnum = UserType.valueOf(createUserRequestVO.getUserType());
        String userCode = createNewUserCode(userTypeEnum);

        // 비밀번호 랜덤 문자열 생성
        String randomPw = generateRandomPassword();
        
        // 생성된 비밀번호 암호화
        String encryptedPassword = bCryptPasswordEncoder.encode(randomPw);
        
        // DB에 저장
        final UserEntity userToSave = new UserEntity();
        userToSave.setCode(userCode);
        userToSave.setPassword(encryptedPassword);
        userToSave.setName(createUserRequestVO.getName());
        userToSave.setEmail(createUserRequestVO.getEmail());
        userToSave.setPhone(createUserRequestVO.getPhone());
        userToSave.setCreatedAt(LocalDateTime.now());
        userToSave.setType(userTypeEnum);

        final UserEntity savedUser = userRepository.save(userToSave);

        if (userTypeEnum == UserType.HQ) {
            HqUserDetailEntity hqUserDetailEntity = new HqUserDetailEntity();
            hqUserDetailEntity.setUser(savedUser);
            hqUserDetailEntity.setDepartmentId(createUserRequestVO.getDepartmentId());
            hqUserDetailEntity.setPositionId(createUserRequestVO.getPositionId());
            hqUserDetailEntity.setDutyId(createUserRequestVO.getDutyId());

            hqUserDetailRepository.save(hqUserDetailEntity);
        }

        // TODO: 유저에게 이메일 발송
        emailService.sendUserCredentials(createUserRequestVO.getEmail(),
                                            createUserRequestVO.getName(),
                                            userCode,
                                            randomPw);
        
        // 정보 반환
        return new CreateUserResponseVO(userCode,
                createUserRequestVO.getName(),
                userTypeEnum.name(),
                createUserRequestVO.getDepartmentId(),
                createUserRequestVO.getPositionId(),
                createUserRequestVO.getDutyId());
    }
}
