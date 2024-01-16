package kr.co.fastcampus.yanabada.domain.payment.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.common.exception.MemberNotFoundException;
import kr.co.fastcampus.yanabada.common.exception.YanoljaPayNotFoundException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.PaymentHistoryResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHomeResponse;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import org.springframework.stereotype.Service;

@Service
public class YanoljaPayService {

    private final YanoljaPayRepository yanoljaPayRepository;

    public YanoljaPayService(YanoljaPayRepository yanoljaPayRepository) {
        this.yanoljaPayRepository = yanoljaPayRepository;
    }

    public YanoljaPayHomeResponse getYanoljaPayData(Member member) {
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMemberId(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        return YanoljaPayHomeResponse.from(yanoljaPay);
    }
}