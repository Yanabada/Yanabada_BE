package kr.co.fastcampus.yanabada.domain.payment.service;

import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.TransactionType.DISBURSEMENT;

import java.time.LocalDateTime;
import java.util.Objects;
import kr.co.fastcampus.yanabada.common.exception.AccessForbiddenException;
import kr.co.fastcampus.yanabada.common.exception.DuplicateYanoljaPayException;
import kr.co.fastcampus.yanabada.common.exception.IncorrectYanoljaPayPasswordException;
import kr.co.fastcampus.yanabada.common.exception.YanoljaPayNotFoundException;
import kr.co.fastcampus.yanabada.common.utils.AccountNumberSplitter;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPayAmountRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPayHistorySearchRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPaySaveRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.AdminPaymentInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHistoryIdResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHistoryInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHistorySummaryPageResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayIdResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPaySummaryResponse;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.ContentsType;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TransactionType;
import kr.co.fastcampus.yanabada.domain.payment.repository.AdminPaymentRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayHistoryRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final YanoljaPayRepository yanoljaPayRepository;
    private final YanoljaPayHistoryRepository yanoljaPayHistoryRepository;
    private final AdminPaymentRepository adminPaymentRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public YanoljaPaySummaryResponse getYanoljaPaySummary(Long memberId) {
        Member member = memberRepository.getMember(memberId);
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        return YanoljaPaySummaryResponse.from(yanoljaPay);
    }

    @Transactional(readOnly = true)
    public YanoljaPayInfoResponse getYanoljaPay(Long memberId) {
        Member member = memberRepository.getMember(memberId);
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        if (yanoljaPay.getAccountNumber() == null) {
            throw new YanoljaPayNotFoundException();
        }

        return YanoljaPayInfoResponse.from(yanoljaPay);
    }

    @Transactional
    public YanoljaPayIdResponse saveYanoljaPay(
        Long memberId,
        YanoljaPaySaveRequest request
    ) {
        Member member = memberRepository.getMember(memberId);
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        if (yanoljaPay.getAccountNumber() != null) {
            throw new DuplicateYanoljaPayException();
        }

        yanoljaPay.setAccountNumber(request.accountNumber());
        yanoljaPay.setBankName(request.bankName());
        yanoljaPay.setSimplePassword(request.simplePassword());

        return YanoljaPayIdResponse.from(yanoljaPay);
    }

    @Transactional
    public YanoljaPayHistoryIdResponse chargeYanoljaPay(
        Long memberId,
        YanoljaPayAmountRequest request
    ) {
        Member member = memberRepository.getMember(memberId);
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        if (yanoljaPay.getAccountNumber() == null) {
            throw new YanoljaPayNotFoundException();
        }
        if (!Objects.equals(yanoljaPay.getSimplePassword(), request.simplePassword())) {
            throw new IncorrectYanoljaPayPasswordException();
        }

        yanoljaPay.charge(request.amount());

        return YanoljaPayHistoryIdResponse.from(
            yanoljaPayHistoryRepository.save(
                YanoljaPayHistory.create(
                    yanoljaPay,
                    ContentsType.CHARGE,
                    yanoljaPay.getBankName()
                        + " "
                        + AccountNumberSplitter.split(yanoljaPay.getAccountNumber()),
                    request.amount(),
                    TransactionType.CHARGE,
                    LocalDateTime.now()
                )
            )
        );
    }

    @Transactional
    public YanoljaPayHistoryIdResponse disburseYanoljaPay(
        Long memberId,
        YanoljaPayAmountRequest request
    ) {
        Member member = memberRepository.getMember(memberId);
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        if (yanoljaPay.getAccountNumber() == null) {
            throw new YanoljaPayNotFoundException();
        }
        if (!Objects.equals(yanoljaPay.getSimplePassword(), request.simplePassword())) {
            throw new IncorrectYanoljaPayPasswordException();
        }

        yanoljaPay.disburse(request.amount());

        return YanoljaPayHistoryIdResponse.from(
            yanoljaPayHistoryRepository.save(
                YanoljaPayHistory.create(
                    yanoljaPay,
                    ContentsType.DISBURSEMENT,
                    yanoljaPay.getBankName()
                        + " "
                        + AccountNumberSplitter.split(yanoljaPay.getAccountNumber()),
                    request.amount(),
                    DISBURSEMENT,
                    LocalDateTime.now()
                )
            )
        );
    }

    @Transactional(readOnly = true)
    public YanoljaPayHistoryInfoResponse getYanoljaPayHistory(Long memberId, Long historyId) {
        Member member = memberRepository.getMember(memberId);
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        if (yanoljaPay.getAccountNumber() == null) {
            throw new YanoljaPayNotFoundException();
        }

        YanoljaPayHistory history = yanoljaPayHistoryRepository.getYanoljaPayHistory(historyId);

        if (!Objects.equals(history.getYanoljaPay().getMember(), member)) {
            throw new AccessForbiddenException();
        }

        return YanoljaPayHistoryInfoResponse.from(history);
    }

    @Transactional(readOnly = true)
    public YanoljaPayHistorySummaryPageResponse getYanoljaPayHistoriesBySearchRequest(
        Long memberId, YanoljaPayHistorySearchRequest request
    ) {
        Member member = memberRepository.getMember(memberId);
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        if (yanoljaPay.getAccountNumber() == null) {
            throw new YanoljaPayNotFoundException();
        }

        return YanoljaPayHistorySummaryPageResponse.from(
            yanoljaPayHistoryRepository.getHistoriesByYanoljaPayAndSearchRequest(
                yanoljaPay, request
            )
        );
    }

    @Transactional(readOnly = true)
    public AdminPaymentInfoResponse getAdminPayment() {
        return AdminPaymentInfoResponse.from(
            productRepository.count(),
            adminPaymentRepository.getAdminPayment()
        );
    }
}