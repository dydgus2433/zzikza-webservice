package com.zzikza.springboot.web.studio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.domain.studio.StudioHolidayRepository;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.result.CommonResult;
import com.zzikza.springboot.web.result.ListResult;
import com.zzikza.springboot.web.result.SingleResult;
import com.zzikza.springboot.web.service.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class StudioRestApiController {


    private final ResponseService responseService;

    private final StudioService studioService;

    private final ProductService productService;

    private final UserReqeustProductService userReqeustProductService;

    private final FileService editorFileService;

    private final StorageServiceImpl storageService;

    private final ReservationService reservationService;
    private final StudioQuestionService studioQuestionService;

    private final StudioHolidayRepository studioHolidayRepository;

    @PostMapping(value = "/api/studio-board")
    public SingleResult<StudioBoardResponseDto> insertStudioBoard(
            @LoginStudio StudioResponseDto studioResponseDto,
            StudioBoardRequestDto studioBoardDto,
            MultipartHttpServletRequest request
    ) throws Exception {
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
            StudioHolidayRequestDto params

    ) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
//        StudioHolidayRequestDto params = new StudioHolidayRequestDto(dateCode, dateValue);
        return responseService.getSingleResult(studioService.saveHoliday(studioResponseDto, params));
    }

    @DeleteMapping(value = "/api/studio-holiday")
    public SingleResult<StudioHolidayResponseDto> deleteStudioHoliday(
            @LoginStudio StudioResponseDto studioResponseDto,
            StudioHolidayRequestDto params) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
//        StudioHolidayRequestDto params =  StudioHolidayRequestDto.builder()
//                .id(id)
//                .dateCode(dateCode)
//                .dateValue(dateValue)
//                .build();
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
            @RequestParam String keyword,
            StudioDetailRequestDto studioDetailRequestDto
    ) throws IllegalAccessException {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
//        StudioDetailRequestDto studioDetailRequestDto = StudioDetailRequestDto.builder()
//                .studioDescription(studioDescription)
//                .openTime(openTime)
//                .closeTime(closeTime)
//                .weekendOpenTime(weekendOpenTime)
//                .weekendCloseTime(weekendCloseTime)
//                .build();
        studioService.updateStudioDetail(studioResponseDto, studioDetailRequestDto);
        studioService.updateStudioKeyword(studioResponseDto, keyword);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/api/studio-file")
    public CommonResult deleteStudioFile(
            @RequestParam String index,
            @RequestParam String flNm
    ) {
        studioService.deleteStudioFileById(index);
        return responseService.getSuccessResult();
    }

    //임시 상품 파일 CRUN
    @PostMapping(value = "/api/product-file/temp")
    @Transactional
    public SingleResult<FileResponseDto> insertProductImageFileTemp(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam(value = "tempKey", required = false) String tempKey,
            @RequestParam("detailFiles") MultipartFile file) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }

        return responseService.getSingleResult(productService.saveProductImageFileTemp(tempKey, file));
    }


    @PutMapping(value = "/api/product-file/temp/order")
    public CommonResult productFileOrderTemp(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam(value = "tempKey", required = false) String tempKey,
            @RequestParam String index
    ) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
//            return responseService.getFailResult();
        }
        productService.saveFileTempOrders(tempKey, index);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/api/product-file/temp")
    public CommonResult deleteProductFileTemp(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String index,
            @RequestParam(value = "tempKey", required = false) String tempKey,
            @RequestParam(required = false) String flNm
    ) throws IllegalAccessException {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
//            return responseService.getFailResult();
        }
        productService.deleteProductFileTempById(index);
        return responseService.getSuccessResult();
    }


    //실제 상품 수정 및 업로드
    @PostMapping(value = "/api/product-file")
    @Transactional
    public SingleResult<FileResponseDto> insertProductImageFile(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String prdId,
            @RequestParam("detailFiles") MultipartFile file) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }

        return responseService.getSingleResult(productService.saveProductImageFile(prdId, file));
    }


    @PutMapping(value = "/api/product-file/order")
    public CommonResult productFileOrder(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String prdId,
            @RequestParam String index
    ) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
