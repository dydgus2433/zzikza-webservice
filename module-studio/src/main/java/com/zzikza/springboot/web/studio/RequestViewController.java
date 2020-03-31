package com.zzikza.springboot.web.studio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class RequestViewController {

//    private final ScheduleRepository scheduleRepository;

    private final String SCHEDULE_VIEW = "/schedule/scheduleView";

//    @GetMapping(value = {"/schedule/view"})
//    public String noticePage(HttpServletRequest request, @RequestParam Map<String, Object> params, Model model) {
//
//        model.addAttribute("date",params);
//        model.addAttribute("title", "찍자사장님 사이트 - 예약관리");
//        return SCHEDULE_VIEW;
//    }

}