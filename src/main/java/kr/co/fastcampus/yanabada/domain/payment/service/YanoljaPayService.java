package kr.co.fastcampus.yanabada.domain.payment.service;

import kr.co.fastcampus.yanabada.common.exception.YanoljaPayNotFoundException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
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
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        return YanoljaPayHomeResponse.from(yanoljaPay);
    }
}