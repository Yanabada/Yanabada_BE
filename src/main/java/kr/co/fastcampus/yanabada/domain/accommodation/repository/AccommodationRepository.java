package kr.co.fastcampus.yanabada.domain.accommodation.repository;

import kr.co.fastcampus.yanabada.common.exception.AccommodationNotFoundException;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    default Accommodation getAccommodation(Long accommodationId) {
        return findById(accommodationId).orElseThrow(AccommodationNotFoundException::new);
    }
}
