package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioDetail;
import com.zzikza.springboot.web.domain.studio.StudioQuestion;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class InfoViewController {

    private final StudioRepository studioRepository;

    private String INFOMATION_VIEW = "/info/infoView";
    private String INFOMATION_PASSWORD = "/info/infoPassword";
    private String INFOMATION_BUSINESS = "/info/infoBusiness";
    private String INFOMATION_WITHDRAWAL = "/info/infoWithdrawal";


    @GetMapping(value = {"/info/view"})
    public String infoViewPage(@LoginStudio StudioResponseDto sessionVo, @RequestParam Map<String, Object> params, Model model) {
//        setStudioId(request, params);
//        Map<String, Object> info = getOne("selectInfo", params);
//        model.addAttribute("info", info);
        Studio studio = studioRepository.findById(sessionVo.getId()).orElseThrow(()-> new IllegalArgumentException("스튜디오가 없습니다."));
        model.addAttribute("detail", studio);

        //detail
        model.addAttribute("title", "찍자사장님 사이트 - 회원정보 수정");
        return INFOMATION_VIEW;
    }

    @GetMapping(value = {"/info/password"})
    public String infoPasswordPage(@LoginStudio StudioResponseDto sessionVo,  Model model) {

//        Map<String, Object> info = getOne("selectInfo", params);
//        model.addAttribute("info", info);
        model.addAttribute("title", "찍자사장님 사이트 - 비밀번호 수정");
        return INFOMATION_PASSWORD;
    }

    @GetMapping(value = {"/info/business"})
    public String infoBusinessPage(@LoginStudio StudioResponseDto sessionVo,  Model model) {

//        Map<String, Object> info = getOne("selectInfo", params);
//        model.addAttribute("info", info);
        Studio studio = studioRepository.findById(sessionVo.getId()).orElseThrow(()-> new IllegalArgumentException("스튜디오가 없습니다."));
        model.addAttribute("detail", studio);
        model.addAttribute("title", "찍자사장님 사이트 - 사업자정보 수정");
        return INFOMATION_BUSINESS;
    }

    @GetMapping(value = {"/info/withdrawal"})
    public String withdrawalPage(HttpServletRequest request, @RequestParam Map<String, Object> params, Model model) {

        model.addAttribute("title", "찍자사장님 사이트 - 예약관리");
        return INFOMATION_WITHDRAWAL;
    }

}