package kr.co.fastcampus.yanabada.domain.payment.service;

import static kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType.YANOLJA_PAY;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.ContentsType.PURCHASE;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.ContentsType.REFUND;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.ContentsType.SALE;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeRole.BUYER;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeRole.SELLER;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus.CANCELED;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus.REJECTED;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus.WAITING;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.TransactionType.DEPOSIT;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.TransactionType.WITHDRAW;

import java.time.LocalDateTime;
import java.util.Objects;
import kr.co.fastcampus.yanabada.common.exception.AccessForbiddenException;
import kr.co.fastcampus.yanabada.common.exception.CannotTradeOwnProductException;
import kr.co.fastcampus.yanabada.common.exception.IllegalProductStatusException;
import kr.co.fastcampus.yanabada.common.exception.IllegalTradeStatusException;
import kr.co.fastcampus.yanabada.common.exception.IncorrectYanoljaPayPasswordException;
import kr.co.fastcampus.yanabada.common.exception.TradeNotFoundException;
import kr.co.fastcampus.yanabada.common.exception.UnavailableStatusQueryException;
import kr.co.fastcampus.yanabada.common.exception.YanoljaPayNotFoundException;
import kr.co.fastcampus.yanabada.common.utils.EntityCodeGenerator;
import kr.co.fastcampus.yanabada.common.utils.PayFeeCalculator;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.notification.dto.TradeNotificationDto;
import kr.co.fastcampus.yanabada.domain.notification.service.NotificationService;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.TradeSaveRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.ApprovalTradeInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.ApprovalTradePageResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.ApprovalTradeSummaryResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.PurchaseTradeInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.PurchaseTradePageResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.PurchaseTradeSummaryResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.TradeIdResponse;
import kr.co.fastcampus.yanabada.domain.payment.entity.AdminPayment;
import kr.co.fastcampus.yanabada.domain.payment.entity.Trade;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import kr.co.fastcampus.yanabada.domain.payment.repository.AdminPaymentRepository;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeRole;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.payment.repository.TradeRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayHistoryRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final YanoljaPayRepository yanoljaPayRepository;
    private final YanoljaPayHistoryRepository yanoljaPayHistoryRepository;
    private final AdminPaymentRepository adminPaymentRepository;
    private final NotificationService notificationService;

    @Transactional
    public TradeIdResponse saveTrade(
        Long buyerId,
        TradeSaveRequest request
    ) {
        AdminPayment adminPayment = adminPaymentRepository.getAdminPayment();
        Product product = productRepository.getProduct(request.productId());
        Member seller = product.getOrder().getMember();
        Member buyer = memberRepository.getMember(buyerId);

        validateTradeSaveRequest(product, seller, buyer);

        long bill = product.getPrice()
            + PayFeeCalculator.calculate(product.getPrice(), request.paymentType())
            - request.point();
        payBill(buyer, bill, request, product);
        adminPayment.deposit(bill);
        buyer.subtractPoint(request.point());

        product.book();

        notificationService.sendTradeRequest(
            TradeNotificationDto.from(
                seller,
                product.getOrder().getRoom().getAccommodation().getName()
            )
        );

        return TradeIdResponse.from(
            tradeRepository.save(request.toEntity(product, seller, buyer))
        );
    }

    @Transactional
    public TradeIdResponse approveTrade(Long sellerId, Long tradeId) {
        AdminPayment adminPayment = adminPaymentRepository.getAdminPayment();
        Member seller = memberRepository.getMember(sellerId);
        Trade trade = tradeRepository.getTrade(tradeId);

        validateTradeApproveRequest(seller, trade);

        long bill = trade.getSellingPrice();
        receiveBill(seller, bill, trade.getProduct());
        adminPayment.withdraw(bill);
        adminPayment.increaseAccumulatedUsageAmount(bill);
        adminPayment.increaseAccumulatedDiscountAmount(trade.getPrice() - trade.getSellingPrice());

        trade.complete();
        trade.getProduct().soldOut();
        trade.getProduct().getOrder().trade();
        orderRepository.save(createOrderFromTrade(trade));

        notificationService.sendTradeApproval(
            TradeNotificationDto.from(
                trade.getBuyer(),
                trade.getProduct().getOrder().getRoom().getAccommodation().getName()
            )
        );

        return TradeIdResponse.from(trade);
    }

    @Transactional
    public TradeIdResponse rejectTrade(Long sellerId, Long tradeId) {
        Member seller = memberRepository.getMember(sellerId);
        Trade trade = tradeRepository.getTrade(tradeId);

        validateTradeRejectRequest(seller, trade);

        long bill = trade.getSellingPrice() + trade.getFee() - trade.getPoint();
        refundBill(trade.getBuyer(), bill, trade.getPaymentType(), trade.getProduct());
        trade.getBuyer().addPoint(trade.getPoint());
        adminPaymentRepository.getAdminPayment().withdraw(bill);

        trade.reject();
        trade.getProduct().onSale();

        notificationService.sendTradeRejected(
            TradeNotificationDto.from(
                trade.getBuyer(),
                trade.getProduct().getOrder().getRoom().getAccommodation().getName()
            )
        );

        return TradeIdResponse.from(trade);
    }

    @Transactional
    public TradeIdResponse cancelTrade(Long buyerId, Long tradeId) {
        Member buyer = memberRepository.getMember(buyerId);
        Trade trade = tradeRepository.getTrade(tradeId);

        validateTradeCancelRequest(buyer, trade);

        long bill = trade.getSellingPrice() + trade.getFee() - trade.getPoint();
        refundBill(buyer, bill, trade.getPaymentType(), trade.getProduct());
        buyer.addPoint(trade.getPoint());
        adminPaymentRepository.getAdminPayment().withdraw(bill);

        trade.cancel();
        trade.getProduct().onSale();

        notificationService.sendTradeCanceled(
            TradeNotificationDto.from(
                trade.getSeller(),
                trade.getProduct().getOrder().getRoom().getAccommodation().getName()
            )
        );

        return TradeIdResponse.from(trade);
    }

    @Transactional(readOnly = true)
    public ApprovalTradeInfoResponse getApprovalTrade(Long memberId, Long tradeId) {
        Member member = memberRepository.getMember(memberId);
        Trade trade = tradeRepository.getTrade(tradeId);

        if (trade.getHasSellerDeleted()) {
            throw new TradeNotFoundException();
        }
        if (!Objects.equals(member, trade.getSeller())) {
            throw new AccessForbiddenException();
        }

        return ApprovalTradeInfoResponse.from(trade);
    }

    @Transactional(readOnly = true)
    public PurchaseTradeInfoResponse getPurchaseTrade(Long memberId, Long tradeId) {
        Member member = memberRepository.getMember(memberId);
        Trade trade = tradeRepository.getTrade(tradeId);

        if (trade.getHasBuyerDeleted()) {
            throw new TradeNotFoundException();
        }
        if (!Objects.equals(member, trade.getBuyer())) {
            throw new AccessForbiddenException();
        }

        return PurchaseTradeInfoResponse.from(trade);
    }

    @Transactional
    public TradeIdResponse deleteTrade(Long memberId, Long tradeId) {
        Member member = memberRepository.getMember(memberId);
        Trade trade = tradeRepository.getTrade(tradeId);

        if (Objects.equals(member, trade.getSeller())) {
            trade.deleteBySeller();
        } else if (Objects.equals(member, trade.getBuyer())) {
            trade.deleteByBuyer();
        } else {
            throw new AccessForbiddenException();
        }

        if (trade.getHasSellerDeleted() && trade.getHasBuyerDeleted()) {
            tradeRepository.delete(trade);
        }

        return TradeIdResponse.from(trade);
    }

    private void validateTradeSaveRequest(
        Product product,
        Member seller,
        Member buyer
    ) {
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
        if (trade.getStatus() != WAITING) {
            throw new IllegalTradeStatusException();
        }
    }

    private void validateTradeRejectRequest(Member seller, Trade trade) {
        if (!Objects.equals(seller, trade.getSeller())) {
            throw new AccessForbiddenException();
        }
        if (trade.getStatus() != WAITING) {
            throw new IllegalTradeStatusException();
        }
    }

    private void validateTradeCancelRequest(Member buyer, Trade trade) {
        if (!Objects.equals(buyer, trade.getBuyer())) {
            throw new AccessForbiddenException();
        }
        if (trade.getStatus() != WAITING) {
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
            LocalDateTime.now(),
            trade.getPaymentType(),
            EntityCodeGenerator.generate()
        );
    }

    private void payBill(Member member, long bill, TradeSaveRequest request, Product product) {
        if (request.paymentType() != YANOLJA_PAY) {
            return;
        }

        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        if (yanoljaPay.getAccountNumber() == null) {
            throw new YanoljaPayNotFoundException();
        }

        if (!Objects.equals(request.simplePassword(), yanoljaPay.getSimplePassword())) {
            throw new IncorrectYanoljaPayPasswordException();
        }

        Accommodation accommodation = product.getOrder().getRoom().getAccommodation();
        yanoljaPay.withdraw(bill);
        yanoljaPayHistoryRepository.save(
            YanoljaPayHistory.create(
                yanoljaPay,
                PURCHASE,
                accommodation.getName(),
                bill,
                WITHDRAW,
                LocalDateTime.now()
            )
        );
    }

    private void receiveBill(Member member, long bill, Product product) {
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);
        Accommodation accommodation = product.getOrder().getRoom().getAccommodation();

        yanoljaPay.deposit(bill);
        yanoljaPayHistoryRepository.save(
            YanoljaPayHistory.create(
                yanoljaPay,
                SALE,
                accommodation.getName(),
                bill,
                DEPOSIT,
                LocalDateTime.now()
            )
        );
    }

    private void refundBill(Member member, long bill, PaymentType paymentType, Product product) {
        if (paymentType != YANOLJA_PAY) {
            return;
        }

        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        if (yanoljaPay.getAccountNumber() == null) {
            throw new YanoljaPayNotFoundException();
        }

        Accommodation accommodation = product.getOrder().getRoom().getAccommodation();
        yanoljaPay.deposit(bill);
        yanoljaPayHistoryRepository.save(
            YanoljaPayHistory.create(
                yanoljaPay,
                REFUND,
                accommodation.getName(),
                bill,
                DEPOSIT,
                LocalDateTime.now()
            )
        );
    }

    @Transactional(readOnly = true)
    public ApprovalTradePageResponse getApprovalTrades(
        Long memberId, TradeStatus status, Pageable pageable
    ) {
        Member member = memberRepository.getMember(memberId);
        if (Objects.equals(status, CANCELED)) {
            throw new UnavailableStatusQueryException();
        }
        Page<Trade> trades = getTrades(member, SELLER, status, pageable);
        return ApprovalTradePageResponse.from(trades.map(ApprovalTradeSummaryResponse::from));
    }

    @Transactional(readOnly = true)
    public PurchaseTradePageResponse getPurchaseTrades(
        Long memberId, TradeStatus status, Pageable pageable
    ) {
        Member member = memberRepository.getMember(memberId);
        if (Objects.equals(status, REJECTED)) {
            throw new UnavailableStatusQueryException();
        }
        Page<Trade> trades = getTrades(member, BUYER, status, pageable);
        return PurchaseTradePageResponse.from(trades.map(PurchaseTradeSummaryResponse::from));
    }

    private Page<Trade> getTrades(
        Member member, TradeRole role, TradeStatus status, Pageable pageable
    ) {
        return tradeRepository.findByMemberRoleAndStatus(
            member, role.name(), status, pageable
        );
    }
}
