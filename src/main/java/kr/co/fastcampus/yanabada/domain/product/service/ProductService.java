package kr.co.fastcampus.yanabada.domain.product.service;

import static kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus.ON_SALE;

import io.micrometer.common.util.StringUtils;
import java.time.LocalDate;
import java.util.Objects;
import kr.co.fastcampus.yanabada.common.exception.AccessForbiddenException;
import kr.co.fastcampus.yanabada.common.exception.InvalidStatusProductUpdateException;
import kr.co.fastcampus.yanabada.common.exception.OrderNotFoundException;
import kr.co.fastcampus.yanabada.common.exception.OrderNotSellableException;
import kr.co.fastcampus.yanabada.common.exception.SaleEndDateRangeException;
import kr.co.fastcampus.yanabada.common.exception.SellingPriceRangeException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductPatchRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSaveRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSearchRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductIdResponse;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductInfoResponse;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductSummaryPageResponse;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

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
}
