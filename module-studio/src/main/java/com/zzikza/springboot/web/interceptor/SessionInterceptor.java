package com.zzikza.springboot.web.interceptor;

import com.zzikza.springboot.web.dto.StudioResponseDto;
import com.zzikza.springboot.web.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// return HandlerInterceptor.super.preHandle(request, response, handler);
		log.debug("handler : {}", handler);
		log.debug(" Request URI \t: " + request.getRequestURI());
		
		try {
			// 세션 사용자 정보 조회
			HttpSession session = request.getSession();
			StudioResponseDto sessionVo = (StudioResponseDto) session.getAttribute("sessionVo");
			
			// 현재 메뉴 조회
			String contextPath = request.getContextPath();
			String uri = request.getRequestURI();
			uri = uri.replace(contextPath, "");
			log.debug(" Request URI \t: " + uri);
			log.debug(" contextPath \t: " + contextPath);

//			List<MenusListResponseDto> menuList = (List<MenusListResponseDto>) session.getAttribute("menuList");
//			for (MenusListResponseDto menuDto : menuList){
//				System.out.println("menuDto.getMenuUrl()" + menuDto.getMenuUrl());
//				System.out.println("request.getRequestURI()" + request.getRequestURI());
//			}
			request.setAttribute("contextPath", contextPath);
			request.setAttribute("requestUri", uri);
			
			// 웹자원 return true
			if (uri.startsWith("/resources/") || uri.startsWith("/img/") || uri.startsWith("/css/") || uri.startsWith("/images/") || uri.startsWith("/js/") || uri.startsWith("/webjars/")) {
				return true;
			}

			// 로그인, 로그아웃, 에러 페이지 return true
			if (uri.startsWith("/login") || uri.startsWith("/logout") || uri.startsWith("/loginProcess") || uri.startsWith("/error")) {
				return true;
			}
			
			//아이디, 비밀번호 찾기
			if (uri.startsWith("/findPassword") || uri.startsWith("/findId")) {
				return true;
			}
			// api 허용
			if (uri.startsWith("/api") || uri.startsWith("/policy")) {
				return true;
			}
			
			//미리보기
			if (uri.startsWith("/preview")) {
				return true;
			}
			
			
			// 매체 rest api url return true
			if (uri.startsWith("/media")) {
				return true;
			}

			if (uri.startsWith("/robots.txt") || uri.startsWith("/favicon.ico") || uri.startsWith("/sitemap.xml")) {
				return true;
			}
			
			if (uri.startsWith("/question/popup") || uri.startsWith("/schedule/popup")) {
				return true;
			}
			
			if (sessionVo == null || "".equals(StringUtil.nvl(sessionVo.getId()))) {
				String returnUrl = request.getScheme()+"://"+request.getServerName();
				if(request.getLocalPort() != 80){
					returnUrl += ":" + request.getLocalPort();
				}
				response.sendRedirect(request.getContextPath() + "/logout?returl="+returnUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.debug("postHandle : {}", handler);
		// HandlerInterceptor.super.postHandle(request, response, handler,
		// modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.debug("afterCompletion : {}", handler);
		// HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
