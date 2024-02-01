package kr.co.fastcampus.yanabada.domain.accommodation.service;

import java.util.List;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.request.AccommodationOptionSaveRequest;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.request.AccommodationSaveRequest;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.request.RoomOptionSaveRequest;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.request.RoomSaveRequest;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.AccommodationRepository;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    private final RoomRepository roomRepository;

    @Transactional
    public void saveAccommodations(List<AccommodationSaveRequest> requests) {
        requests.stream()
            .map(AccommodationSaveRequest::toEntity)
            .forEach(accommodationRepository::save);
    }

    @Transactional
    public void saveRooms(List<RoomSaveRequest> requests) {
        requests.forEach(request -> {
            Accommodation accommodation =
                accommodationRepository.getAccommodation(request.accommodationId());
            accommodation.addRoom(request.toEntity(accommodation));
        });
    }

    @Transactional
    public void saveAccommodationOptions(List<AccommodationOptionSaveRequest> requests) {
        requests.forEach(request -> {
            Accommodation accommodation =
                accommodationRepository.getAccommodation(request.accommodationId());
            accommodation.registerAccommodationOption(request.toEntity(accommodation));
        });
    }

    @Transactional
    public void saveRoomOptions(List<RoomOptionSaveRequest> requests) {
        requests.forEach(request -> {
            Room room = roomRepository.getRoom(request.roomId());
            room.registerRoomOption(request.toEntity(room));
        });
    }
}
