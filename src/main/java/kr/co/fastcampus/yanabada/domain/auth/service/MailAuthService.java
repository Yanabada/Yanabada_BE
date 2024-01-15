package kr.co.fastcampus.yanabada.domain.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import kr.co.fastcampus.yanabada.common.exception.EmailSendFailedException;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.EmailAuthCodeRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.EmailAuthCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailAuthService {

    private final JavaMailSender mailSender;
    @Value("${email.user}")
    private String user;
    private static final int AUTH_CODE_LENGTH = 6;

    public Integer sendEmail(String email) {
        int authCode = makeRandomCode();
        String title = "[Yanabada]회원 가입 인증 이메일 입니다.";
        String content = makeContent(authCode);
        sendToSmtp(user, email, title, content);
        return authCode;
    }

    private String makeContent(int authCode) {
        return "<br><br>" + "인증 번호는 " + authCode + "입니다.";
    }

    private void sendToSmtp(String from, String to, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper
                = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendFailedException();
        }
    }

    private Integer makeRandomCode() {
        Random r = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < AUTH_CODE_LENGTH; i++) {
            randomNumber.append(r.nextInt(AUTH_CODE_LENGTH));
        }
        return Integer.parseInt(randomNumber.toString());
    }

}