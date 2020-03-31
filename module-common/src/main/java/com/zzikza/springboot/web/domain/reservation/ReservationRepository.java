package com.zzikza.springboot.web.domain.reservation;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
//    List<Reservation> findAllByBetweenReservationStartDateAndReservationEndDate(LocalDate localDate, Sort sort);
    List<Reservation> findAllByReservationStartDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime2, Sort sort);
    List<Reservation> findAllByReservationStartDate(LocalDate localDateTime, Sort sort);
//      List<Reservation> findAllBy
//    List<Reservation> findAllByCreatedDateIsBetween()
}
