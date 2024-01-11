package kr.co.fastcampus.yanabada.domain.product.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import kr.co.fastcampus.yanabada.common.exception.AccessForbiddenException;
import kr.co.fastcampus.yanabada.common.exception.OrderNotFoundException;
import kr.co.fastcampus.yanabada.common.exception.OrderNotSellableException;
import kr.co.fastcampus.yanabada.common.exception.SaleEndDateRangeException;
import kr.co.fastcampus.yanabada.common.exception.SellingPriceRangeException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
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

    @Transactional
    public ProductIdResponse saveProduct(
        Long memberId,
        ProductSaveRequest request
    ) {
        Member member = memberRepository.getMember(memberId);
        Order order = orderRepository.findById(request.orderId())
            .orElseThrow(OrderNotFoundException::new);

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
    @Scheduled(cron = CRON_SCHEDULING)
    public void cancelProductsSaleEndDateExpired() {
        List<Product> products = productRepository.getBySaleEndDateExpired(LocalDate.now());
        products.forEach(
            product -> {
                product.cancel();
                if (product.getIsAutoCancel()) {
                    product.getOrder().cancel();
                }
                //TODO: 상품 취소되면서 Trade 도 같이 reject 돼야함
            }
        );
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
        if (LocalDate.now().isAfter(order.getCheckInDate())) {
            throw new OrderNotSellableException();
        }
    }

    private void validateProductPrice(int price, int purchasePrice) {
        if (price > purchasePrice) {
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
}
