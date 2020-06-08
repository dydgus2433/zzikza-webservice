package com.zzikza.springboot.web.client;

import com.zzikza.springboot.web.client.annotation.LoginUser;
import com.zzikza.springboot.web.domain.certification.Certification;
import com.zzikza.springboot.web.domain.certification.CertificationRepository;
import com.zzikza.springboot.web.domain.crawling.Company;
import com.zzikza.springboot.web.domain.crawling.CompanyRepository;
import com.zzikza.springboot.web.domain.enums.*;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.product.ProductRepository;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioHoliday;
import com.zzikza.springboot.web.domain.studio.StudioHolidayRepository;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.domain.user.UserRepository;
import com.zzikza.springboot.web.domain.user.UserWishProduct;
import com.zzikza.springboot.web.domain.user.UserWishProductRepository;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.result.CommonResult;
import com.zzikza.springboot.web.result.ListResult;
import com.zzikza.springboot.web.service.*;
import com.zzikza.springboot.web.util.MessageUtil;
import com.zzikza.springboot.web.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zzikza.springboot.web.client.LoginClientViewController.ID_WITHDRAWAL;
import static com.zzikza.springboot.web.client.LoginClientViewController.SESSION_VO;
import static com.zzikza.springboot.web.client.Oauth2ClientApiController.USER_VO;

@RequiredArgsConstructor
@RestController
public class ClientRestApiController {


    public static final String RETURN_URL = "returl";
    final StudioRepository studioRepository;
    final StudioHolidayRepository studioHolidayRepository;
    final ProductRepository productRepository;
    final UserWishProductRepository userWishProductRepository;
    final CompanyRepository companyRepository;
    private final ResponseService responseService;
    private final CertificationRepository certificationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final StudioQuestionService studioQuestionService;
    private final ProductReplyService productReplyService;
    private final StudioService studioService;

    @ExceptionHandler(Exception.class)
    public CommonResult exceptionResult(Exception ex) {
        return responseService.getFailResult(ex);
    }

    @PostMapping(value = "/api/search-categories")
    public ListResult<Map<String, Object>> findSearchCategories() throws Exception {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (EProductCategory commonCode : EProductCategory.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("commCd", commonCode.getKey());
            map.put("commCdNm", commonCode.getValue());
            //selected 옵션
            mapList.add(map);
        }
        return responseService.getListResult(mapList);
    }
    /*@PostMapping(value = "/api/search-keywords")
    public ListResult<Map<String, Object>> findSearchKeywords(
    ) throws Exception {
        List<Map<String, Object>> showCodes = new ArrayList<>();
        for (EShowStatus commonCode : ESearc.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("commCd", commonCode.getKey());
            map.put("commCdNm", commonCode.getValue());
            //selected 옵션
            showCodes.add(map);
        }
        return responseService.getListResult(showCodes);
    }*/

    /**
     * UserId 존재여부 검사
     *
     * @param userId
     * @return
     */
    @GetMapping(value = {"/api/user-id"})
    public boolean selectUserId(String userId) {
        return !userRepository.findByUserId(userId).isPresent();
    }

