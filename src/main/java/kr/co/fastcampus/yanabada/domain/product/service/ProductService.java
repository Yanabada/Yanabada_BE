package kr.co.fastcampus.yanabada.domain.product.service;

import static kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType.YANOLJA_PAY;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.ContentsType.REFUND;
import static kr.co.fastcampus.yanabada.domain.payment.entity.enums.TransactionType.DEPOSIT;
import static kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus.CANCELED;
import static kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus.ON_SALE;
import static kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus.SOLD_OUT;
import static kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus.TIMEOUT;

import io.micrometer.common.util.StringUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import kr.co.fastcampus.yanabada.common.exception.AccessForbiddenException;
import kr.co.fastcampus.yanabada.common.exception.IllegalProductStatusException;
import kr.co.fastcampus.yanabada.common.exception.InvalidStatusProductUpdateException;
import kr.co.fastcampus.yanabada.common.exception.OrderNotSellableException;
import kr.co.fastcampus.yanabada.common.exception.SaleEndDateRangeException;
import kr.co.fastcampus.yanabada.common.exception.SellingPriceRangeException;
import kr.co.fastcampus.yanabada.common.exception.YanoljaPayNotFoundException;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
import kr.co.fastcampus.yanabada.domain.payment.entity.AdminPayment;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.payment.repository.AdminPaymentRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.TradeRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayHistoryRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductPatchRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSaveRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSearchRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductIdResponse;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductInfoResponse;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductSummaryPageResponse;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String CRON_SCHEDULING = "0 0 0 * * *";

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;
    private final YanoljaPayRepository yanoljaPayRepository;
    private final YanoljaPayHistoryRepository yanoljaPayHistoryRepository;
    private final AdminPaymentRepository adminPaymentRepository;

    @Transactional
    public ProductIdResponse saveProduct(
        Long memberId,
        ProductSaveRequest request
    ) {
        Member member = memberRepository.getMember(memberId);
        Order order = orderRepository.getOrder(request.orderId());

        if (!Objects.equals(member, order.getMember())) {
            throw new AccessForbiddenException();
        }
        validateProductSaveRequest(request, order);

        return ProductIdResponse.from(
            productRepository.save(request.toEntity(order))
        );
    }

    @Transactional(readOnly = true)
    public ProductInfoResponse getProductById(Long productId) {
        return ProductInfoResponse.from(productRepository.getProduct(productId));
    }

    @Transactional(readOnly = true)
    public ProductSummaryPageResponse getProductsBySearchRequest(
        ProductSearchRequest request
    ) {
        return ProductSummaryPageResponse.from(
            productRepository.getBySearchRequest(request)
        );
    }

    @Transactional
    public ProductIdResponse updateProduct(
        Long memberId,
        Long productId,
        ProductPatchRequest request
    ) {
        Member member = memberRepository.getMember(memberId);
        Product product = productRepository.getProduct(productId);
        Order order = product.getOrder();

        if (!Objects.equals(member, order.getMember())) {
            throw new AccessForbiddenException();
        }

        validateUpdatableProduct(product);

        if (request.price() != null) {
            validateProductPrice(request.price(), order.getPrice());
            product.updatePrice(request.price());
        }

        if (StringUtils.isNotBlank(request.description())) {
            product.updateDescription(request.description());
        }

        if (request.canNegotiate() != null) {
            product.updateCanNegotiate(request.canNegotiate());
        }

        if (request.saleEndDate() != null) {
            validateProductSaleEndDate(request.saleEndDate(), order.getCheckInDate());
            product.updateSaleEndDate(request.saleEndDate());
        }

        if (request.isAutoCancel() != null) {
            product.updateIsAutoCancel(request.isAutoCancel());
        }

        return ProductIdResponse.from(product);
    }

    @Transactional
    public void cancelProduct(
        Long memberId,
        Long productId
    ) {
        Member member = memberRepository.getMember(memberId);
        Product product = productRepository.getProduct(productId);

        validateProductCancelRequest(member, product);

        cancelTradeRelatedToProduct(product);
        product.cancel();
    }

    @Scheduled(cron = CRON_SCHEDULING)
    @Transactional
    public void expireProducts() {
        List<Product> products = productRepository.getBySaleEndDateExpired();
        products.forEach(
            product -> {
                if (product.getIsAutoCancel()) {
                    product.getOrder().cancel();
                }
                cancelTradeRelatedToProduct(product);
                product.expire();
            }
        );
    }

    private void cancelTradeRelatedToProduct(Product product) {
        AdminPayment adminPayment = adminPaymentRepository.getAdminPayment();

        tradeRepository.findByProduct(product)
            .forEach(trade -> {
                if (trade.getStatus() == TradeStatus.WAITING) {
                    long bill = trade.getSellingPrice() + trade.getFee() - trade.getPoint();
                    refundBill(trade.getBuyer(), bill, trade.getPaymentType(), trade.getProduct());
                    trade.getBuyer().addPoint(trade.getPoint());
                    adminPayment.withdraw(bill);

                    trade.reject();
                    //TODO: Buyer에게 알림 (Optional)
                }
            });
    }

    private void validateProductSaveRequest(
        ProductSaveRequest request,
        Order order
    ) {
        validateSellableOrder(order);
        validateProductPrice(request.price(), order.getPrice());
        validateProductSaleEndDate(request.saleEndDate(), order.getCheckInDate());
    }

    private void validateSellableOrder(Order order) {
        if (order.getStatus() != OrderStatus.RESERVED) {
            throw new OrderNotSellableException();
        }
        if (productRepository.existOnSaleOrBookingByOrder(order)) {
            throw new OrderNotSellableException();
        }
    }

    private void validateProductPrice(int price, int purchasePrice) {
        if (price > purchasePrice || price <= 0) {
            throw new SellingPriceRangeException();
        }
    }

    private void validateProductSaleEndDate(
        LocalDate saleEndDate, LocalDate checkInDate
    ) {
        if (saleEndDate.isBefore(LocalDate.now())
            || saleEndDate.isAfter(checkInDate)) {
            throw new SaleEndDateRangeException();
        }
    }

    private void validateUpdatableProduct(Product product) {
        if (product.getStatus() != ON_SALE) {
            throw new InvalidStatusProductUpdateException();
        }
    }

    private void validateProductCancelRequest(Member member, Product product) {
        if (!Objects.equals(member, product.getOrder().getMember())) {
            throw new AccessForbiddenException();
        }
        if (product.getStatus() == CANCELED
            || product.getStatus() == TIMEOUT
            || product.getStatus() == SOLD_OUT) {
            throw new IllegalProductStatusException();
        }
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
}
