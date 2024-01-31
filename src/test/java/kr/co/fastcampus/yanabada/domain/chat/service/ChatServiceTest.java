package kr.co.fastcampus.yanabada.domain.chat.service;

import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createAccommodation;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createAccommodationOption;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createOrder;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createProduct;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createRoom;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createRoomOption;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.common.exception.CannotNegotiateOwnProductException;
import kr.co.fastcampus.yanabada.common.utils.EntityCodeGenerator;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.AccommodationOption;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.RoomOption;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomModifyRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomSaveRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomInfoResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomModifyResponse;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import kr.co.fastcampus.yanabada.domain.chat.repository.ChatMessageRepository;
import kr.co.fastcampus.yanabada.domain.chat.repository.ChatRoomRepository;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.notification.service.NotificationService;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ChatService chatService;

    @Test
    @DisplayName("채팅방 생성 성공")
    void getOrSaveChatRoom_success() {

        //given
        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        room.registerRoomOption(roomOption);
        accommodation.addRoom(room);
        Member seller = mock(Member.class);
        Member buyer = mock(Member.class);
        Order order = createOrder(room, seller);
        Product product = createProduct(order);
        ChatRoom chatRoom = mock(ChatRoom.class);

        given(productRepository.getProduct(1L)).willReturn(product);
        given(seller.getId()).willReturn(2L);
        given(memberRepository.getMember(2L)).willReturn(seller);
        given(memberRepository.getMember(1L)).willReturn(buyer);
        when(chatRoomRepository.save(any())).thenReturn(chatRoom);

        //when
        ChatRoomSaveRequest request = new ChatRoomSaveRequest(1L, 1L);
        ChatRoomInfoResponse result = chatService.getOrSaveChatRoom(request);

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("채팅방 생성 실패")
    void getOrSaveChatRoom_fail() {

        //given
        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        room.registerRoomOption(roomOption);
        accommodation.addRoom(room);
        Member seller = mock(Member.class);
        Member buyer = mock(Member.class);
        Order order = createOrder(room, seller);
        Product product = createProduct(order);
        ChatRoom chatRoom = mock(ChatRoom.class);

        given(productRepository.getProduct(1L)).willReturn(product);
        given(seller.getId()).willReturn(1L);
        given(memberRepository.getMember(1L)).willReturn(seller);
        given(memberRepository.getMember(1L)).willReturn(buyer);

        //when then
        ChatRoomSaveRequest request = new ChatRoomSaveRequest(1L, 1L);
        assertThrows(CannotNegotiateOwnProductException.class, () -> {
            chatService.getOrSaveChatRoom(request);
        });
    }

    @Test
    @DisplayName("채팅방 수정 성공")
    void updateChatRoom_success() {

        //given
        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        room.registerRoomOption(roomOption);
        accommodation.addRoom(room);
        Member seller = mock(Member.class);
        Member buyer = mock(Member.class);
        Order order = createOrder(room, seller);
        Product product = createProduct(order);
        String code = EntityCodeGenerator.generate();
        ChatRoom chatRoom = ChatRoom.create(
            product, seller, buyer,
            code,
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        given(chatRoomRepository.getChatroom(any())).willReturn(chatRoom);
        given(memberRepository.getMember(1L)).willReturn(buyer);

        //when
        ChatRoomModifyRequest request = new ChatRoomModifyRequest(code);
        ChatRoomModifyResponse result = chatService.updateChatRoom(1L, request);

        //then
        assertNotNull(result);
        assertThat(result.chatRoomCode()).isEqualTo(code);
        assertThat(result.updatedMemberId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("채팅방 삭제 성공")
    void modifyOrDeleteChatRoom_success() {

        //given
        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        room.registerRoomOption(roomOption);
        accommodation.addRoom(room);
        Member seller = mock(Member.class);
        Member buyer = mock(Member.class);
        Order order = createOrder(room, seller);
        Product product = createProduct(order);
        String code = EntityCodeGenerator.generate();
        ChatRoom chatRoom = ChatRoom.create(
            product, seller, buyer,
            code,
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        given(chatRoomRepository.getChatroom(any())).willReturn(chatRoom);
        given(memberRepository.getMember(1L)).willReturn(buyer);

        //when
        ChatRoomModifyRequest request = new ChatRoomModifyRequest(code);
        ChatRoomModifyResponse result = chatService.modifyOrDeleteChatRoom(1L, request);

        //then
        assertNotNull(result);
        assertThat(result.chatRoomCode()).isEqualTo(code);
        assertThat(result.updatedMemberId()).isEqualTo(1L);
    }
}
