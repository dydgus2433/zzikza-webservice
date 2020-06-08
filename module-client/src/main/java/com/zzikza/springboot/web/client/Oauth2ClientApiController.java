package com.zzikza.springboot.web.client;

import com.zzikza.springboot.web.domain.enums.ESnsType;
import com.zzikza.springboot.web.domain.enums.EUserStatus;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.dto.UserResponseDto;
import com.zzikza.springboot.web.service.UserService;
import com.zzikza.springboot.web.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.zzikza.springboot.web.client.ClientRestApiController.RETURN_URL;
import static com.zzikza.springboot.web.client.LoginClientViewController.REDIRECT;
import static com.zzikza.springboot.web.util.URLConnectUtil.getUrlResultMap;

@Slf4j
@RequiredArgsConstructor
@Controller
public class Oauth2ClientApiController {

    public static final String USER_VO = "loginUser";
    public final static String NAVER_CLIENT_ID = "mkYAWOcoWQmNSJARsASc";
    public final static String NAVER_CLIENT_SECRET = "5jkxfYbJSQ";
    public final static String KAKAO_CLIENT_ID = "614db2bfd5b925ea0581a11d8eeef340";
    public final static String KAKAO_CLIENT_SECRET = "Ayvi5dzfFXses7OsX4KKax64Z2B0kzjd";
    public final static String GOOGLE_CLIENT_ID = "1062903569815-q13m573f81sifokm7kq4na5eomm26ohk.apps.googleusercontent.com";
    public final static String GOOGLE_CLIENT_SECRET = "8Mznh28WG-zjoZEF1851bmCB";
    public static final String GOOGLE_SCOPE_URL = "https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/calendar.readonly profile openid"; //"https://www.googleapis.com/auth/youtube";
    public final static String FACEBOOK_APP_ID = "1370960753055710";
    public final static String FACEBOOK_APP_SECRET = "ce5fc176028f890e6d9b12f0ab7b4d6d";
    public static final String FACEBOOK_SCOPE_URL = "email groups_access_member_info openid pages_manage_cta pages_manage_instant_articles pages_messaging pages_messaging_phone_number pages_messaging_subscriptions pages_show_list publish_pages publish_to_groups publish_video read_audience_network_insights read_insights read_page_mailboxes user_age_range user_birthday user_events user_friends user_gender user_hometown user_likes user_link user_location user_photos user_posts user_status user_tagged_places user_videos whatsapp_business_management"; //"https://www.googleapis.com/auth/youtube";
    public final static String FACEBOOK_CLIENT_ID = "305a85a2692323e449d06be11305e2fe";
    final UserService userService;

    /**
     * SNS 회원가입 페이지
     *
     * @param request
     * @param response
     * @param session
     * @param model
     * @param params
     * @return
     */
    @GetMapping(value = "/join_sns")
    public String join_snsPage(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                               ModelMap model, @RequestParam Map<String, Object> params) {

        Map<String, Object> snsInfo = (Map<String, Object>) session.getAttribute("snsInfo");
        model.addAttribute(snsInfo);

        model.addAttribute("title", "찍자 - SNS 회원가입");
        return "/client/join_sns";
    }

