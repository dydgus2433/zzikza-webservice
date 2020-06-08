package com.zzikza.springboot.web.client;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(Throwable.class)
    public ModelAndView genericExceptionHandler(Exception ex) {
        String errorMessage = getMessage(ex);
        String errorRootCauseMessage = getRootCauseMessage(ex);
        String errorStacktrace = getStackTrace(ex);
        ModelAndView mv = new ModelAndView("common/error");
        Map<String, Object> model = mv.getModelMap();
        model.put("msg", errorMessage);
        model.put("title", "찍자 - 에러페이지");
        model.put("rootCauseErrorMessage", errorRootCauseMessage);
        model.put("stackTrace", errorStacktrace);
        return mv;
    }


    public String getMessage(final Throwable th) {
        if (th == null) {
            return StringUtils.EMPTY;
        }
        final String msg = th.getMessage();
        return StringUtils.defaultString(msg);
    }

}
