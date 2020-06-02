package com.zzikza.springboot.web.client;

import com.zzikza.springboot.web.domain.enums.EStudioStatus;
import com.zzikza.springboot.web.domain.enums.ETableStatus;
import com.zzikza.springboot.web.domain.enums.EUserStatus;
import com.zzikza.springboot.web.domain.policy.Policy;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.domain.user.UserRepository;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.result.CommonResult;
import com.zzikza.springboot.web.service.*;
import com.zzikza.springboot.web.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class LoginClientViewController {

    public static final String SESSION_VO = "loginUser";
    public static final String FAIL = "FAILURE";
    public static final String ID_WITHDRAWAL = "탈퇴한 회원입니다.";
    public static final String RETURN_URL = "returl";
    public static final String REDIRECT = "redirect:";
    private final UserService userService;
    private final PolicyService policyService;
    private final ResponseService responseService;

    @PostMapping(value = "/loginProcess")
    @ResponseBody
    public CommonResult loginProcess(UserRequestDto params, HttpSession session) {
        UserResponseDto responseDto = userService.findByUserIdAndPassword(params);
        if (EUserStatus.N.equals(responseDto.getUserStatus())) {
            throw new IllegalArgumentException(ID_WITHDRAWAL);
        }
        session.setAttribute(SESSION_VO, responseDto);
        return responseService.getSingleResult(responseDto);
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session, @RequestParam Map<String, Object> params) {
        session.removeAttribute(SESSION_VO);
        String url = (String) params.get(RETURN_URL);
        if (StringUtil.isNotEmpty(url)) {
            return REDIRECT + url;
        }
        return  REDIRECT + "/";
    }

    @GetMapping(value = "/policy")
    public String policyView(@RequestParam("position") String termCode, Model model) {
        Map<String, Object> params = new HashMap<String, Object>();
        Policy policy = policyService.findByTermCode(termCode);
        model.addAttribute("policy", policy);

        model.addAttribute("title", "찍자사장님 사이트 - 약관");
        return "/policy/policyView";
    }



}