    /**
     * OAUTH2 카카오 인증
     *
     * @param model
     * @param request
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @GetMapping(value = {"/oauth2/kakao"})
    public String oauthKakao(Model model, HttpServletRequest request, HttpSession session)
            throws IOException, ParseException {
        // CSRF 방지를 위한 상태 토큰 검증 검증
        // 세션 또는 별도의 저장 공간에 저장된 상태 토큰과 콜백으로 전달받은 state 파라미터의 값이 일치해야 함

        // 콜백 응답에서 state 파라미터의 값을 가져옴
        String state = request.getParameter("state");
        // 세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
        String storedState = (String) session.getAttribute("state");

        String authorizationCode = request.getParameter("code");
        if (!storedState.equals(state)) {
            return REDIRECT + "/login"; // 401 unauthorized
        }

        // AccessToken 받고
        Map<String, Object> responseMap = new HashMap<String, Object>();
        String apiURL = "https://kauth.kakao.com/oauth/token"; // json
        String urlParameters = "client_id=" + KAKAO_CLIENT_ID + "&client_secret=" + KAKAO_CLIENT_SECRET
                + "&grant_type=authorization_code&state=" + storedState + "&code=" + authorizationCode;
        byte[] postDataBytes = urlParameters.getBytes(StandardCharsets.UTF_8);

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setInstanceFollowRedirects(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(con.getOutputStream());
            wr.write(postDataBytes);
        } catch (Exception e) {
            assert wr != null;
            wr.flush();
            wr.close();
        } finally {
            assert wr != null;
            wr.flush();
            wr.close();
        }
        responseMap = getUrlResultMap(con);
        log.debug("token map : {}", responseMap.toString());
        String accessToken = (String) responseMap.get("access_token");
        // 갱신시 필요한 토큰 = 미구현
//		String refreshToken = (String) responseMap.get("refresh_token");

        // 프로필 확인 URL
        String naverProfile = "https://kapi.kakao.com/v2/user/me";
        URL profileUrl = new URL(naverProfile);
        HttpURLConnection profileCon = (HttpURLConnection) profileUrl.openConnection();
        profileCon.setRequestMethod("POST");
        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
        profileCon.setRequestProperty("Authorization", header);
        responseMap = getUrlResultMap(profileCon);
        log.debug("profile : {}", responseMap.toString());
        String kakaoSeq = responseMap.get("id") + "";
        if (StringUtil.isEmpty(kakaoSeq)) {
            return REDIRECT + "/login"; // 401 unauthorized
        }
        responseMap.remove("id");
        responseMap.put("kakaoSeq", kakaoSeq);
        // 20191007 : SNS정보 세션통한 파라미터 전달
        Map<String, Object> kakaoAccount = (Map<String, Object>) responseMap.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String userNm = (String) profile.get("nickname");
        responseMap.remove("id");
        responseMap.put("userId", kakaoSeq);
        responseMap.put("kakaoSeq", kakaoSeq);
        responseMap.put("name", userNm);
        responseMap.put("userNm", userNm);
        responseMap.put("snsType", ESnsType.KAKAO);

        //password 나 userId가 없으면
        Optional<User> optionalUser = userService.findByUserIdAndSnsType(kakaoSeq, ESnsType.KAKAO);
        // User 없을 시 SNS 전용 가입페이지 이동

        // User 없을 시 SNS 전용 가입페이지 이동
        if (optionalUser.isPresent()) {
            User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
            log.debug("user : {}", user.toString());

            if (EUserStatus.Y.equals(user.getUserStatus())) {
                log.debug("user : {}", user.toString());
                session.setAttribute(USER_VO, new UserResponseDto(user));
                session.setAttribute("snsType", ESnsType.KAKAO);
                // 아이디로 NAVER_SEQ select 해서 있으면 로그인하고 없으면 회원가입시키고 로그인허용
            } else if (EUserStatus.N.equals(user.getUserStatus())) {
                model.addAttribute("msg", "탈퇴한 회원입니다.");
                model.addAttribute("small_msg", "회원가입 후 로그인해주세요.");
                model.addAttribute("returl", "/login");
                return "/client/alert";
            }
        } else {
            session.setAttribute("snsInfo", responseMap);
            return REDIRECT + "/join_sns"; // 401 unauthorized
        }

        if (session.getAttribute(RETURN_URL) != null) {
            String returl = (String) session.getAttribute(RETURN_URL);
            session.removeAttribute(RETURN_URL);
            return REDIRECT + returl;
        }
        // 아이디로 NAVER_SEQ select 해서 있으면 로그인하고 없으면 회원가입시키고 로그인허용
        return REDIRECT + "/"; // 200 success
    }

    /**
     * OAUTH2 구글 인증
     *
     * @param model
     * @param request
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @GetMapping(value = {"/oauth2/google"})
    public String oauthGoogle(Model model, HttpServletRequest request,
                              HttpSession session) throws IOException, ParseException {
        String HTTPS = "http://";
        String portNumber = ":" + request.getLocalPort();
        if (request.isSecure()) {
            HTTPS = "https://";
            portNumber = "";
        }
        // CSRF 방지를 위한 상태 토큰 검증 검증
        // 세션 또는 별도의 저장 공간에 저장된 상태 토큰과 콜백으로 전달받은 state 파라미터의 값이 일치해야 함
        // 콜백 응답에서 state 파라미터의 값을 가져옴
        String state = request.getParameter("state");
        // 세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
        String storedState = (String) session.getAttribute("state");

        String authorizationCode = request.getParameter("code");
        if (!storedState.equals(state)) {
            return REDIRECT + "/login"; // 401 unauthorized
        }

        // AccessToken 받고
        Map<String, Object> responseMap = new HashMap<String, Object>();
        String apiURL = "https://www.googleapis.com/oauth2/v4/token"; // json
        URL url = new URL(apiURL);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        String urlParameters = "client_id=" + GOOGLE_CLIENT_ID + "&client_secret=" + GOOGLE_CLIENT_SECRET
                + "&grant_type=authorization_code&state=" + storedState + "&code=" + authorizationCode
                + "&redirect_uri="
                + URLEncoder.encode(HTTPS + request.getServerName() + portNumber + "/oauth2/google", "UTF-8");
        byte[] postDataBytes = urlParameters.getBytes(StandardCharsets.UTF_8);
        con.setDoOutput(true);
        con.setInstanceFollowRedirects(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
//		con.setRequestProperty("Content-length", Integer.toString( postDataLength ));
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(con.getOutputStream());
            wr.write(postDataBytes);
        } catch (Exception e) {
            assert wr != null;
            wr.flush();
            wr.close();
        } finally {
            assert wr != null;
            wr.flush();
            wr.close();
        }
        log.debug("getURL : {}", con.getURL().toString());

        responseMap = getUrlResultMap(con);
        log.debug("token map : {}", responseMap.toString());
        String accessToken = (String) responseMap.get("access_token");
        // 갱신시 필요한 토큰 = 미구현
//		String refreshToken = (String) responseMap.get("refresh_token");

        if (StringUtil.isEmpty(accessToken)) {
            return REDIRECT + "/login"; // 401 unauthorized
        }
        // 프로필 확인 URL
        String naverProfile = "https://www.googleapis.com/oauth2/v1/userinfo";
        URL profileUrl = new URL(naverProfile);
        HttpURLConnection profileCon = (HttpURLConnection) profileUrl.openConnection();
        profileCon.setRequestMethod("GET");
        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
        profileCon.setRequestProperty("Authorization", header);
        responseMap = getUrlResultMap(profileCon);
        log.debug("profile : {}", responseMap.toString());
        String googleSeq = responseMap.get("id") + "";
        responseMap.remove("id");
        responseMap.put("googleSeq", googleSeq);

        if (StringUtil.isEmpty(googleSeq)) {
            return REDIRECT + "/login"; // 401 unauthorized
        }

        responseMap.remove("id");
        responseMap.put("googleSeq", googleSeq);
        // 20191007 : SNS정보 세션통한 파라미터 전달
        String userNm = (String) responseMap.get("name");
        responseMap.remove("id");
        responseMap.put("userId", googleSeq);
        responseMap.put("googleSeq", googleSeq);
        responseMap.put("userNm", userNm);
        responseMap.put("snsType", ESnsType.GOOGLE);

        //password 나 userId가 없으면
        Optional<User> optionalUser = userService.findByUserIdAndSnsType(googleSeq, ESnsType.GOOGLE);
//
        // User 없을 시 SNS 전용 가입페이지 이동
        if (optionalUser.isPresent()) {
            User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
            log.debug("user : {}", user.toString());

            if (EUserStatus.Y.equals(user.getUserStatus())) {
                log.debug("user : {}", user.toString());
                session.setAttribute(USER_VO, new UserResponseDto(user));
                session.setAttribute("snsType", ESnsType.GOOGLE);
                // 아이디로 NAVER_SEQ select 해서 있으면 로그인하고 없으면 회원가입시키고 로그인허용
            } else if (EUserStatus.N.equals(user.getUserStatus())) {
                model.addAttribute("msg", "탈퇴한 회원입니다.");
                model.addAttribute("small_msg", "회원가입 후 로그인해주세요.");
                model.addAttribute("returl", "/login");
                return "/client/alert";
            }
        } else {
            session.setAttribute("snsInfo", responseMap);
            return REDIRECT + "/join_sns"; // 401 unauthorized
        }


        if (session.getAttribute(RETURN_URL) != null) {
            String returl = (String) session.getAttribute(RETURN_URL);
            session.removeAttribute(RETURN_URL);
            return REDIRECT + returl;
        }

        return REDIRECT + "/"; // 200 success
    }

    /**
     * OAUTH2 페이스북 인증
     *
     * @param body
     * @param model
     * @param request
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @GetMapping(value = {"/oauth2/facebook"})
    public String oauthFacebook(@RequestParam Map<String, Object> body, Model model, HttpServletRequest request,
                                HttpSession session) throws IOException, ParseException {
        String HTTPS = "http://";
        String portNumber = ":" + request.getLocalPort();
        log.debug("request.isSecure() : {}", request.isSecure());
        if (request.isSecure()) {
            HTTPS = "https://";
            portNumber = "";
        }
        // CSRF 방지를 위한 상태 토큰 검증 검증
        // 세션 또는 별도의 저장 공간에 저장된 상태 토큰과 콜백으로 전달받은 state 파라미터의 값이 일치해야 함

        // 콜백 응답에서 state 파라미터의 값을 가져옴
        String state = request.getParameter("state");
        // 세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
        String storedState = (String) session.getAttribute("state");

        String authorizationCode = request.getParameter("code");
        if (!storedState.equals(state)) {
            return REDIRECT + "/login"; // 401 unauthorized
        }

//		String GRANT_TYPE = "client_credentials";
        String GRANT_TYPE = "authorization_code";
//		String GRANT_TYPE = "fb_exchange_token";

        // AccessToken 받고
        Map<String, Object> responseMap = new HashMap<String, Object>();
        String apiURL = "https://graph.facebook.com/oauth/access_token"; // json
        String urlParameters = "client_id=" + FACEBOOK_APP_ID + "&client_secret=" + FACEBOOK_APP_SECRET + "&grant_type="
                + GRANT_TYPE + "&state=" + storedState + "&code=" + authorizationCode + "&redirect_uri="
                + URLEncoder.encode(HTTPS + request.getServerName() + portNumber + "/oauth2/facebook", "UTF-8");
        log.debug("urlParameters : {}", urlParameters);
        byte[] postDataBytes = urlParameters.getBytes(StandardCharsets.UTF_8);

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setInstanceFollowRedirects(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(con.getOutputStream());
            wr.write(postDataBytes);
        } catch (Exception e) {
            assert wr != null;
            wr.flush();
            wr.close();
        } finally {
            assert wr != null;
            wr.flush();
            wr.close();
        }
        responseMap = getUrlResultMap(con);
        log.debug("token map : {}", responseMap.toString());
        String accessToken = (String) responseMap.get("access_token");
//		String token_type = (String) responseMap.get("token_type");

        // 프로필 확인 URL
        String naverProfile = "https://graph.facebook.com/v4.0/me?access_token=" + accessToken;
        log.debug("naverProfile : {}", naverProfile);
        URL profileUrl = new URL(naverProfile);
        HttpURLConnection profileCon = (HttpURLConnection) profileUrl.openConnection();
        profileCon.setRequestMethod("GET");
        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
        profileCon.setRequestProperty("Authorization", header);
        responseMap = getUrlResultMap(profileCon);
        log.debug("profile : {}", responseMap.toString());
        String facebookSeq = responseMap.get("id") + "";
        if (StringUtil.isEmpty(facebookSeq)) {
            return REDIRECT + "/login"; // 401 unauthorized
        }
        // 20191007 : SNS정보 세션통한 파라미터 전달
        responseMap.remove("id");
        responseMap.put("facebookSeq", facebookSeq);
        String userNm = (String) responseMap.get("name");
        responseMap.put("userId", facebookSeq);
        responseMap.put("facebookSeq", facebookSeq);
        responseMap.put("userNm", userNm);
        responseMap.put("snsType", ESnsType.FACEBOOK);
        //password 나 userId가 없으면
        Optional<User> optionalUser = userService.findByUserIdAndSnsType(facebookSeq, ESnsType.FACEBOOK);
//
        // User 없을 시 SNS 전용 가입페이지 이동
        if (optionalUser.isPresent()) {
            User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
            log.debug("user : {}", user.toString());

            if (EUserStatus.Y.equals(user.getUserStatus())) {
                log.debug("user : {}", user.toString());
                session.setAttribute(USER_VO, new UserResponseDto(user));
                session.setAttribute("snsType", ESnsType.FACEBOOK);
                // 아이디로 NAVER_SEQ select 해서 있으면 로그인하고 없으면 회원가입시키고 로그인허용
            } else if (EUserStatus.N.equals(user.getUserStatus())) {
                model.addAttribute("msg", "탈퇴한 회원입니다.");
                model.addAttribute("small_msg", "회원가입 후 로그인해주세요.");
                model.addAttribute("returl", "/login");
                return "/client/alert";
            }
        } else {
            session.setAttribute("snsInfo", responseMap);
            return REDIRECT + "/join_sns"; // 401 unauthorized
        }

        if (session.getAttribute(RETURN_URL) != null) {
            String returl = (String) session.getAttribute(RETURN_URL);
            session.removeAttribute(RETURN_URL);
            return REDIRECT + returl;
        }

        return REDIRECT + "/"; // 200 success
    }

    @GetMapping(value = {"/oauth2/naver"})
    public String oauthNaver(Model model, HttpServletRequest request, HttpSession session)
            throws IOException, ParseException {

        // CSRF 방지를 위한 상태 토큰 검증 검증
        // 세션 또는 별도의 저장 공간에 저장된 상태 토큰과 콜백으로 전달받은 state 파라미터의 값이 일치해야 함

        // 콜백 응답에서 state 파라미터의 값을 가져옴
        String state = request.getParameter("state");
        // 세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
        String storedState = (String) session.getAttribute("state");

        String authorizationCode = request.getParameter("code");
        if (!storedState.equals(state)) {
            return REDIRECT + "/login"; // 401 unauthorized
        }

        // AccessToken 받고
        Map<String, Object> map = new HashMap<String, Object>();
        String apiURL = "https://nid.naver.com/oauth2.0/token?is_popup=true&client_id=" + NAVER_CLIENT_ID
                + "&client_secret=" + NAVER_CLIENT_SECRET + "&grant_type=authorization_code&state=" + storedState
                + "&code=" + authorizationCode; // json
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        map = getUrlResultMap(con);
        log.debug("token map : {}", map.toString());
        String accessToken = (String) map.get("access_token");
        // 갱신시 필요한 토큰 = 미구현
//		String refreshToken = (String) map.get("refresh_token");

        // 프로필 확인 URL
        String naverProfile = "https://openapi.naver.com/v1/nid/me";
        URL profileUrl = new URL(naverProfile);
        HttpURLConnection profileCon = (HttpURLConnection) profileUrl.openConnection();
        profileCon.setRequestMethod("GET");
        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
        profileCon.setRequestProperty("Authorization", header);

        map = getUrlResultMap(profileCon);
        Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
        // name
        String naverSeq = (String) responseMap.get("id");

        if (StringUtil.isEmpty(naverSeq)) {
            return REDIRECT + "/login"; // 401 unauthorized
        }
        // 20191007 : SNS정보 세션통한 파라미터 전달
        String userNm = (String) responseMap.get("name");
        responseMap.remove("id");
        responseMap.put("userId", naverSeq);
        responseMap.put("userNm", userNm);
        responseMap.put("snsType", ESnsType.NAVER);

        Optional<User> optionalUser = userService.findByUserIdAndSnsType(naverSeq, ESnsType.NAVER);
//
        // User 없을 시 SNS 전용 가입페이지 이동
        if (isPresent(optionalUser)) {
            User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
            log.debug("user : {}", user.toString());

            if (EUserStatus.Y.equals(user.getUserStatus())) {
                log.debug("user : {}", user.toString());
                session.setAttribute(USER_VO, new UserResponseDto(user));
                session.setAttribute("snsType", ESnsType.NAVER);
                // 아이디로 NAVER_SEQ select 해서 있으면 로그인하고 없으면 회원가입시키고 로그인허용
            } else if (EUserStatus.N.equals(user.getUserStatus())) {
                model.addAttribute("msg", "탈퇴한 회원입니다.");
                model.addAttribute("small_msg", "회원가입 후 로그인해주세요.");
                model.addAttribute("returl", "/login");
                return "/client/alert";
            }
        } else {
            session.setAttribute("snsInfo", responseMap);
            return REDIRECT + "/join_sns"; // 401 unauthorized
        }

        if (session.getAttribute(RETURN_URL) != null) {
            String returl = (String) session.getAttribute(RETURN_URL);
            session.removeAttribute(RETURN_URL);
            return REDIRECT + returl;
        }
        log.debug("responseMap : {}", responseMap.toString());
        return REDIRECT + "/"; // 200 success
    }

    private boolean isPresent(Optional<User> optionalUser) {
        return optionalUser.isPresent();
    }


}
