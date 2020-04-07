package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.domain.enums.EReservationStatus;
import com.zzikza.springboot.web.domain.studio.*;
import com.zzikza.springboot.web.dto.StudioQuestionReplyResponseDto;
import com.zzikza.springboot.web.dto.StudioQuestionResponseDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import com.zzikza.springboot.web.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class QuestionViewController {

    private final StudioQuestionRepository studioQuestionRepository;
    private final StudioQuestionReplyRepository studioQuestionReplyRepository;
    private final StudioRepository studioRepository;

    private final String QUESTION_LIST = "/question/questionList";
    private final String QUESTION_POPUP = "/question/questionPopup";

    @Transactional(readOnly = true)
    @GetMapping(value = {"/question/list"})
    public String questionListPage(@LoginStudio StudioResponseDto sessionVo, Pageable pageable, Model model) {
        if (sessionVo == null) {
            try {
                throw new IllegalAccessException("로그인 해주세요.");
//        에러시 페이지 이동 해야함
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //detail
//        assert sessionVo != null;
        Studio studio = studioRepository.findById(sessionVo.getId()).orElseThrow(() -> new IllegalArgumentException("해당 스튜디오가 존재하지 않습니다."));

        Page<StudioQuestion> paging = studioQuestionRepository.findAllByStudio(studio, pageable);
        List<StudioQuestion> list = (List<StudioQuestion>) PagingUtil.setPagingParameters(model, paging);
        model.addAttribute("list", list.stream().map(StudioQuestionResponseDto::new).collect(Collectors.toList()));
        model.addAttribute("title", "찍자사장님 사이트 - 문의 목록");
        return QUESTION_LIST;
    }
///question/popup

    @Transactional(readOnly = true)
    @GetMapping(value = {"/question/popup"})
    public String questionPopupPage(@RequestParam(required = false) String id
            , Model model) {
        StudioQuestion studioQuestion = studioQuestionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("본문이 없습니다."));
        List<StudioQuestionReply> questionReplyList = studioQuestionReplyRepository.findAllByStudioQuestion(studioQuestion);
        //현재는 하나만 답변할 수 있도록 되어있음.
        model.addAttribute("question", new StudioQuestionResponseDto(studioQuestion));
        if(!questionReplyList.isEmpty()){
            model.addAttribute("answer", new StudioQuestionReplyResponseDto(questionReplyList.get(0)));
        }

//        List<Map<String, Object>> commonCodes = new ArrayList<>();
//        for (EReservationStatus commonCode : EReservationStatus.values()) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("commCd", commonCode.getKey());
//            map.put("commCdNm", commonCode.getValue());
//            map.put("selected", commonCode.equals(reservation.getReservationStatus()));
//            //selected 옵션
//            commonCodes.add(map);
//        }
//        model.addAttribute("commonCodes", commonCodes);
        return QUESTION_POPUP;
    }
}