package com.zzikza.springboot.web.studio;

//import com.zzikza.springboot.web.annotaion.LoginStudio;

import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import com.zzikza.springboot.web.domain.studio.StudioHolidayRepository;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.result.CommonResult;
import com.zzikza.springboot.web.result.SingleResult;
import com.zzikza.springboot.web.service.FileService;
import com.zzikza.springboot.web.service.ResponseService;
import com.zzikza.springboot.web.service.StorageServiceImpl;
import com.zzikza.springboot.web.service.StudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;

@RequiredArgsConstructor
@RestController
public class StudioRestApiController {



    private final ResponseService responseService;

    private final StudioService studioService;

    private final FileService editorFileService;

    private final StorageServiceImpl storageService;

    private final StudioHolidayRepository studioHolidayRepository;

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
    public SingleResult<FileResponseDto> insertEditorImage(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam("image") MultipartFile file) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
        return responseService.getSingleResult(editorFileService.saveEditorImageFile(file));
    }

    @GetMapping(value = "/api/file-download")
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

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(originName, "UTF-8") + "\"")
                .body(resource);

    }


    @PostMapping(value = "/api/studio-holiday")
    public SingleResult<StudioHolidayResponseDto> insertStudioHoliday(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String dateCode,
            @RequestParam String dateValue

    ) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
        StudioHolidayRequestDto params = new StudioHolidayRequestDto(dateCode, dateValue);
        return responseService.getSingleResult(studioService.saveHoliday(studioResponseDto, params));
    }

    @DeleteMapping(value = "/api/studio-holiday")
    public SingleResult<StudioHolidayResponseDto> deleteStudioHoliday(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String dateCode,
            @RequestParam String dateValue) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
        StudioHolidayRequestDto params = new StudioHolidayRequestDto(dateCode, dateValue);
        return responseService.getSingleResult(studioService.deleteHoliday(studioResponseDto, params));
    }

    @PostMapping(value = "/api/studio-file")
    @Transactional
    public SingleResult<FileResponseDto> insertImageFile(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam("detailFiles") MultipartFile file) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }

        return responseService.getSingleResult(editorFileService.saveStudioImageFile(studioResponseDto, file));
    }

    @PutMapping(value = "/api/studio-file/order")
    public CommonResult studioFileOrder(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String index
    ) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
//            return responseService.getFailResult();
        }
        studioService.saveFileOrders(studioResponseDto, index);
        return responseService.getSuccessResult();
    }

    //
    @PutMapping(value = "/api/studio-detail")
    @Transactional
    public CommonResult insertStudioDetail(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String stdoDsc,
            @RequestParam int openDayStartTm,
            @RequestParam int openDayEndTm,
            @RequestParam int wkndStartTm,
            @RequestParam int wkndDayEndTm,
            @RequestParam String keyword
    ) throws IllegalAccessException {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
        StudioDetailRequestDto studioDetailRequestDto = StudioDetailRequestDto.builder()
                .studioDescription(stdoDsc)
                .openTime(openDayStartTm)
                .closeTime(openDayEndTm)
                .weekendOpenTime(wkndStartTm)
                .weekendCloseTime(wkndDayEndTm)
                .build();
        studioService.updateStudioDetail(studioResponseDto, studioDetailRequestDto);
        studioService.updateStudioKeyword(studioResponseDto, keyword);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/api/studio-file")
    public CommonResult deleteStudioFile(
            @RequestParam String index,
            @RequestParam String flNm
    ) {
            //        index: indexStr, flNm: src
        /*
        파일 버킷에서 실제 삭제 해줘야함
         */
        studioService.deleteStudioFileById(index);
        return responseService.getSuccessResult();
    }
}
