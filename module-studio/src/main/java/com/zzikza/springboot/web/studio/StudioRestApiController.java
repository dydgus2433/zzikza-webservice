package com.zzikza.springboot.web.studio;

//import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import com.zzikza.springboot.web.dto.EditorFileResponseDto;
import com.zzikza.springboot.web.dto.StudioBoardRequestDto;
import com.zzikza.springboot.web.dto.StudioBoardResponseDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import com.zzikza.springboot.web.result.SingleResult;
import com.zzikza.springboot.web.service.EditorFileService;
import com.zzikza.springboot.web.service.ResponseService;
import com.zzikza.springboot.web.service.StorageServiceImpl;
import com.zzikza.springboot.web.service.StudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;

@RequiredArgsConstructor
@RestController
public class StudioRestApiController {

    private final ResponseService responseService;

    private final StudioService studioService;

    private final EditorFileService editorFileService;

    private final StorageServiceImpl storageService;

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

    @GetMapping(value = "/api/fileDownload")
    public ResponseEntity<Resource> fileDownload(@RequestParam String fileName,
                                                 @RequestParam String originName,
                                                 HttpServletRequest request) throws IOException {
        Resource resource = storageService.loadAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(contentType == null){
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(originName, "UTF-8")  + "\"")
                .body(resource);

    }
}
