package com.zzikza.springboot.web.client.config;

import com.zzikza.springboot.web.client.interceptor.ClientSessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> URL_PATTERNS = Collections.singletonList("/**");
        List<String> SESSION_EXCLUDE_PATTERNS =
                Arrays.asList("/img/**", "/resources/**", "/", "/join", "/loginProcess", "/logout");

        registry.addInterceptor(new ClientSessionInterceptor())
                .addPathPatterns(URL_PATTERNS)
                .excludePathPatterns(SESSION_EXCLUDE_PATTERNS);
    }
}
