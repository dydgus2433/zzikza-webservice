package com.zzikza.springboot.web.client.interceptor;

import com.zzikza.springboot.web.dto.UserResponseDto;
import com.zzikza.springboot.web.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.zzikza.springboot.web.client.LoginClientViewController.SESSION_VO;

/**
 * 세션 관리 인터셉터
 *
 * @author user
 */
@Slf4j
public class ClientSessionInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // return HandlerInterceptor.super.preHandle(request, response, handler);

        try {
            // 세션 사용자 정보 조회
            HttpSession session = request.getSession();
            UserResponseDto userVO = (UserResponseDto) session.getAttribute(SESSION_VO);

            // 현재 메뉴 조회
            String contextPath = request.getContextPath();
            String uri = request.getRequestURI();
            uri = uri.replace(contextPath, "");

            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("requestUri", request.getRequestURI());

            //로그인 필요없는 페이지 URL
            String[] passPaths = {
                    // 웹자원 return true
                    "/resources/", "/img/", "/css/", "/images/", "/js/", "/webjars/",
                    "/robots.txt", "/favicon.ico", "/sitemap.xml",
                    // 로그인, 로그아웃, 에러 페이지 return true
                    "/login", "/logout", "/loginProcess", "/error",
                    // api 허용
                    "/api", "/policy",
                    // 매체 rest api url return true
                    "/goods/list", "/goods/view", "/goods/repl/list",
                    "/goods/detailView", "/studio/detailView",
                    "/join_sns", "/find_id", "/find_password", "/oauth2", "/join_normal",
                    "/goods/payments/web_hook", "/evt/view", "/exh/list",
                    "/evt/goods/view", "/evt/goods/list",

            };

            for (String webPath : passPaths) {
                if (uri.startsWith(webPath)) {
                    if (webPath.equals("/evt/view")) {
                        String old_url = (String) request.getHeader("Referer");
                        if (old_url != null && !"".equals(old_url)) {
                            URL oldUrl = new URL(old_url);
                            String getHost = oldUrl.getHost();
                            String getProtocol = oldUrl.getProtocol();
                            int getPort = oldUrl.getPort();
                            String companyCd = getProtocol + "://" + getHost;
                            if (getPort > 0) {
                                companyCd += ":" + getPort;
                            } else {
                                if (!"https://www.zzikza.com".equals(companyCd)) {
                                    session.setAttribute("companyCd", companyCd);
                                }
                            }
                        }
                    }
                    return true;
                }
            }

            if (userVO == null || "".equals(StringUtil.nvl(userVO.getId()))) {
                //세션에 저장 및 리다이렉트 해볼까나
                String params = "";
                Enumeration enumeration = request.getParameterNames();
                Map<String, Object> modelMap = new HashMap<>();
                while (enumeration.hasMoreElements()) {
                    String parameterName = (String) enumeration.nextElement();
                    params += "&" + parameterName + "=" + request.getParameter(parameterName);
                    modelMap.put(parameterName, request.getParameter(parameterName));
                }
                log.debug("uri + params {}" + uri + params);
                if (params.length() > 0) {
                    params = params.replaceFirst("&", "?");
                }
                String returl = uri + params;
//				session.setAttribute("returl", uri + params);
//				session.setAttribute("params", modelMap);
                if (!"".equals(returl)) {
                    session.setAttribute("returl", returl);
                    response.sendRedirect(request.getContextPath() + "/login" + "?returl=" + request.getContextPath() + returl);
                }
//                else {
//                    response.sendRedirect(request.getContextPath() + "/login");
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // HandlerInterceptor.super.postHandle(request, response, handler,
        // modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
