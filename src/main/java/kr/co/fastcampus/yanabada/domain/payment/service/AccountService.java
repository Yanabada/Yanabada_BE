package kr.co.fastcampus.yanabada.domain.payment.service;

import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayment;
import org.springframework.stereotype.Service;
import java.util.Random;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPaymentRepository;

@Service
public class AccountService {
    private final YanoljaPaymentRepository yanoljaPaymentRepository;
    private final Random random = new Random();
    private String verificationCode;

    public AccountService(YanoljaPaymentRepository yanoljaPaymentRepository) {
        this.yanoljaPaymentRepository = yanoljaPaymentRepository;
    }

    public void linkAccount(Long memberId, String accountNumber, String bankName) {
        if (!isAccountNumberValid(accountNumber)) {
            throw new IllegalArgumentException("계좌번호는 13자리 숫자여야 합니다.");
        }
        YanoljaPayment yanoljaPayment = new YanoljaPayment();
        yanoljaPayment.setMemberId(memberId);
        yanoljaPayment.setAccountNumber(accountNumber);
        yanoljaPayment.setBankName(bankName);
        yanoljaPaymentRepository.save(yanoljaPayment);
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

