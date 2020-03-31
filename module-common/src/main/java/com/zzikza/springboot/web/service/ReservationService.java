package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.reservation.Reservation;
import com.zzikza.springboot.web.domain.reservation.ReservationRepository;
import com.zzikza.springboot.web.dto.ReservationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    @PersistenceContext
    private EntityManager em;

    public void saveReservation(ReservationRequestDto reservationRequestDto) {
        reservationRepository.save(reservationRequestDto.toEntity());
    }
    @Transactional
    public void updateReservation(ReservationRequestDto reservationRequestDto) {
       Reservation reservation = reservationRepository.findById(reservationRequestDto.getId()).orElseThrow(()-> new IllegalArgumentException("예약정보가 없습니다."));
        reservation.update(reservationRequestDto);

    }

    public void deleteReservation(ReservationRequestDto reservationRequestDto) {

    }

//    @Transactional
//    public FileResponseDto saveProductImageFileTemp(String tempKey, MultipartFile file) throws IOException {
//        /*
//        스튜디오로
//        파일들 찾고 사이즈 + 1을 fileOrder로 세팅해야함.
//         */
//        FileAttribute fileAttribute = storageService.fileUpload(file, "product_temp");
//        fileAttribute.setFileOrder(productFileTempRepository.findAllByTempKey(tempKey).size() + 1);
//        ProductFileTemp productFileTemp = ProductFileTemp.builder().fileAttribute(fileAttribute).tempKey(tempKey).build();
//        productFileTempRepository.save(productFileTemp);
//        return new FileResponseDto(productFileTemp);
//    }
}
