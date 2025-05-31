package com.example.gptreviewtest.user.command.service;

import com.example.gptreviewtest.customexception.InvalidUserTypeException;
import com.example.gptreviewtest.email.EmailService;
import com.example.gptreviewtest.customexception.DuplicateException;
import com.example.gptreviewtest.email.dto.UserCredentialsDTO;
import com.example.gptreviewtest.user.command.aggregate.HqUserDetailEntity;
import com.example.gptreviewtest.user.command.aggregate.UserEntity;
import com.example.gptreviewtest.user.enums.UserType;
import com.example.gptreviewtest.user.command.repository.HqUserDetailCommandRepository;
import com.example.gptreviewtest.user.command.repository.UserCommandRepository;
import com.example.gptreviewtest.user.command.vo.CreateUserRequestVO;
import com.example.gptreviewtest.user.command.vo.CreateUserResponseVO;
import com.example.gptreviewtest.user.query.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserCommandRepository userRepository;
    private final HqUserDetailCommandRepository hqUserDetailRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    private final UserQueryService userQueryService;

    @Autowired
    public UserCommandServiceImpl(UserCommandRepository userRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  HqUserDetailCommandRepository hqUserDetailRepository,
                                  EmailService emailService,
                                  UserQueryService userQueryService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.hqUserDetailRepository = hqUserDetailRepository;
        this.emailService = emailService;
        this.userQueryService = userQueryService;
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
        String latestUserCode = userQueryService.findLatestUserCode(userType.name(), codePrefix);
        if (latestUserCode == null) {
            return codePrefix + "0001";
        } else {
            String last4Digits = latestUserCode.substring(latestUserCode.length() - 4);
            int nextNumber = Integer.parseInt(last4Digits) + 1;
            return codePrefix + String.format("%04d", nextNumber);
        }
    }

    private static final String CHAR_POOL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int PASSWORD_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateRandomPassword() {
        StringBuilder randomPassword = new StringBuilder();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            randomPassword.append(CHAR_POOL.charAt(RANDOM.nextInt(CHAR_POOL.length())));
        }

        return randomPassword.toString();
    }


    @Transactional
    @Override
    public CreateUserResponseVO createUser(CreateUserRequestVO createUserRequestVO) {
        
        // 전화번호 중복 체크
        if (userQueryService.isPhoneExist(createUserRequestVO.getPhone())) {
            throw new DuplicateException("이미 등록된 전화번호입니다.");
        }
        
        // 이메일 중복 체크
        if (userQueryService.isEmailExist(createUserRequestVO.getEmail())) {
            throw new DuplicateException("이미 등록된 이메일입니다.");
        }

        // userType 확인 후 userCode 생성
        UserType userTypeEnum;
        try {
            userTypeEnum = UserType.valueOf(createUserRequestVO.getUserType());
        } catch (IllegalArgumentException e) {
            throw new InvalidUserTypeException("지원하지 않는 사용자 타입입니다: " + createUserRequestVO.getUserType());
        }

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

        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setTo(createUserRequestVO.getEmail());
        userCredentialsDTO.setName(createUserRequestVO.getName());
        userCredentialsDTO.setUserCode(userCode);
        userCredentialsDTO.setRawPassword(randomPw);

        // 유저에게 이메일 발송
        emailService.sendUserCredentials(userCredentialsDTO);
        
        // 정보 반환
        return new CreateUserResponseVO(userCode,
                createUserRequestVO.getName(),
                userTypeEnum.name(),
                createUserRequestVO.getDepartmentId(),
                createUserRequestVO.getPositionId(),
                createUserRequestVO.getDutyId());
    }
}
