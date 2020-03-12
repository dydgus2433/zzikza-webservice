package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import com.zzikza.springboot.web.domain.studio.StudioBoard;
import com.zzikza.springboot.web.domain.studio.StudioBoardFile;
import com.zzikza.springboot.web.domain.studio.StudioBoardFileRepository;
import com.zzikza.springboot.web.domain.studio.StudioBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class BoardViewController {

    private final StudioBoardRepository studioBoardRepository;
    private final StudioBoardFileRepository studioBoardFileRepository;

    private final String BOARD_WRITE_VIEW = "/board/boardWrite";
    private final String BOARD_DETAIL_VIEW = "/board/boardView";
    private final String BOARD_LIST_VIEW = "/board/boardList";

    @GetMapping(value = {"/board/{brdCateCd}"})
    public String noticePage(@PathVariable("brdCateCd") String brdCateCd, Pageable pageable, HttpServletRequest request, Model model) {
        setBoardCategoriesWithSelected(brdCateCd, model);

        Page<StudioBoard> paging = studioBoardRepository.findAllByBoardCategoryCodeEquals(EBoardCategory.valueOf(brdCateCd), pageable);
        setPagingParameters(model, paging);
        model.addAttribute("brdCateCd", brdCateCd);
        model.addAttribute("title", "찍자사장님 사이트 - 게시판목록");
        return BOARD_LIST_VIEW;
    }

    @GetMapping(value = "/board/{brdCateCd}/write")
    public String write(@PathVariable String brdCateCd, Model model) {
        setBoardCategoriesWithSelected(brdCateCd, model);

        model.addAttribute("title", "찍자사장님 사이트 - 상세 글작성");
        return BOARD_WRITE_VIEW;
    }

    @GetMapping(value = {"/board/{brdCateCd}/view/{brdId}"})
    @Transactional(readOnly = true)
    public String read(@PathVariable String brdCateCd, @PathVariable String brdId, Model model) {
        setBoardCategoriesWithSelected(brdCateCd, model);

        StudioBoard studioBoard = studioBoardRepository.findById(brdId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        model.addAttribute("board", studioBoard);
        List<StudioBoardFile> studioBoardFile = studioBoardFileRepository.findAllByStudioBoard(studioBoard);
        model.addAttribute("files", studioBoardFile);


        StudioBoard nextBoard = studioBoardRepository.findNextBoard(studioBoard.getId(), studioBoard.getBoardCategoryCode().getKey()).orElse(null);
        StudioBoard prevBoard = studioBoardRepository.findPrevBoard(studioBoard.getId(), studioBoard.getBoardCategoryCode().getKey()).orElse(null);
//        StudioBoard prevBoard =studioBoardRepository.findPrevBoard(studioBoard.getId()).orElseThrow(()->new IllegalArgumentException("이전 글이 없습니다."));
        model.addAttribute("prev", prevBoard);
        model.addAttribute("next", nextBoard);
        model.addAttribute("title", "찍자사장님 사이트 - 상세 글보기");
        return BOARD_DETAIL_VIEW;
    }


    private void setPagingParameters(Model model, Page<?> paging) {
        Pageable pageable = paging.getPageable();
        int firstIndexNotZero = 1;
        int nowPage = pageable.getPageNumber() + firstIndexNotZero;

        model.addAttribute("is-paging", paging.getPageable().isPaged());
        model.addAttribute("is-fisrt", paging.isFirst());
        List<Map<String, Object>> pageList = new ArrayList<>();
        for (int i = paging.getPageable().first().getPageNumber() + firstIndexNotZero; i < paging.getTotalPages() + firstIndexNotZero; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("idx", i);
            map.put("now-active", i == nowPage);
            pageList.add(map);
        }
        model.addAttribute("pageList", pageList);
        model.addAttribute("now-page", nowPage); //현재 페이지
        model.addAttribute("prev-page", nowPage - 1); //이전 페이지
        model.addAttribute("next-page", nowPage + 1); //다음 페이지
        model.addAttribute("paging-fnc", "fn_paging");
        model.addAttribute("has-next", paging.hasNext());
        model.addAttribute("last-page-no", paging.getTotalPages());

        model.addAttribute("list", paging.getContent());
    }


    private void setBoardCategoriesWithSelected(@PathVariable String brdCateCd, Model model) {
        EBoardCategory selected = EBoardCategory.valueOf(EBoardCategory.class, brdCateCd.toLowerCase());
        List<Map<String, Object>> commonCodes = new ArrayList<>();
        for (EBoardCategory commonCode : EBoardCategory.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("commCd", commonCode.getKey());
            map.put("commCdNm", commonCode.getValue());
            //QNA는 질문가능
            if (commonCode.ordinal() == EBoardCategory.qna.ordinal()) {
                map.put("qna", true);
            }
            //selected 옵션
            if (commonCode.equals(selected)) {
                map.put("selected", true);
                model.addAttribute("commonCode", map);
            }
            commonCodes.add(map);
        }
        model.addAttribute("commonCodes", commonCodes);
    }
}