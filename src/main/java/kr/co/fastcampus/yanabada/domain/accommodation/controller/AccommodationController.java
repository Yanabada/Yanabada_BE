package kr.co.fastcampus.yanabada.domain.accommodation.controller;

import java.util.List;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.request.AccommodationOptionSaveRequest;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.request.AccommodationSaveRequest;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.request.RoomOptionSaveRequest;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.request.RoomSaveRequest;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.AccommodationOption;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.RoomOption;
import kr.co.fastcampus.yanabada.domain.accommodation.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping
    public ResponseBody<Void> addAccommodations(
        @RequestBody List<AccommodationSaveRequest> requests
    ) {
        accommodationService.saveAccommodations(requests);
        return ResponseBody.ok();
    }

    @PostMapping("/options")
    public ResponseBody<Void> addAccommodationOptions(
        @RequestBody List<AccommodationOptionSaveRequest> requests
    ) {
        accommodationService.saveAccommodationOptions(requests);
        return ResponseBody.ok();
    }

    @PostMapping("/rooms")
    public ResponseBody<Void> addRoom(@RequestBody List<RoomSaveRequest> requests) {
        accommodationService.saveRooms(requests);
        return ResponseBody.ok();
    }

    @PostMapping("/rooms/options")
    public ResponseBody<Void> addRoomOptions(@RequestBody List<RoomOptionSaveRequest> requests) {
        accommodationService.saveRoomOptions(requests);
        return ResponseBody.ok();
    }
}
