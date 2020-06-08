package com.zzikza.springboot.web.studio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.domain.certification.Certification;
import com.zzikza.springboot.web.domain.certification.CertificationRepository;
import com.zzikza.springboot.web.domain.enums.ECertificationStatus;
import com.zzikza.springboot.web.domain.enums.EStudioStatus;
import com.zzikza.springboot.web.domain.enums.ETableStatus;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.result.CommonResult;
import com.zzikza.springboot.web.result.ListResult;
import com.zzikza.springboot.web.result.SingleResult;
import com.zzikza.springboot.web.service.*;
import com.zzikza.springboot.web.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.zzikza.springboot.web.studio.LoginViewController.ID_WITHDRAWAL;
import static com.zzikza.springboot.web.studio.LoginViewController.SESSION_VO;

@RequiredArgsConstructor
@RestController
public class StudioRestApiController {


    /**
     * 휴대폰 인증 후 본인이름과 같은지 확인
     * 같으면 성공 메시지 전송 (임시비밀번호 변경 후 문자 발송)
     * 다르면 오류 메세지 발송
     *
     * @param params
     * @return
     */

    private final ResponseService responseService;
    private final StudioService studioService;
    private final ProductService productService;
    private final UserReqeustProductService userReqeustProductService;
    private final FileService editorFileService;
    private final StorageServiceImpl storageService;
    private final ReservationService reservationService;
    private final StudioQuestionService studioQuestionService;
    private final CertificationRepository certificationRepository;

    private final MenuService menuService;

