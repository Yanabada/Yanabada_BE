package kr.co.fastcampus.yanabada.domain.payment.controller;

import java.util.List;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.AccountLinkingRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.AccountVerificationRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.ChargeRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.OpenBankingConsentRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.PasswordRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.AccountLinkingResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.TransactionHistoryResponse;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanabadaPaymentHistory;
import kr.co.fastcampus.yanabada.domain.payment.service.AccountService;
import kr.co.fastcampus.yanabada.domain.payment.service.ChargeService;
import kr.co.fastcampus.yanabada.domain.payment.service.OpenBankingService;
import kr.co.fastcampus.yanabada.domain.payment.service.PasswordService;
import kr.co.fastcampus.yanabada.domain.payment.service.TransactionHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PasswordService passwordService;
    private final AccountService accountService;
    private final OpenBankingService openBankingService;
    private final ChargeService chargeService;
    private final TransactionHistoryService transactionHistoryService;

    public PaymentController(PasswordService passwordService,
        AccountService accountService,
        OpenBankingService openBankingService,
        ChargeService chargeService,
        TransactionHistoryService transactionHistoryService) {
        this.passwordService = passwordService;
        this.accountService = accountService;
        this.openBankingService = openBankingService;
        this.chargeService = chargeService;
        this.transactionHistoryService = transactionHistoryService;
    }

    // 비밀번호 등록
    @PostMapping("/payPassword")
    public ResponseEntity<?> setPayPassword(@RequestBody PasswordRequest passwordRequest,
        @RequestParam Long memberId) {
        // passwordService 로직 호출
        passwordService.registerPassword(memberId, passwordRequest.password());
        return ResponseEntity.ok().build();
    }

    // 비밀번호 검증
    @PostMapping("/payPassword/validate")
    public ResponseEntity<?> validatePayPassword(@RequestBody PasswordRequest passwordRequest,
        @RequestParam Long memberId) {
        boolean isValid = passwordService.validatePassword(memberId, passwordRequest.password());
        return ResponseEntity.ok(isValid);
    }

    // 계좌 연결
    @PostMapping("/account")
    public ResponseEntity<AccountLinkingResponse> linkAccount(
        @RequestBody AccountLinkingRequest accountLinkingRequest, @RequestParam Long memberId) {
        accountService.linkAccount(memberId, accountLinkingRequest.accountNumber(),
            accountLinkingRequest.bankName());
        String message = "계좌가 성공적으로 연결되었습니다.";
        return ResponseEntity.ok(new AccountLinkingResponse(memberId, message));
    }

    // 계좌 인증
    @PostMapping("/account/verify")
    public ResponseEntity<String> verifyAccount(
        @RequestBody AccountVerificationRequest accountVerificationRequest) {
        boolean isVerified = accountService.verifyAccount(
            accountVerificationRequest.verificationCode());
        if (isVerified) {
            return ResponseEntity.ok("인증번호가 일치합니다.");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 일치하지 않습니다.");
        }
    }


    // 오픈뱅킹 동의
    @GetMapping("/openbanking")
    public ResponseEntity<Member> getOpenBankingInfo(@RequestParam Long memberId) {
        Member memberInfo = openBankingService.getOpenBankingInfo(memberId);
        return ResponseEntity.ok(memberInfo);
    }

    @PostMapping("/openbanking/consent")
    public ResponseEntity<?> consentOpenBanking(
        @RequestBody OpenBankingConsentRequest consentRequest, @RequestParam Long memberId) {
        openBankingService.processConsent(memberId, consentRequest);
        return ResponseEntity.ok().build();
    }

    // 페이 충전
    @PostMapping("/payCharge")
    public ResponseEntity<?> chargePay(@RequestBody ChargeRequest chargeRequest,
        @RequestParam Long memberId) {
        // chargeService 로직 호출
        chargeService.chargeAccount(memberId, chargeRequest.amount());
        return ResponseEntity.ok().build();
    }

    // 페이 이용 내역 조회
    @GetMapping("/")
    public ResponseEntity<List<TransactionHistoryResponse>> getPaymentHistory(
        @RequestParam Long memberId) {
        List<TransactionHistoryResponse> history = transactionHistoryService.getTransactionHistory(
            memberId);
        return ResponseEntity.ok(history);
    }
}
