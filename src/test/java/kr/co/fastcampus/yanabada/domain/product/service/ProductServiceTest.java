package kr.co.fastcampus.yanabada.domain.product.service;

import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createAccommodation;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createAccommodationOption;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createOrder;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createProduct;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createRoom;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createRoomOption;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createSeller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.AccommodationOption;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.RoomOption;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.notification.service.NotificationService;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.AdminPaymentRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayHistoryRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSaveRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSearchRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.request.enums.ProductSearchCategory;
import kr.co.fastcampus.yanabada.domain.product.dto.request.enums.ProductSearchOrderCondition;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductIdResponse;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductInfoResponse;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductSummaryPageResponse;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import kr.co.fastcampus.yanabada.domain.trade.repository.TradeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private YanoljaPayRepository yanoljaPayRepository;
    @Mock
    private YanoljaPayHistoryRepository yanoljaPayHistoryRepository;
    @Mock
    private AdminPaymentRepository adminPaymentRepository;
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private ProductService productService;

    @DisplayName("정상적인 상품 등록일 경우, 등록되 상품 아이디를 반환합니다.")
    @Test
    void saveProduct() {
        //given
        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        room.registerRoomOption(roomOption);
        accommodation.addRoom(room);
        Member member = createSeller();
        Order order = createOrder(room, member);
        ProductSaveRequest request = new ProductSaveRequest(
            1L,
            order.getPrice(),
            "description",
            true,
            LocalDate.now(),
            true
        );
        given(memberRepository.getMember(1L)).willReturn(member);
        given(orderRepository.getOrder(1L)).willReturn(order);
        given(productRepository.save(any())).willReturn(createProduct(order));

        //when
        ProductIdResponse productIdResponse = productService.saveProduct(1L, request);

        //then
        assertThat(productIdResponse).isNotNull();
    }

    @DisplayName("정상적인 상품 상세 조회 요청일 경우, 해당 상품을 반환합니다.")
    @Test
    void getProductById() {
        //given
        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        room.registerRoomOption(roomOption);
        accommodation.addRoom(room);
        Member member = createSeller();
        Order order = createOrder(room, member);
        Product product = createProduct(order);
        given(productRepository.getProduct(1L)).willReturn(product);

        //when
        ProductInfoResponse productInfoResponse = productService.getProductById(1L);

        //then
        assertThat(productInfoResponse).isNotNull();
        assertThat(productInfoResponse.roomInfo().name())
            .isEqualTo(product.getOrder().getRoom().getName());
    }

    @DisplayName("정상적인 상품 상세 조회 요청일 경우, 해당 상품을 반환합니다.")
    @Test
    void getProductsBySearchRequest() {
        //given
        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        room.registerRoomOption(roomOption);
        accommodation.addRoom(room);
        Member member = createSeller();
        Order order = createOrder(room, member);
        Product product = createProduct(order);
        ProductSearchRequest request = new ProductSearchRequest(
            "keyword",
            LocalDate.now(),
            LocalDate.now().plusDays(2L),
            2,
            3,
            123.123,
            124.124,
            125.125,
            126.126,
            true,
            ProductSearchOrderCondition.RECENT,
            ProductSearchCategory.PENSION,
            null,
            1,
            10
        );
        given(productRepository.getBySearchRequest(any()))
            .willReturn(new PageImpl<>(List.of(product)));

        //when
        ProductSummaryPageResponse products = productService.getProductsBySearchRequest(request);

        //then
        assertThat(products).isNotNull();
        assertThat(products.totalElements()).isEqualTo(1);
    }

    @DisplayName("정상적인 상품 취소 요청일 경우, 상품의 상태가 취소로 변환됩니다.")
    @Test
    void cancelProduct() {
        //given
        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        room.registerRoomOption(roomOption);
        accommodation.addRoom(room);
        Member member = createSeller();
        Order order = createOrder(room, member);
        Product product = createProduct(order);
        given(memberRepository.getMember(1L)).willReturn(member);
        given(productRepository.getProduct(1L)).willReturn(product);
        given(tradeRepository.findByProduct(product)).willReturn(List.of());

        //when
        ProductIdResponse response = productService.cancelProduct(1L, 1L);

        //then
        assertThat(product.getStatus()).isEqualTo(ProductStatus.CANCELED);
    }
}