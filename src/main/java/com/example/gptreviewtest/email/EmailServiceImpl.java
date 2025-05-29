package com.example.gptreviewtest.email;

import com.example.gptreviewtest.CustomException.EmailSendingException;
import com.example.gptreviewtest.email.dto.UserCredentialsDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String userName;

    public void sendUserCredentials(UserCredentialsDTO userCredentialsDTO) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String htmlMsg = """
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f0f7ff; color: #003366; padding: 20px;">
                <h2 style="color: #1a73e8;">환영합니다 %s님! 회원가입이 완료되었습니다.</h2>
                <p><strong style="color: #0d47a1;">사용자 코드:</strong> %s</p>
                <p><strong style="color: #0d47a1;">초기 비밀번호:</strong> %s</p>
                <p style="margin-top: 30px;">로그인 후 비밀번호를 꼭 변경해 주세요.</p>
            </body>
            </html>
            """.formatted(userCredentialsDTO.getName(),
                    userCredentialsDTO.getUserCode(),
                    userCredentialsDTO.getRawPassword());

            helper.setText(htmlMsg, true);
            helper.setTo(userCredentialsDTO.getTo());
            helper.setSubject("[Frans] 계정 정보 안내");
            helper.setFrom(userName);

            mailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            throw new EmailSendingException("이메일 전송 실패");
        }
    }
}