//            return responseService.getFailResult();
        }
        productService.saveFileOrders(prdId, index);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/api/product-file")
    public CommonResult deleteProductFile(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String index,
            @RequestParam String prdId,
            @RequestParam(required = false) String flNm
    ) throws IllegalAccessException {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
//            return responseService.getFailResult();
        }
        productService.deleteProductFileById(prdId, index);
        return responseService.getSuccessResult();
    }

    @Transactional
    @PostMapping("/api/product")
    public SingleResult<ProductResponseDto> insertProduct(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String tempKey,
            ProductRequestDto product
    ) {
        return responseService.getSingleResult(productService.saveProduct(product, tempKey, studioResponseDto));
    }


    @Transactional
    @PutMapping("/api/product")
    public SingleResult<ProductResponseDto> updateProduct(
            @LoginStudio StudioResponseDto studioResponseDto,
            ProductRequestDto productRequestDto

    ) {
        return responseService.getSingleResult(productService.updateProduct(productRequestDto, studioResponseDto));
    }

    @Transactional
    @DeleteMapping("/api/product")
    public CommonResult deleteProduct(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String id) {
        productService.deleteProduct(id);
        return responseService.getSuccessResult();
    }

    //Schedule CRUD

    @GetMapping("/api/studio-holiday/week")
    public ListResult<StudioHolidayResponseDto> getStudioHolidayWeek(
            @LoginStudio StudioResponseDto studioResponseDto, @RequestParam Map<String, Object> params) {
        return responseService.getListResult(studioService.selectStudioWeekHolidays(studioResponseDto));
    }


    @GetMapping("/api/studio-holiday/day")
    public ListResult<StudioHolidayResponseDto> getStudioHolidayDay(
            @LoginStudio StudioResponseDto studioResponseDto, @RequestParam Map<String, Object> params) {
        return responseService.getListResult(studioService.selectStudioDayHolidays(studioResponseDto));
    }

    //Reservation CRUD

    @GetMapping("/api/reservation/yyyymm")
    public List<ReservationResposeDto> getReservationYYYYMM(
            @LoginStudio StudioResponseDto studioResponseDto, @RequestParam Map<String, Object> params) {
        return studioService.selectReservationYYYYMM(studioResponseDto);
    }

    @Transactional
    @PostMapping("/api/reservation")
    public CommonResult insertReservation(
            @LoginStudio StudioResponseDto studioResponseDto,
            ReservationRequestDto reservationRequestDto) {
        System.out.println("reservationRequestDto >>>>>>>>>>>>>>" + reservationRequestDto.toString());
        reservationService.saveReservation(reservationRequestDto);
        return responseService.getSuccessResult();
    }

    @Transactional
    @PutMapping("/api/reservation")
    public CommonResult updateReservation(
            @LoginStudio StudioResponseDto studioResponseDto,
            ReservationRequestDto reservationRequestDto) {
        System.out.println("reservationRequestDto >>>>>>>>>>>>>>" + reservationRequestDto.toString());
        reservationService.updateReservation(reservationRequestDto);
        return responseService.getSuccessResult();
    }

    @Transactional
    @DeleteMapping("/api/reservation")
    public CommonResult deleteReservation(
            @LoginStudio StudioResponseDto studioResponseDto,
            ReservationRequestDto reservationRequestDto) {
        System.out.println("reservationRequestDto >>>>>>>>>>>>>>" + reservationRequestDto.toString());
        reservationService.deleteReservation(reservationRequestDto);
        return responseService.getSuccessResult();
    }


    //임시 요청 상품 파일 CRUD
    @PostMapping(value = "/api/request/product/file/temp")
    @Transactional
    public SingleResult<FileResponseDto> insertRequestProductImageFileTemp(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam(value = "tempKey", required = false) String tempKey,
            @RequestParam("detailFiles") MultipartFile file) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }

        return responseService.getSingleResult(userReqeustProductService.saveProductImageFileTemp(tempKey, file));
    }


    @PutMapping(value = "/api/request/product/file/temp/order")
    public CommonResult requestProductFileOrderTemp(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam(value = "tempKey", required = false) String tempKey,
            @RequestParam String index
    ) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
