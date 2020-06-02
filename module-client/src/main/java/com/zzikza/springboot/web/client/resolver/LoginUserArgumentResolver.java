package com.zzikza.springboot.web.client.resolver;

import com.zzikza.springboot.web.client.annotation.LoginUser;
import com.zzikza.springboot.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    //컨트롤러 메서드의 특정 파라미터를 지원하는지 판단.
    // @LoginStudio 이 붙어 있고
    // 파라미터 클래스 타입이 UserResponseDto 인 경우
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = UserResponseDto.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    // 파라미터에 전달할 객체 생성 (여기서는 세션에서 객체 가져옴)
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("loginUser");
    }
}
