package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.domain.enums.EReservationStatus;
import com.zzikza.springboot.web.domain.reservation.Reservation;
import com.zzikza.springboot.web.domain.reservation.ReservationRepository;
import com.zzikza.springboot.web.dto.ReservationResposeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class ReservationViewController {

    private final ReservationRepository reservationRepository;

    private final String RESERVATION_VIEW = "/reservation/reservationMonth";
    private final String RESERVATION_LIST = "/reservation/reservationDay";
    private final String RESERVATION_POPUP = "/reservation/reservationPopup";

    @GetMapping(value = {"/reservation/month"})
    public String reservationViewPage(
            @RequestParam(required = false) Integer month
            , @RequestParam(required = false) Integer year
            , Model model) {
        if (Objects.isNull(month) || Objects.isNull(year)) {
            setDateAttribute(model);
        } else {
            model.addAttribute("month", month);
            model.addAttribute("year", year);
        }

        model.addAttribute("title", "찍자사장님 사이트 - 예약관리(월)");
        return RESERVATION_VIEW;
    }


    @GetMapping(value = {"/reservation/day"})
    public String reservationListPage(
            @RequestParam(required = false) Integer day
            , @RequestParam(required = false) Integer month
            , @RequestParam(required = false) Integer year
            , Model model) {
        LocalDate localDate;
        if (Objects.isNull(day) || Objects.isNull(month) || Objects.isNull(year)) {
            localDate = setDateAttribute(model);
        } else {
            model.addAttribute("day", day);
            model.addAttribute("month", month);
            model.addAttribute("year", year);
            model.addAttribute("minus-month", month - 1);
            localDate = LocalDate.of(year, month, day);
        }

        //오늘의 예약리스트 (예약시작시간 이 오늘 안에 있는지
        model.addAttribute("list", reservationRepository.findAllByReservationStartDate(localDate, Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(ReservationResposeDto::new).collect(Collectors.toList()));
        model.addAttribute("title", "찍자사장님 사이트 - 예약관리(일)");
        return RESERVATION_LIST;
    }


    @GetMapping(value = {"/reservation/popup"})
    public String reservationPopupPage(@RequestParam(required = false) String id
            , @RequestParam(required = false) String date
            , Model model) {
        List<Map<String, Object>> times = new ArrayList<>();
        List<Map<String, Object>> minutes = new ArrayList<>();
        if (Objects.isNull(id)) {
            for (int i = 0; i < 24; i++) {
                Map<String, Object> time = new HashMap<>();
                time.put("idx", i);
                times.add(time);
            }
            for (int i = 0; i < 60; i += 10) {
                Map<String, Object> minute = new HashMap<>();
                minute.put("minute", i);
                minutes.add(minute);
            }


            List<Map<String, Object>> commonCodes = new ArrayList<>();
            for (EReservationStatus commonCode : EReservationStatus.values()) {
                Map<String, Object> map = new HashMap<>();
                map.put("commCd", commonCode.getKey());
                map.put("commCdNm", commonCode.getValue());
                //selected 옵션
                commonCodes.add(map);
            }
            model.addAttribute("commonCodes", commonCodes);

        } else {
            Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));

            model.addAttribute("detail", new ReservationResposeDto(reservation));

            for (int i = 0; i < 24; i++) {
                Map<String, Object> time = new HashMap<>();
                time.put("idx", i);
                time.put("is-start-time", reservation.getReservationStartHour() == i);
                time.put("is-end-time", reservation.getReservationEndHour() == i);
                times.add(time);
            }
            for (int i = 0; i < 60; i++) {
                Map<String, Object> minute = new HashMap<>();
                minute.put("minute", i);
                minute.put("is-start-minute", reservation.getReservationStartMinute() == i);
                minute.put("is-end-minute", reservation.getReservationEndMinute() == i);
                minutes.add(minute);
            }


            List<Map<String, Object>> commonCodes = new ArrayList<>();
            for (EReservationStatus commonCode : EReservationStatus.values()) {
                Map<String, Object> map = new HashMap<>();
                map.put("commCd", commonCode.getKey());
                map.put("commCdNm", commonCode.getValue());
                map.put("selected", commonCode.equals(reservation.getReservationStatus()));
                //selected 옵션
                commonCodes.add(map);
            }
            model.addAttribute("commonCodes", commonCodes);

        }

        model.addAttribute("date", date);
        model.addAttribute("times", times);
        model.addAttribute("minutes", minutes);
        model.addAttribute("title", "찍자사장님 사이트 - 예약관리(일)");
        return RESERVATION_POPUP;
    }

    private LocalDate setDateAttribute(Model model) {
        LocalDate registedDate = LocalDate.now();
        Integer day = registedDate.getDayOfMonth();
        Integer month = registedDate.getMonth().getValue();
        Integer year = registedDate.getYear();
        model.addAttribute("day", day);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("minus-month", month - 1);
        return registedDate;
    }
}