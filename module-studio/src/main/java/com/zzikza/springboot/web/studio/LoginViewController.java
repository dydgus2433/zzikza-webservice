package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.domain.enums.ETableStatus;
import com.zzikza.springboot.web.domain.enums.EUserStatus;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class LoginViewController {

    private final StudioService studioService;
    private final MenuService menuService;

    private static final String LOGIN_PAGE = "login/login";
    private static final String FIND_PASSWORD_PAGE = "login/findPassword";
    private static final String FIND_ID_PAGE = "login/findId";
    private static final String JOIN_PAGE = "login/join";

    public static final String SESSION_VO = "sessionVo";
    public static final String FAIL = "FAILURE";
    public static final String ID_WITHDRAWAL = "탈퇴한 회원입니다.";
    public static final String AC_STATUS_OUT = "09"; // 탈퇴
    // 스튜디오 회원 권한코드
    public static final String RETURN_URL = "returl";
    public static final String REDIRECT = "redirect:";


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

	@SuppressWarnings("unchecked")
//    @RequestMapping(value = "/loginProcess", method = { RequestMethod.POST }, produces = CONTENT_TYPE)
    @PostMapping(value = "/loginProcess")
	@ResponseBody
	public StudioResponseDto loginProcess(@RequestBody StudioRequestDto params, HttpSession session, HttpServletRequest request, ModelMap model) {
		StudioResponseDto responseDto = studioService.findByStudioIdAndPassword(params);
		if(AC_STATUS_OUT.equals(StringUtil.nvl(responseDto.getAccountStatus()))) {
			throw  new IllegalArgumentException(ID_WITHDRAWAL);
		}
		session.setAttribute(SESSION_VO, responseDto);
		List<MenusListResponseDto> menuList = menuService.findAllByParentMenuIsNullAndUseStatusEquals(ETableStatus.valueOf("Y"));
		session.setAttribute("menuList", menuList);
		return responseDto;
	}
//

	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model, @RequestParam Map<String, Object> params) {
        session.removeAttribute(SESSION_VO);
		String url = (String) params.get(RETURN_URL);
		if (StringUtil.isNotEmpty(url)) {
			return REDIRECT + url;
		}
		return LOGIN_PAGE;
	}
//
//
}