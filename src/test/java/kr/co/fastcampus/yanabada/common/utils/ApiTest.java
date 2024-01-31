package kr.co.fastcampus.yanabada.common.utils;

import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createAccommodation;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createAccommodationOption;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createBuyer;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createOrder;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createRoom;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createRoomOption;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createSeller;

import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.AccommodationOptionRepository;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.AccommodationRepository;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.RoomOptionRepository;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.RoomRepository;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ApiTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationOptionRepository accommodationOptionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomOptionRepository roomOptionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @BeforeEach
    public void setup() {
        Member seller = memberRepository.save(createSeller());
        memberRepository.save(createBuyer());
        Accommodation accommodation = accommodationRepository.save(createAccommodation());
        accommodationOptionRepository.save(createAccommodationOption(accommodation));
        Room room = roomRepository.save(createRoom(accommodation));
        roomOptionRepository.save(createRoomOption(room));
        Order order = orderRepository.save(createOrder(room,seller));
        //productRepository.save(createProduct(order));
    }
}