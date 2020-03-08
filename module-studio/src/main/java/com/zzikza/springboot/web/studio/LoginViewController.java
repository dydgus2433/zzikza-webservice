package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.dto.MenusListResponseDto;
import com.zzikza.springboot.web.dto.StudioRequestDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import com.zzikza.springboot.web.service.MenuService;
import com.zzikza.springboot.web.service.StudioService;
import com.zzikza.springboot.web.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class LoginViewController {

    private final StudioService studioService;
    private final MenuService menuService;

    private static final String LOGIN_PAGE = "login/login";
    private static final String FIND_PASSWORD_PAGE = "login/findPassword";
    private static final String FIND_ID_PAGE = "login/findId";
    private static final String JOIN_PAGE = "login/join";
    private static final String SITE_VIEW_PAGE = "preview/guide";


    @GetMapping(value = {"/", ""})
    public String loginPage(Model model) {
        model.addAttribute("title", "찍자사장님 사이트 - 로그인");
        return LOGIN_PAGE;
    }

    @GetMapping(value = {"/join"})
    public String joinPage(Model model) {

        model.addAttribute("title", "찍자사장님 사이트 - 회원가입");
        return JOIN_PAGE;
    }

    @GetMapping(value = {"/findPassword"})
    public String findPasswordPage(Model model) {

        model.addAttribute("title", "찍자사장님 사이트 - 비밀번호 찾기");
        return FIND_PASSWORD_PAGE;
    }

    @GetMapping(value = {"/findId"})
    public String findIdPage(Model model) {

        model.addAttribute("title", "찍자사장님 사이트 - 아이디 찾기");
        return FIND_ID_PAGE;
    }

    public static final String PASSWORD = "pw";
    public static final String STUDIO_ID = "stdoId";
    public static final String SESSION_VO = "sessionVo";
    public static final String ERROR = "ERROR";
    public static final String FAIL = "FAILURE";
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR_MSG = "rtnMsg";
    public static final String ERROR_CODE = "rtnCode";
    public static final String ID_PW_NOT_COLLECT = "아이디 또는 패스워드가 일치하지 않습니다.";
    public static final String ID_WITHDRAWAL = "탈퇴한 회원입니다.";
    public static final String PROCESS_ERROR = "처리중 오류가 발생하였습니다.";

    public static final String ERROR_503 = "서비스를 사용할 수 없습니다.";
    public static final String ERROR_500 = "서버에 오류가 발생하였습니다.";
    public static final String ERROR_405 = "요청된 메소드가 허용되지 않습니다.";
    public static final String ERROR_404 = "요청하신 페이지는 존재하지 않습니다.";
    public static final String ERROR_EXCEPTION = "예외가 발생하였습니다.";
    public static final String ERROR_403 = "접근이 금지되었습니다.";
    public static final String ERROR_400 = "잘못된 요청입니다.";

    // 스튜디오회원 상태코드
    public static final String AC_STATUS_NORMAL = "00"; // 정상
    public static final String AC_STATUS_WAIT = "01"; // 대기
    public static final String AC_STATUS_SLEEP = "02"; // 휴면
    public static final String AC_STATUS_OUT = "09"; // 탈퇴

    // 스튜디오 회원 권한코드
    public static final String AC_ADMIN = "ADMIN"; // 관리자
    public static final String AC_USER = "USER"; // 일반

    public final static String CONTENT_TYPE = "application/json;charset=UTF-8";

	@SuppressWarnings("unchecked")
//    @RequestMapping(value = "/loginProcess", method = { RequestMethod.POST }, produces = CONTENT_TYPE)
    @PostMapping(value = "/loginProcess")
	@ResponseBody
	public StudioResponseDto loginProcess(@RequestBody StudioRequestDto params,HttpSession session, ModelMap model) {
		StudioResponseDto responseDto = studioService.findByStudioIdAndPassword(params);
		if(AC_STATUS_OUT.equals(StringUtil.nvl(responseDto.getAccountStatus()))) {
			throw  new IllegalArgumentException(ID_WITHDRAWAL);
		}
		session.setAttribute(SESSION_VO, responseDto);
//		session.setMaxInactiveInterval(SESSION_MAX_INTERVAL_TIME);
		List<MenusListResponseDto> menuList = menuService.findAllDepth1();

		session.setAttribute("menuList", menuList);
//		session.setAttribute("menuList2", newMenuList2);
		return responseDto;
	}
//
//	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
//	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model, @RequestParam Map<String, Object> params) {
//		session.removeAttribute(SESSION_VO);
//		String url = (String) params.get(RETURN_URL);
//		if (StringUtil.isNotEmpty(url)) {
//			return REDIRECT + url;
//		}
//		return LOGIN_PAGE;
//	}
//
//
}