//            return responseService.getFailResult();
        }
        userReqeustProductService.saveFileTempOrders(tempKey, index);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/api/request/product/file/temp")
    public CommonResult deleteRequestProductFileTemp(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String index,
            @RequestParam(value = "tempKey", required = false) String tempKey,
            @RequestParam(required = false) String flNm
    ) throws IllegalAccessException {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
        userReqeustProductService.deleteProductFileTempById(index);
        return responseService.getSuccessResult();
    }


    @Transactional
    @PostMapping("/api/request/product")
    public SingleResult<UserRequestProductResponseDto> insertUserRequestProduct(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String tempKey,
            UserRequestProductRequestDto requestDto
    ) {
        return responseService.getSingleResult(userReqeustProductService.saveUserRequestProductTempToReal(requestDto, tempKey, studioResponseDto));
    }


    @Transactional
    @PutMapping("/api/request/product")
    public SingleResult<UserRequestProductResponseDto> updateUserRequestProduct(
            @LoginStudio StudioResponseDto studioResponseDto,
            UserRequestProductRequestDto requestDto
    ) {
        return responseService.getSingleResult(userReqeustProductService.saveUserRequestProduct(requestDto, studioResponseDto));
    }

    //실제 상품 수정 및 업로드
    @PostMapping(value = "/api/request/product/file")
    @Transactional
    public SingleResult<FileResponseDto> insertRequestProductImageFile(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String id,
            @RequestParam("detailFiles") MultipartFile file) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }

        return responseService.getSingleResult(userReqeustProductService.saveUserRequestProductImageFile(id, file));
    }

    @PutMapping(value = "/api/request/product/file/order")
    public CommonResult requestProductFileOrder(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String id,
            @RequestParam String index
    ) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
        userReqeustProductService.saveFileOrders(id, index);
        return responseService.getSuccessResult();
    }


    @DeleteMapping("/api/request/product/file")
    public CommonResult deleteRequestProductFile(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String index,
            @RequestParam(required = false) String flNm
    ) throws IllegalAccessException {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
        userReqeustProductService.deleteProductFileById(index);
        return responseService.getSuccessResult();
    }

    //    /api/question/repl
    @PostMapping("/api/question/repl")
    public CommonResult insertStudioQuestionRepl(@LoginStudio StudioResponseDto studioResponseDto
            , StudioQuestionReplyRequestDto reply
    ) throws IllegalAccessException {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }
        studioQuestionService.addQuestionReply(reply);
        return responseService.getSuccessResult();
    }

    @PutMapping("/api/userinfo")
    public CommonResult updateUserInfo(@LoginStudio StudioResponseDto sessionVo, StudioInfoRequestDto studioInfoRequestDto) throws IllegalAccessException {

        studioService.updateStudio(sessionVo, studioInfoRequestDto);

        return responseService.getSuccessResult();

    }

    @PutMapping("/api/userinfo-password")
    public CommonResult updateUserInfoPassword(@LoginStudio StudioResponseDto sessionVo, StudioInfoRequestDto studioInfoRequestDto) throws IllegalAccessException {

        studioService.updateStudioPassword(sessionVo, studioInfoRequestDto);

        return responseService.getSuccessResult();

    }

    @PutMapping("/api/userinfo-business")
    public CommonResult updateUserInfoBusiness(@LoginStudio StudioResponseDto sessionVo, StudioInfoRequestDto studioInfoRequestDto, @RequestParam("businessLicFile") MultipartFile file) throws IllegalAccessException, IOException {

        studioService.updateStudioBusiness(sessionVo, studioInfoRequestDto, file);

        return responseService.getSuccessResult();

    }

    @DeleteMapping("/api/studio/withdrawal")
    public CommonResult deleteStudio(@LoginStudio StudioResponseDto sessionVo, StudioInfoRequestDto studioInfoRequestDto) throws IllegalAccessException, IOException {

        studioService.withdrawalStudio(sessionVo);
//        studioService.deleteStudio(sessionVo);

        return responseService.getSuccessResult();

    }

    //아이디 찾기
//    /api/studio/id-check
    @GetMapping("/api/studio/id-check")
    @ResponseBody
    public boolean studioCheckId(StudioRequestDto dto) {

        try {
            StudioResponseDto response = studioService.findById(dto.getStudioId());

        } catch (Exception e) {
            return true;
        }

        return false;
    }


    @GetMapping("/api/location")
    public Map<String, Object> getLocation(@RequestParam Map<String, Object> params) throws IllegalStateException, IOException, ParseException {
        String query = (String) params.get("query");
        String clientId = "vi60zll466";// 애플리케이션 클라이언트 아이디값";
        String clientSecret = "EHv5vyvhMSXHv8zmDsGAfNWHEmckBUKQrvm96THs";// 애플리케이션 클라이언트 시크릿값";
        String addr = URLEncoder.encode(query, "UTF-8");
        String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr; // json
        // String apiURL = "https://openapi.naver.com/v1/map/geocode.xml?query=" + addr;
        // // xml

        ObjectMapper mapper = new ObjectMapper();


        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if (responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else { // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        // convert JSON string to Map
        Map map = mapper.readValue(response.toString(), Map.class);
        return map;
    }


    @RequestMapping(value = {"/join"}, method = RequestMethod.POST)
    public CommonResult insertStudio(
            StudioSaveRequestDto dto,
            @RequestParam("businessLicFile") MultipartFile file) throws Exception {
        System.out.println(dto.toString());

        try {
            StudioRequestDto studioRequestDto = StudioRequestDto.builder().studioId(dto.getStudioId()).build();
            StudioResponseDto response = studioService.findById(dto.getStudioId());

        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals("회원정보가 맞지 않습니다.")) {
                throw new Exception("이미 존재하는 아이디입니다.");
            }

        }
        try {
            studioService.saveStudio(dto, file);
            return responseService.getSuccessResult();
        } catch (Exception e) {
            throw new Exception("이미 존재하는 아이디입니다.");
        }




    }
}
