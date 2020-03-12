package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.result.SingleResult;
import com.zzikza.springboot.web.service.EditorFileService;
import com.zzikza.springboot.web.service.ResponseService;
import com.zzikza.springboot.web.service.StudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RequiredArgsConstructor
@RestController
public class StudioRestApiController {

    private final ResponseService responseService;

    private final StudioService studioService;

    private final EditorFileService editorFileService;

    @PostMapping(value = "/api/studio-board")
    public SingleResult<StudioBoardResponseDto> insertStudioBoard(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam int fileLength,
            @RequestParam EBoardCategory brdCateCd,
            MultipartHttpServletRequest request
    ) throws Exception {
        StudioBoardRequestDto studioBoardDto = StudioBoardRequestDto.builder()
                .title(title)
                .content(content)
                .brdCateCd(brdCateCd)
                .fileLength(fileLength)
                .build();

//        StudioResponseDto studioResponseDto = (StudioResponseDto) session.getAttribute("sessionVo");
        if (studioResponseDto == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
        studioBoardDto.setStudioSeq(studioResponseDto.getId());

        return responseService.getSingleResult(studioService.saveStudioBoardWithFiles(studioBoardDto, request));
    }

    @PostMapping(value = "/api/editor-image")
    @Transactional
    public SingleResult<EditorFileResponseDto> insertEditorImage(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam("image") MultipartFile file) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
        return responseService.getSingleResult(editorFileService.save(file));
    }
}
