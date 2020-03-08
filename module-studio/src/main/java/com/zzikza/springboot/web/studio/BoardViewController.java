package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import com.zzikza.springboot.web.domain.studio.StudioBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class BoardViewController {

    private final StudioBoardRepository studioBoardRepository;

    @GetMapping(value = { "/board/{brdCateCd}" })
    public String noticePage(@PathVariable("brdCateCd") String brdCateCd, @RequestParam(required = false) Map<String, Object> params, HttpServletRequest request, Model model) {
//        params.put(BRD_CATE_CODE, brdCateCd);
//        params.put(COMMON_CODE, brdCateCd);
//        params.put(GROUP_CODE, BRD_CATE_CD);
//        setStudioId(request, params);
//        setCommonCodeModel(brdCateCd, params, model);
          EBoardCategory boardCategory;
//        pagingSql(params, "selectBoards", model);

        model.addAttribute("title", "찍자사장님 사이트 - 게시판목록");
        return "board/boardList";
    }

}