    /**
     * 인증번호 발송 API 휴대폰 인증 후 본인이름과 같은지 확인 같으면 성공 메시지 전송 (임시비밀번호 변경 후 문자 발송) 다르면 오류
     * 메세지 발송
     *
     * @param params
     * @return
     */
    @Transactional
    @PostMapping(value = "/api/secure-code")
    public CommonResult sendSecureCodeApi(CertificationClientRequestDto params) throws IOException {
        //임시비밀번호랑 id로 비밀번호 update 후 전송
        String secureCode = RandomStringUtils.randomNumeric(6);
        params.setCertificationValue(secureCode);
        //존재하면 임시번호 전송
//        params.setCertificationStatus(ECertificationStatus.S1);

        Certification certification = params.toEntity();
        certificationRepository.save(certification);
        Map<String, Object> successMap = MessageUtil.sendSecureCode(params);
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
    public CommonResult checkSecureCodeApi(CertificationClientRequestDto params) throws IOException, ParseException {
        String checkSecure = params.getCertificationValue();
        String tel = params.getTel();
        String userName = params.getUserName();
        ECertificationStatus certificationStatus = params.getCertificationStatus();
        //존재하면 임시번호 전송
        Optional<Certification> certification = certificationRepository.findByCertificationValueAndUserNameAndTelAndCertificationStatus(checkSecure, userName, tel, certificationStatus);
        if (certification.isPresent()) {
            Certification certification1 = certification.orElseThrow(() -> new IllegalArgumentException("임시번호가 없습니다."));
            String crtfVal = certification1.getCertificationValue();
            if (checkSecure.equals(crtfVal)) {
                Optional<User> user = userService.findByCertification(certification1);
                //스튜디오 전화번호와 인증번호 같은지 비교
                if (certificationStatus.equals(ECertificationStatus.S0)) {
                    //아이디 찾기
                    if (user.isPresent()) {
                        return responseService.getSingleResult(new UserResponseDto(user.get()));
                    } else {
                        throw new IllegalArgumentException("가입되지 않은 정보입니다.");
                    }
                } else if (certificationStatus.equals(ECertificationStatus.S1)) {
                    //비밀번호 찾기
                    //비밀번호 생성하고
                    //비밀번호 update 하고
                    //생성한 비밀번호 메세지 보내야함
                    if (user.isPresent()) {
                        User userOrigin = user.get();
                        //임시비밀번호랑 id로 비밀번호 update 후 전송
                        String tempPassword = RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomNumeric(8);
                        params.setCertificationValue(tempPassword);
                        UserRequestDto entity = new UserRequestDto();
                        entity.setChangePassword(tempPassword);
                        userOrigin.update(entity);
                        userRepository.save(userOrigin);
                        MessageUtil.sendPassword(params);
                        certificationRepository.save(params.toEntity());
                        CommonResult commonResult = responseService.getSuccessResult();
                        commonResult.setMsg("해당 인증이 확인되어 메세지가 발송되었습니다.");
                        return commonResult;
                    } else {
                        throw new IllegalArgumentException("가입되지 않은 정보입니다.");
                    }
                } else if (certificationStatus.equals(ECertificationStatus.C0)) {
                    //SNS 회원가입
                    if (user.isPresent()) {
                        throw new IllegalArgumentException("이미 가입된 정보입니다.");
                    }
                } else if (certificationStatus.equals(ECertificationStatus.C1)) {
                    //일반 가입
                    if (user.isPresent()) {
                        throw new IllegalArgumentException("이미 가입된 정보입니다.");
                    }
                } else if (certificationStatus.equals(ECertificationStatus.S2)) {
                    //일반 가입
                    if (user.isPresent()) {
                        User userOrigin = user.get();
                        //임시비밀번호랑 id로 비밀번호 update 후 전송
                        UserRequestDto entity = new UserRequestDto();
                        userOrigin.update(entity);
                        userRepository.save(userOrigin);
                    } else {
                        throw new IllegalArgumentException("가입되지 않은 정보입니다.");
                    }
                }
            }
        }
        return responseService.getSuccessResult();
    }

    /**
     * 휴대폰 인증 후 본인이름과 같은지 확인 같으면 성공 메시지 전송 (임시비밀번호 변경 후 문자 발송) 다르면 오류 메세지 발송
     * <p>
     * //     * @param params
     *
     * @return
     */
//    @RequestMapping(value = "/api/findPassword", produces = CONTENT_TYPE, method = { RequestMethod.GET,
//            RequestMethod.POST })
//    @ResponseBody
//    public Map<String, Object> apiFindPassword(@RequestParam Map<String, Object> params) {
//        try {
//            List<Map<String, Object>> rows = loginService.selectUserExist(params);
//            // 아이디와 전화번호 같은 지 여부 확인
//            if (rows != null && !rows.isEmpty()) {
//                Map<String, Object> row = rows.get(0);
//                if (row.get("exist").equals("1")) {
//                    String studioId = params.get(USER_ID).toString();
//                    // 임시비밀번호랑 id로 비밀번호 update 후 전송
//                    String tempPassword = RandomStringUtils.randomAlphanumeric(8);
//                    params.put("crtfVal", tempPassword);
//                    // 존재하면 임시비밀번호 전송
//                    String encPw = PasswordUtil.getEncriptPassword(tempPassword, studioId);
//                    params.put("changePw", encPw);
//                    params.put("crtfStat", CRTF_PASSWORD);
//                    executeService("insertSecureCode", params, this.loginService.getClass());
//                    // 비밀번호 발송 후 비밀번호 변경
//                    Map<String, Object> resultMap = executeService("updateTempPassword", params,
//                            this.loginService.getClass());
//
//                    if (resultMap.get("rows").equals(1)) {
//                        // 문자 전송
//                        Map<String, Object> successMap = sendPassword(params);
//                        if (successMap.get("result_code").equals("1")) {
//                            successMap.put(ERROR_CODE, successMap.get("result_code"));
//                            successMap.put(ERROR_MSG, successMap.get("message"));
//                            return successMap;
//                        } else {
//                            successMap.put(ERROR_CODE, successMap.get("result_code"));
//                            successMap.put(ERROR_MSG, "문제가 있을 경우 문의 부탁드립니다. 02-6205-3420");
//                            return successMap;
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            Map<String, Object> errorMap = new HashMap<String, Object>();
//            errorMap.put(ERROR_CODE, FAIL);
//            errorMap.put(ERROR_MSG, "해당 정보와 일치하는 아이디가 없습니다.");
//            return errorMap;
//        }
//        Map<String, Object> errorMap = new HashMap<String, Object>();
//        errorMap.put(ERROR_CODE, FAIL);
//        errorMap.put(ERROR_MSG, "해당 정보와 일치하는 아이디가 없습니다.");
//        return errorMap;
//    }
    @Transactional
    @PostMapping(value = "/api/user")
    public CommonResult insertUser(UserRequestDto userRequestDto, HttpSession session) throws UnsupportedEncodingException {

        userRequestDto.setUserStatus(EUserStatus.Y);
        userRepository.save(userRequestDto.toEntity());
        if (session.getAttribute(RETURN_URL) != null) {
            String returl = URLEncoder.encode((String) session.getAttribute(RETURN_URL), "UTF-8");
            session.removeAttribute(RETURN_URL);
            return responseService.getSingleResult(returl);
        }
        return responseService.getSuccessResult();
    }

    @Transactional
    @PostMapping(value = "/api/sns-user")
    public CommonResult insertSnsUser(SnsUserRequestDto userRequestDto, HttpSession session) throws UnsupportedEncodingException {

        userRequestDto.setUserStatus(EUserStatus.Y);
        userRequestDto.setSnsConnectStatus(ESnsConnectStatus.Y);
        User user = userRequestDto.toEntity();
        userRepository.save(user);
        session.setAttribute(USER_VO, new UserResponseDto(user));
        if (session.getAttribute(RETURN_URL) != null) {
            String returl = URLEncoder.encode((String) session.getAttribute(RETURN_URL), "UTF-8");
            session.removeAttribute(RETURN_URL);
            return responseService.getSingleResult(returl);
        }
        return responseService.getSuccessResult();
    }

    @Transactional
    @PostMapping(value = "/api/question")
    public CommonResult insertQuestion(@LoginUser UserResponseDto userResponseDto, StudioQuestionRequestDto questionRequestDto) {
        studioQuestionService.insertQuestion(questionRequestDto, userResponseDto);
        return responseService.getSuccessResult();
    }

    @Transactional
    @PostMapping(value = "/api/product/repl")
    public CommonResult insertProductRepl(@LoginUser UserResponseDto userResponseDto,
                                          ProductReplyRequestDto productReplyRequestDto) {
        if (userResponseDto == null) {
            throw new IllegalArgumentException("로그인 후 이용해주세요.");
        }

        productReplyService.insertProductReply(productReplyRequestDto, userResponseDto);
        return responseService.getSuccessResult();
    }

    @Transactional
    @GetMapping(value = "/api/studio-files")
    public ListResult<StudioFileResponseDto> selectStudioFile(StudioRequestDto requestDto, Pageable pageable) {
        return responseService.getListResult(studioService.selectStudioFile(requestDto, pageable));
    }

    /**
     * 비밀번호 변경
     *
     * @param request
     * @return
     */
    @PutMapping(value = "/api/user-password")
    public CommonResult updatePassword(@LoginUser UserResponseDto userVo, UserRequestDto request) {
        if ("".equals(StringUtil.nvl(userVo.getId()))) {
            throw new IllegalArgumentException("로그인 해주세요.");
        }
        userService.updatePassword(userVo, request);

        return responseService.getSuccessResult();
    }

    @PutMapping(value = "/api/user")
    public CommonResult updateUser(@LoginUser UserResponseDto userVo, UserRequestDto request) {
        if ("".equals(StringUtil.nvl(userVo.getId()))) {
            throw new IllegalArgumentException("로그인 해주세요.");
        }
        userService.update(userVo, request);

        return responseService.getSuccessResult();
    }

    @PostMapping(value = "/loginProcess")
    public CommonResult loginProcess(UserRequestDto params, HttpSession session) {
        UserResponseDto responseDto = userService.findByUserIdAndPassword(params);
        if (EUserStatus.N.equals(responseDto.getUserStatus())) {
            throw new IllegalArgumentException(ID_WITHDRAWAL);
        }
        session.setAttribute(SESSION_VO, responseDto);
        String returl = (String) session.getAttribute(RETURN_URL);
        if (returl != null && !returl.equals("")) {
            responseDto.setReturl((String) session.getAttribute(RETURN_URL));
        }
        return responseService.getSingleResult(responseDto);
    }

    /**
     * 영업가능일 여부
     *
     * @param params
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws java.text.ParseException
     */
    @GetMapping(value = "/api/possible-day")
    @ResponseBody
    public boolean selectPossibleDay(@RequestParam Map<String, Object> params) throws ParseException {
        /*
        studioId : $("#studioId").val(),
	        			prdHour : $("#prdHour").val(),
         */
        String day = (String) params.get("reservationStartDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nDate = dateFormat.parse(day);
        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        params.put("dtVal", dayNum);
        Studio studio = studioRepository.findById((String) params.get("studioId")).orElseThrow(() -> new IllegalArgumentException("해당 스튜디오가 없습니다."));
        Optional<StudioHoliday> holiday = studioHolidayRepository.findByStudioAndDateCodeAndDateValue(studio, EDateStatus.D, dayNum);
        return holiday.isPresent();
    }


//    /**
//     * 영업가능 시간여부(평일)
//     *
//     * @param params
//     * @return
//     * @throws IOException
//     * @throws ParseException
//     */
//    @RequestMapping(value = "/api/possible-time")
//    @ResponseBody
//    public boolean selectPossibleTime(@LoginUser UserResponseDto user, @RequestParam Map<String, Object> params) {
//         /*
//    studioId : $("#studioId").val(),
//    prdHour : $("#prdHour").val()
//     */
//        if (user == null) {
//            throw new IllegalArgumentException("로그인 해주세요.");
//        }
//        Studio studio = studioRepository.findById((String) params.get("studioId")).orElseThrow(() -> new IllegalArgumentException("해당 스튜디오가 없습니다."));
//        Optional<StudioHoliday> holiday = studioHolidayRepository.findByStudioAndDateCodeAndDateValue(studio, EDateStatus.D, dayNum);
//        return holiday.isPresent();
//    }
//
//    /**
//     * 영업가능 시간여부(주말)
//     *
//     * @param params
//     * @return
//     */
//    @RequestMapping(value = "/api/possible-time-weekend")
//    @ResponseBody
//    public boolean selectPossibleTimeAtWeekend(@LoginUser UserResponseDto user, @RequestParam Map<String, Object> params) {
////        studioId : $("#studioId").val(),
////                reservationStartDate : $("#reservationStartDate").val(),
////                rsrvStrtTime : $("#rsrvStrtTime").val(),
////                prdHour : $("#prdHour").val()
//        if (user == null) {
//            throw new IllegalArgumentException("로그인 해주세요.");
//        }
//        Studio studio = studioRepository.findById((String) params.get("studioId")).orElseThrow(() -> new IllegalArgumentException("해당 스튜디오가 없습니다."));
//        Optional<StudioHoliday> holiday = studioHolidayRepository.findByStudioAndDateCodeAndDateValue(studio, EDateStatus.D, dayNum);
//        return holiday.isPresent();
//    }

    /**
     * 찜하기
     *
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ParseException
     */
//    /api/product-wish -> /api/product-wish
    @PostMapping(value = "/api/product-wish")
    public CommonResult insertProductWish(@LoginUser UserResponseDto user, ProductRequestDto params) {

        if (user != null) {
            if (!"".equals(user.getId())) {
                Optional<Product> oProduct = productRepository.findById(params.getId());
                Optional<User> oUser = userRepository.findById(user.getId());
                if (oProduct.isPresent() && oUser.isPresent()) {
                    Optional<UserWishProduct> optionalUserWishProduct = userWishProductRepository.findByUserAndProduct(oUser.get(), oProduct.get());

                    if (optionalUserWishProduct.isPresent()) {
                        userWishProductRepository.delete(optionalUserWishProduct.get());
                        return responseService.getSingleResult(2);
                    } else {
                        UserWishProduct userWishProduct = UserWishProduct.builder()
                                .user(oUser.get())
                                .product(oProduct.get())
                                .build();
                        userWishProductRepository.save(userWishProduct);
                        return responseService.getSingleResult(1);
                    }

                }
            }
        } else {
            throw new IllegalArgumentException("로그인 해주세요.");
        }
        return responseService.getSuccessResult();
    }

    @GetMapping(value = "/api/crawling")
    public void 크롤링(@RequestParam Map<String, String> params) throws IOException {
        String query = params.get("query");
        //#fix_wrap #wrap #container #content div ol li div a.lnk_tit
        //#fix_wrap #wrap #container #content div ol li div div a.url
        //#fix_wrap #wrap #container #content div ol li div p.ad_dsc_inner
        //https://ad.search.naver.com/search.naver?where=ad&sm=svc_nrs&query=%EB%A8%B8%EC%8B%A0%EB%B9%84%EC%A0%84&referenceId=&pagingIndex=2

        int paginIndex = 1;
        int endIndex = 100;
        if (params.get("pagingIndex") != null && !"".equals(params.get("pagingIndex"))) {
            paginIndex = Integer.parseInt(params.get("pagingIndex"));

        }
        List<Company> companyIterator = new ArrayList<>();
        for (int i = paginIndex; i < endIndex; i++) {
            String urlPath = "https://ad.search.naver.com/search.naver?where=ad&sm=svc_nrs&query=" + query + "&referenceId=&pagingIndex=" + i;
            Document doc1 = Jsoup.connect(urlPath).get();
//            System.out.println(doc1.text());
//            System.out.println(doc1.html());
            //건수 0건이면 break 하자
//#fix_wrap #wrap div.search_result div.result_wrap div.inner span.num_result
            List<String> titles = new ArrayList<>();
            List<String> urls = new ArrayList<>();
            List<String> descs = new ArrayList<>();

            if (isSearchEnd(doc1)) {
                break;
            } else {
                Elements elements = doc1.select("#fix_wrap #wrap #container #content div ol li div");
                for (Element e : elements.select("a.lnk_tit")) {
                    //a.lnk_tit
                    //div a.url
                    //p.ad_dsc_inner
                    if (e.className().equals("lnk_tit")) {
//                        System.out.println("제목 : "+e.text());
                        titles.add(e.text());
                    }
                }

                for (Element e : elements.select("div a.url")) {
                    if (e.className().equals("url")) {
//                        System.out.println("url : "+e.text());
                        urls.add(e.text());
                    }
                }

                for (Element e : elements.select("p.ad_dsc_inner")) {
                    if (e.className().equals("ad_dsc_inner")) {
//                        System.out.println("간단설명 : "+e.text());
                        descs.add(e.text());
                    }
                }
            }


            if (titles.size() == descs.size() && descs.size() == urls.size()) {
                for (int j = 0; j < titles.size(); j++) {
                    System.out.println(titles.get(j) + "/" + urls.get(j) + "/" + descs.get(j));
                    Company company = Company.builder()
                            .title(titles.get(j))
                            .url(urls.get(j))
                            .description(descs.get(j))
                            .query(query)
                            .build();
                    companyIterator.add(company);
                    System.out.println(company.toString());
                    try {
                        companyRepository.save(company);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }

            }
        }
//        System.out.println(companyIterator.toString());
//        companyRepository.saveAll(companyIterator);
    }

    private boolean isSearchEnd(Document doc1) {
        Elements elem = doc1.select("#fix_wrap #wrap div.search_result div.result_wrap div.inner span.num_result");
        for (Element e : elem.select("span.num_result")) {
            if (e.className().equals("num_result")) {
                System.out.println("건수가 0이면 끌거야" + e.text());
                String xx = e.text();
                String[] xxx = xx.split("/");
                if (xxx.length == 2) {
                    String xxxx = xxx[1].replace("건", "");
                    System.out.println(xxxx);
                    xxxx = xxxx.replace(" ", "");
                    System.out.println(xxxx);
                    if (xxxx.equals("0")) {
                        return true;
                    }

                    String[] firstNumbers = xxx[0].split("-");
                    if (firstNumbers.length == 2) {
                        int totalCount = Integer.parseInt(xxxx);
                        int firstNumber = Integer.parseInt(firstNumbers[1].replace(" ", ""));
                        if (firstNumber >= totalCount) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }
}
