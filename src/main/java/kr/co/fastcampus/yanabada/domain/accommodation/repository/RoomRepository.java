package kr.co.fastcampus.yanabada.domain.accommodation.repository;

import kr.co.fastcampus.yanabada.common.exception.RoomNotFoundException;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    default Room getRoom(Long roomId) {
        return findById(roomId).orElseThrow(RoomNotFoundException::new);
    }
}
