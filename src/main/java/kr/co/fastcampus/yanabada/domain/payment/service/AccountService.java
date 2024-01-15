package kr.co.fastcampus.yanabada.domain.payment.service;

import java.util.Random;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final YanoljaPayRepository yanoljaPayRepository;
    private final MemberRepository memberRepository;
    private final Random random = new Random();
    private String verificationCode;

    public AccountService(YanoljaPayRepository yanoljaPayRepository,
        MemberRepository memberRepository) {
        this.yanoljaPayRepository = yanoljaPayRepository;
        this.memberRepository = memberRepository;
    }

    public void linkAccount(Long memberId, String accountNumber, String bankName) {
        if (!isAccountNumberValid(accountNumber)) {
            throw new IllegalArgumentException("계좌번호는 13자리 숫자여야 합니다.");
        }
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        YanoljaPay yanoljaPay = YanoljaPay.create(
            member, accountNumber, null, bankName, null, 0L);
        yanoljaPayRepository.save(yanoljaPay);
    }

    public String generateVerificationCode() {
        int verificationNumber = random.nextInt(900) + 100; // 100에서 999 사이의 숫자
        verificationCode = "야나바다" + verificationNumber;
        return verificationCode;
    }

    public boolean verifyAccount(String inputCode) {
        if (verificationCode == null || !verificationCode.equals(inputCode)) {
            throw new IllegalArgumentException("인증번호가 일치하지 않아요 다시 확인해주세요.");
        }
        return true;
    }

    private boolean isAccountNumberValid(String accountNumber) {
        return accountNumber.matches("\\d{13}");
    }
}

