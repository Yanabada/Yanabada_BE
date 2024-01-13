package kr.co.fastcampus.yanabada.domain.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.regex.Pattern;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;

@Service
public class PasswordService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerPassword(Long memberId, String password) {
        if (!isPasswordValid(password)) {
            throw new IllegalArgumentException("비밀번호는 6자리 숫자여야 합니다.");
        }
        Member member = memberRepository.getMember(memberId);
        member.updatePassword(passwordEncoder.encode(password));
        memberRepository.save(member);
    }

    public boolean validatePassword(Long memberId, String password) {
        Member member = memberRepository.getMember(memberId);
        return passwordEncoder.matches(password, member.getPassword());
    }

    private boolean isPasswordValid(String password) {
        return Pattern.matches("\\d{6}", password);
    }
}
