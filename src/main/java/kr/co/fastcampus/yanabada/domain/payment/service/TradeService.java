package kr.co.fastcampus.yanabada.domain.payment.service;

import java.util.Objects;
import kr.co.fastcampus.yanabada.common.exception.AccessForbiddenException;
import kr.co.fastcampus.yanabada.common.exception.CannotTradeOwnProductException;
import kr.co.fastcampus.yanabada.common.exception.IllegalProductStatusException;
import kr.co.fastcampus.yanabada.common.exception.IllegalTradeStatusException;
import kr.co.fastcampus.yanabada.common.utils.OrderCodeGenerator;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.TradeSaveRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.TradeIdResponse;
import kr.co.fastcampus.yanabada.domain.payment.entity.Trade;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.payment.repository.TradeRepository;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public TradeIdResponse saveTrade(
        Long buyerId,
        TradeSaveRequest request
    ) {
        Product product = productRepository.getProduct(request.productId());
        Member seller = product.getOrder().getMember();
        Member buyer = memberRepository.getMember(buyerId);

        validateTradeSaveRequest(product, seller, buyer);

        //TODO: Buyer 결제

        product.book();

        //TODO: Seller에게 알림

        return TradeIdResponse.from(
            tradeRepository.save(request.toEntity(product, seller, buyer))
        );
    }

    @Transactional
    public void approveTrade(Long sellerId, Long tradeId) {
        Member seller = memberRepository.getMember(sellerId);
        Trade trade = tradeRepository.getTrade(tradeId);

        validateTradeApproveRequest(seller, trade);

        //TODO: Buyer 결제

        trade.complete();
        trade.getProduct().soldOut();
        trade.getProduct().getOrder().trade();
        orderRepository.save(createOrderFromTrade(trade));

        //TODO: Seller에게 알림
    }

    @Transactional
    public void rejectTrade(Long sellerId, Long tradeId) {
        Member seller = memberRepository.getMember(sellerId);
        Trade trade = tradeRepository.getTrade(tradeId);

        validateTradeRejectRequest(seller, trade);

        //TODO: Buyer에게 환불 진행

        trade.reject();
        trade.getProduct().onSale();

        //TODO: Buyer에게 알림(Optional)
    }

    @Transactional
    public void cancelTrade(Long buyerId, Long tradeId) {
        Member buyer = memberRepository.getMember(buyerId);
        Trade trade = tradeRepository.getTrade(tradeId);

        validateTradeCancelRequest(buyer, trade);

        //TODO: Buyer에게 환불

        trade.cancel();
        trade.getProduct().onSale();

        //TODO: Seller에게 알림(Optional)
    }

    private void validateTradeSaveRequest(Product product, Member seller, Member buyer) {
        if (product.getStatus() != ProductStatus.ON_SALE) {
            throw new IllegalProductStatusException();
        }
        if (Objects.equals(seller, buyer)) {
            throw new CannotTradeOwnProductException();
        }
    }

    private void validateTradeApproveRequest(Member seller, Trade trade) {
        if (!Objects.equals(seller, trade.getSeller())) {
            throw new AccessForbiddenException();
        }
        if (trade.getStatus() != TradeStatus.WAITING) {
            throw new IllegalTradeStatusException();
        }
    }

    private void validateTradeRejectRequest(Member seller, Trade trade) {
        if (!Objects.equals(seller, trade.getSeller())) {
            throw new AccessForbiddenException();
        }
        if (trade.getStatus() != TradeStatus.WAITING) {
            throw new IllegalTradeStatusException();
        }
    }

    private void validateTradeCancelRequest(Member buyer, Trade trade) {
        if (!Objects.equals(buyer, trade.getBuyer())) {
            throw new AccessForbiddenException();
        }
        if (trade.getStatus() != TradeStatus.WAITING) {
            throw new IllegalTradeStatusException();
        }
    }

    private Order createOrderFromTrade(Trade trade) {
        Product product = trade.getProduct();
        Order order = product.getOrder();

        return Order.create(
            order.getRoom(),
            trade.getBuyer(),
            order.getCheckInDate(),
            order.getCheckOutDate(),
            OrderStatus.RESERVED,
            trade.getSellingPrice(),
            trade.getReservationPersonName(),
            trade.getReservationPersonPhoneNumber(),
            trade.getUserPersonName(),
            trade.getUserPersonPhoneNumber(),
            trade.getPaymentType(),
            OrderCodeGenerator.generate()
        );
    }
}
