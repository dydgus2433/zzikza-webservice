package com.zzikza.springboot.web.client;

import com.zzikza.springboot.web.client.annotation.LoginUser;
import com.zzikza.springboot.web.domain.certification.Certification;
import com.zzikza.springboot.web.domain.certification.CertificationRepository;
import com.zzikza.springboot.web.domain.enums.ECertificationStatus;
import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.ESnsConnectStatus;
import com.zzikza.springboot.web.domain.enums.EUserStatus;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.domain.user.UserRepository;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.result.CommonResult;
import com.zzikza.springboot.web.result.ListResult;
import com.zzikza.springboot.web.service.*;
import com.zzikza.springboot.web.util.MessageUtil;
import com.zzikza.springboot.web.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import static com.zzikza.springboot.web.client.Oauth2ClientApiController.USER_VO;

@RequiredArgsConstructor
@RestController
public class ClinetRestApiController {


    public static final String RETURN_URL = "returl";
    //비밀번호
    public static final String CRTF_PASSWORD = "S0";
    //미인증
    public static final String CRTF_NOT_ACCEPT = "S1";
    //인증
    public static final String CRTF_ACCEPT = "S2";
    //거절
    public static final String CRTF_REJECT = "S3";

    private final ResponseService responseService;
    private final CertificationRepository certificationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final StudioQuestionService studioQuestionService;
    private final ProductReplyService productReplyService;
    private final StudioService studioService;

    @PostMapping(value = "/api/search-categories")
    public ListResult<Map<String, Object>> findSearchCategories(
    ) throws Exception {

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
    public CommonResult sendSecureCodeApi(
            CertificationClientRequestDto params,
            HttpSession session
    ) {
        //임시비밀번호랑 id로 비밀번호 update 후 전송
        String secureCode = RandomStringUtils.randomNumeric(6);
        params.setCertificationValue(secureCode);
        //존재하면 임시번호 전송
//        params.setCertificationStatus(ECertificationStatus.S1);

        try {
            Certification certification = params.toEntity();
            certificationRepository.save(certification);
            Map<String, Object> successMap = MessageUtil.sendSecureCode(params);
            if (successMap.get("result_code").equals("1")) {
                return responseService.getSuccessResult();
            } else {
                CommonResult commonResult = responseService.getFailResult();
                commonResult.setMsg("문제가 있을 경우 문의 부탁드립니다. 02-6205-3420");
                return commonResult;
            }
        } catch (Exception e) {
            //문자 전송
            CommonResult commonResult = responseService.getFailResult();
            commonResult.setMsg("문제가 있을 경우 문의 부탁드립니다. 02-6205-3420");
            return commonResult;
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
    public CommonResult checkSecureCodeApi(
//            @RequestParam Map<String, Object>  params

            CertificationClientRequestDto params
    ) {
        try {
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
                            return responseService.getSuccessResult();
                        }
                    } else if (certificationStatus.equals(ECertificationStatus.S1)) {
                        //비밀번호 찾기
                        //비밀번호 생성하고
                        //비밀번호 update 하고
                        //생성한 비밀번호 메세지 보내야함
                        String userId = params.getUserId();
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
                        }
                    } else if (certificationStatus.equals(ECertificationStatus.C0)) {
                        //SNS 회원가입
                        if (user.isPresent()) {
                            throw new IllegalArgumentException("이미 가입된 정보입니다.");
                        } else {
                            return responseService.getSuccessResult();
                        }
                    } else if (certificationStatus.equals(ECertificationStatus.C1)) {
                        //일반 가입
                        if (user.isPresent()) {
                            throw new IllegalArgumentException("이미 가입된 정보입니다.");
                        } else {
                            return responseService.getSuccessResult();
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
                            return responseService.getSuccessResult();
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            CommonResult commonResult = responseService.getFailResult();
            commonResult.setMsg(e.getMessage());
            return commonResult;
        } catch (Exception e) {
            CommonResult commonResult = responseService.getFailResult();
            commonResult.setMsg("인증정보가 일치하지 않습니다.");
            return commonResult;
        }
        CommonResult commonResult = responseService.getFailResult();
        commonResult.setMsg("인증정보가 일치하지 않습니다.");
        return commonResult;
    }

//


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

        try {
            userRequestDto.setUserStatus(EUserStatus.Y);
            userRepository.save(userRequestDto.toEntity());
        } catch (Exception e) {
            return responseService.getFailResult();
        }
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

        try {
            userRequestDto.setUserStatus(EUserStatus.Y);
            userRequestDto.setSnsConnectStatus(ESnsConnectStatus.Y);
            User user = userRequestDto.toEntity();
            userRepository.save(user);
            session.setAttribute(USER_VO, new UserResponseDto(user));
        } catch (Exception e) {
            return responseService.getFailResult();
        }
        if (session.getAttribute(RETURN_URL) != null) {
            String returl = URLEncoder.encode((String) session.getAttribute(RETURN_URL), "UTF-8");
            session.removeAttribute(RETURN_URL);
            return responseService.getSingleResult(returl);
        }
        return responseService.getSuccessResult();
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

    @Transactional
    @PostMapping(value = "/api/question")
    public CommonResult insertQuestion(@LoginUser UserResponseDto userResponseDto, StudioQuestionRequestDto questionRequestDto) {
        try {
            studioQuestionService.insertQuestion(questionRequestDto, userResponseDto);
        } catch (Exception e) {
            return responseService.getFailResult();
        }
        return responseService.getSuccessResult();
    }

    @Transactional
    @PostMapping(value = "/api/product/repl")
    public CommonResult insertProductRepl(@LoginUser UserResponseDto userResponseDto,
                                          ProductReplyRequestDto productReplyRequestDto
//                                          @RequestParam Map<String, Object> params

    ) {
        if (userResponseDto == null) {
            throw new IllegalArgumentException("로그인 후 이용해주세요.");
        }
        try {

            productReplyService.insertProductReply(productReplyRequestDto, userResponseDto);
        } catch (Exception e) {
            return responseService.getFailResult();
        }
        return responseService.getSuccessResult();
    }


    @Transactional
    @GetMapping(value = "/api/studio-files")
//    @ResponseBody
    public ListResult<StudioFileResponseDto> selectStudioFile(StudioRequestDto requestDto, Pageable pageable) {
        return responseService.getListResult(studioService.selectStudioFile(requestDto, pageable));
    }


    /**
     * 비밀번호 변경
     *
     * @param request
     * @return
     */
    @PutMapping(value = "/api/user-password") //, produces = CONTENT_TYPE)
    public CommonResult updatePassword(@LoginUser UserResponseDto userVo, UserRequestDto request) {
        String user_seq = userVo.getId();
        String changePw = request.getChangePassword();
        if ("".equals(StringUtil.nvl(userVo.getId()))) {

            return responseService.getFailResult();
        } else {

            userService.updatePassword(userVo, request);
        }
        return responseService.getSuccessResult();
    }

    @PutMapping(value = "/api/user") //, produces = CONTENT_TYPE)
    public CommonResult updateUser(@LoginUser UserResponseDto userVo, UserRequestDto request) {
        if ("".equals(StringUtil.nvl(userVo.getId()))) {

            return responseService.getFailResult();
        } else {

            userService.update(userVo, request);
        }
        return responseService.getSuccessResult();
    }


}
