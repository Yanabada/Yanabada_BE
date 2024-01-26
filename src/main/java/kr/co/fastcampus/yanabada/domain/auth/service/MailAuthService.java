package kr.co.fastcampus.yanabada.domain.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import kr.co.fastcampus.yanabada.common.exception.EmailAuthTimeExpiredException;
import kr.co.fastcampus.yanabada.common.exception.EmailNotVerifiedException;
import kr.co.fastcampus.yanabada.common.exception.EmailSendFailedException;
import kr.co.fastcampus.yanabada.common.redis.RedisUtils;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.AuthCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailAuthService {

    private static final int AUTH_CODE_LENGTH = 6;
    private final JavaMailSender mailSender;
    private final RedisUtils<AuthCodeDto> redisUtils;
    @Value("${email.user}")
    private String user;
    private static final long EMAIL_CODE_EXPIRED_TIME = 3 * 60000L; //3분
    private static final long EMAIL_MAINTAINED_VERIFIED_TIME = 10 * 60000L; //10분

    public void sendAuthCodeToEmail(String email) {
        String authCode = makeRandomCode();
        String title = "[Yanabada]회원 가입 인증 이메일 입니다.";
        String content = makeContent(authCode);
        sendToSmtp(user, email, title, content);
        saveAuthCodeSendHistoryInRedis(email, authCode);
    }

    public boolean verifyAuthCode(String email, String code) {
        AuthCodeDto findAuthCodeDto = redisUtils.getDataAsHash(email);
        if (findAuthCodeDto == null) {
            throw new EmailAuthTimeExpiredException();
        }
        if (!findAuthCodeDto.code().equals(code)) {
            return false;
        }

        AuthCodeDto newAuthCodeDto = new AuthCodeDto(findAuthCodeDto.code(), true);
        redisUtils.setDataAsHash(email, newAuthCodeDto, EMAIL_MAINTAINED_VERIFIED_TIME);
        return true;
    }

    public boolean checkEmailIsVerified(String email) {
        AuthCodeDto findAuthCodeDto = redisUtils.getDataAsHash(email);
        if (findAuthCodeDto == null) {
            throw new EmailNotVerifiedException();
        }
        return findAuthCodeDto.isVerified();
    }

    private void saveAuthCodeSendHistoryInRedis(String email, String authCode) {
        AuthCodeDto authCodeDto = new AuthCodeDto(authCode, false);
        redisUtils.setDataAsHash(email, authCodeDto, EMAIL_CODE_EXPIRED_TIME);
    }

    private String makeContent(String authCode) {
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

    private String makeRandomCode() {
        Random r = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < AUTH_CODE_LENGTH; i++) {
            randomNumber.append(r.nextInt(AUTH_CODE_LENGTH));
        }
        return randomNumber.toString();
    }

}