    @ExceptionHandler(Exception.class)
    public CommonResult exceptionResult(Exception ex) {
        return responseService.getFailResult(ex);
    }

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
            @RequestParam String id,
            @RequestParam("detailFiles") MultipartFile file) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
        }

        return responseService.getSingleResult(productService.saveProductImageFile(id, file));
    }

    @PutMapping(value = "/api/product-file/order")
    public CommonResult productFileOrder(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String id,
            @RequestParam String index
    ) throws Exception {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
//            return responseService.getFailResult();
        }
        productService.saveFileOrders(id, index);
        return responseService.getSuccessResult();
    }

    //Schedule CRUD

    @DeleteMapping("/api/product-file")
    public CommonResult deleteProductFile(
            @LoginStudio StudioResponseDto studioResponseDto,
            @RequestParam String index,
            @RequestParam String id,
            @RequestParam(required = false) String flNm
    ) throws IllegalAccessException {
        if (studioResponseDto == null || studioResponseDto.getId() == null) {
            throw new IllegalAccessException("로그인 해주세요.");
//            return responseService.getFailResult();
        }
        productService.deleteProductFileById(id, index);
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

    //Reservation CRUD

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
    public CommonResult updateUserInfo(@LoginStudio StudioResponseDto loginStudio, StudioInfoRequestDto studioInfoRequestDto) throws IllegalAccessException {

        studioService.updateStudio(loginStudio, studioInfoRequestDto);

        return responseService.getSuccessResult();

    }

    @PutMapping("/api/userinfo-password")
    public CommonResult updateUserInfoPassword(@LoginStudio StudioResponseDto loginStudio, StudioInfoRequestDto studioInfoRequestDto) throws IllegalAccessException {

        studioService.updateStudioPassword(loginStudio, studioInfoRequestDto);

        return responseService.getSuccessResult();

    }

    @PutMapping("/api/userinfo-business")
    public CommonResult updateUserInfoBusiness(@LoginStudio StudioResponseDto loginStudio, StudioInfoRequestDto studioInfoRequestDto, @RequestParam("businessLicFile") MultipartFile file) throws IllegalAccessException, IOException {

        studioService.updateStudioBusiness(loginStudio, studioInfoRequestDto, file);

        return responseService.getSuccessResult();

    }

    @DeleteMapping("/api/studio/withdrawal")
    public CommonResult deleteStudio(@LoginStudio StudioResponseDto loginStudio, StudioInfoRequestDto studioInfoRequestDto) throws IllegalAccessException, IOException {

        studioService.withdrawalStudio(loginStudio);
//        studioService.deleteStudio(loginStudio);

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
    public CommonResult getLocation(@RequestParam Map<String, Object> params) throws IllegalStateException, IOException, ParseException {
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
        return responseService.getSingleResult(map);
    }

    @RequestMapping(value = {"/join"}, method = RequestMethod.POST)
    public CommonResult insertStudio(StudioSaveRequestDto dto, @RequestParam("businessLicFile") MultipartFile file) throws Exception {
        studioService.saveStudio(dto, file);
        return responseService.getSuccessResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @PostMapping(value = "/api/secure-code")
    public CommonResult sendSecureCodeApi(CertificationRequestDto params) throws IOException, ParseException {
        //임시비밀번호랑 id로 비밀번호 update 후 전송
        String secureCode = RandomStringUtils.randomNumeric(6);
        params.setCertificationValue(secureCode);
        //존재하면 임시번호 전송
        params.setCertificationStatus(ECertificationStatus.S1);
        Certification certification = params.toEntity();
        certificationRepository.save(certification);
        Map<String, Object> successMap = MessageUtil.sendSecureCode(params);
        //{"result_code":"1","message":"success","msg_id":"58027717","success_cnt":1,"error_cnt":0,"msg_type":"SMS"}
        if (successMap.get("result_code").equals("1")) {
            return responseService.getSuccessResult();
        } else {
            throw new IllegalArgumentException("문제가 있을 경우 문의 부탁드립니다. 02-6205-3420");
        }
    }


    /**
     * 인증번호가 같은지 확인
     * 같으면 성공 메시지 전송 (임시비밀번호 변경 후 문자 발송)
     * 다르면 오류 메세지 발송
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/api/secure-code/check")
    public CommonResult checkSecureCodeApi(CertificationRequestDto params) {
        try {
            String checkSecure = params.getCertificationValue();
            String managerTel = params.getManagerTel();
            String managerName = params.getManagerName();
            //존재하면 임시번호 전송
            Optional<Certification> certification = certificationRepository.findByCertificationValueAndManagerNameAndManagerTelAndCertificationStatus(checkSecure, managerName, managerTel, ECertificationStatus.S1);
            if (certification.isPresent()) {
                Certification certification1 = certification.orElseThrow(() -> new IllegalArgumentException("임시번호가 없습니다."));
                String crtfVal = certification1.getCertificationValue();
                if (checkSecure.equals(crtfVal)) {
                    Studio studio = studioService.findByCertification(certification1);
                    //스튜디오 전화번호와 인증번호 같은지 비교
                    if (certification1.getManagerTel().equals(studio.getManagerTel()) && certification1.getManagerName().equals(studio.getManagerName())) {
                        return responseService.getSingleResult(new StudioResponseDto(studio));
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("인증정보가 일치하지 않습니다.");
        }
        CommonResult commonResult = responseService.getFailResult();
        commonResult.setMsg("인증정보가 일치하지 않습니다.");
        return commonResult;
    }

//

    @Transactional
    @PostMapping(value = "/api/secure-password-code/check")
    public CommonResult apiCheckSecurePassCode(CertificationRequestDto params) {
        try {
            String checkSecure = params.getCertificationValue();
            String studioId = params.getStudioId();
            String managerTel = params.getManagerTel();
            //존재하면 임시번호 전송
            Optional<Certification> certification = certificationRepository.findByCertificationValueAndStudioIdAndManagerTelAndCertificationStatus(checkSecure, studioId, managerTel, ECertificationStatus.S1);
            if (certification.isPresent()) {
                Certification certification1 = certification.orElseThrow(() -> new IllegalArgumentException("임시번호가 없습니다."));
                String crtfVal = certification1.getCertificationValue();
                if (checkSecure.equals(crtfVal)) {
                    Studio studio = studioService.findByCertification(certification1);
                    //스튜디오 전화번호와 인증번호 같은지 비교

                    if (certification1.getManagerTel().equals(studio.getManagerTel()) && certification1.getStudioId().equals(studio.getStudioId())) {
                        //임시 비밀번호 만들어서 전송하고 메세지 보내야함
                        //임시비밀번호랑 id로 비밀번호 update 후 전송
                        String tempPassword = RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomNumeric(8);
                        params.setCertificationValue(tempPassword);
                        StudioInfoRequestDto entity = new StudioInfoRequestDto();
                        entity.setChangePassword(tempPassword);
                        studio.update(entity);
                        MessageUtil.sendPassword(params);

                        certificationRepository.save(params.toEntity());
                        CommonResult commonResult = responseService.getSuccessResult();
                        commonResult.setMsg("해당 인증이 확인되어 메세지가 발송되었습니다.");
                        return commonResult;
                    }

                }
            }
            CommonResult commonResult = responseService.getFailResult();
            commonResult.setMsg("인증정보가 일치하지 않습니다.");
            return commonResult;
        } catch (Exception e) {
            throw new IllegalArgumentException("인증정보가 일치하지 않습니다.");
        }

    }


    @SuppressWarnings("unchecked")
    @PostMapping(value = "/loginProcess")
    public CommonResult loginProcess(@RequestBody StudioRequestDto params, HttpSession session, HttpServletRequest request, ModelMap model) {
        StudioResponseDto responseDto = studioService.findByStudioIdAndPassword(params);
        if (EStudioStatus.N.equals(responseDto.getAccountStatus())) {
            throw new IllegalArgumentException(ID_WITHDRAWAL);
        }
        session.setAttribute(SESSION_VO, responseDto);
        List<MenusListResponseDto> menuList = menuService.findAllByParentMenuIsNullAndUseStatusEquals(ETableStatus.valueOf("Y"));
        session.setAttribute("menuList", menuList);
        return responseService.getSingleResult(responseDto);
    }
}
