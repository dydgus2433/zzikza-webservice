package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import com.zzikza.springboot.web.dto.StudioBoardRequestDto;
import com.zzikza.springboot.web.dto.StudioBoardResponseDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import com.zzikza.springboot.web.result.SingleResult;
import com.zzikza.springboot.web.service.ResponseService;
import com.zzikza.springboot.web.service.StudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class StudioRestApiController {

    private final ResponseService responseService;

    private final StudioService studioService;

    @PostMapping(value = "/api/studio-board")
    public SingleResult<StudioBoardResponseDto> insertStudioBoard(@RequestParam String title,
                                                                  @RequestParam String content,
                                                                  @RequestParam int fileLength,
                                                                  @RequestParam EBoardCategory brdCateCd,
                                                                  MultipartHttpServletRequest request,
                                                                  HttpSession session) throws Exception {
        StudioBoardRequestDto studioBoardDto = StudioBoardRequestDto.builder()
                .title(title)
                .content(content)
                .brdCateCd(brdCateCd)
                .fileLength(fileLength)
                .build();

        StudioResponseDto studioResponseDto = (StudioResponseDto) session.getAttribute("sessionVo");
        if(studioResponseDto == null){
            throw new IllegalAccessException("로그인 해주세요.");
        }
        studioBoardDto.setStudioSeq(studioResponseDto.getId());

        return responseService.getSingleResult(studioService.saveStudioBoardWithFiles(studioBoardDto, request));
    }


//    private void insertBoardFile(Map<String, Object> params, HttpServletRequest request, MultipartFile file) throws Exception {
//        if (file.getSize() > 0) {
//            Map<String, Object> result = FileUtils.uploadFile(file);
//            setStudioId(request, result);
//            result.put(BRD_ID, params.get(BRD_ID));
//            executeService("insertBoardFile", result, this.boardService.getClass());
//        }
//    }
}
