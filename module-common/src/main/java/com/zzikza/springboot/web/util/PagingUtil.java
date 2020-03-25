package com.zzikza.springboot.web.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagingUtil {

    public static void setPagingParameters(Model model, Page<?> paging) {
        Pageable pageable = paging.getPageable();
        int firstIndexNotZero = 1;
        int nowPage = pageable.getPageNumber() + firstIndexNotZero;

        model.addAttribute("is-paging", paging.getTotalPages() > 0);
        model.addAttribute("is-fisrt", paging.isFirst());
        List<Map<String, Object>> pageList = new ArrayList<>();
        for (int i = paging.getPageable().first().getPageNumber() + firstIndexNotZero; i < paging.getTotalPages() + firstIndexNotZero; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("idx", i);
            map.put("now-active", i == nowPage);
            pageList.add(map);
        }
        model.addAttribute("pageList", pageList);
        model.addAttribute("now-page", nowPage); //현재 페이지
        model.addAttribute("prev-page", nowPage - 1); //이전 페이지
        model.addAttribute("next-page", nowPage + 1); //다음 페이지
        model.addAttribute("paging-fnc", "fn_paging");
        model.addAttribute("has-next", paging.hasNext());
        model.addAttribute("last-page-no", paging.getTotalPages());

        model.addAttribute("list", paging.getContent());
    }
}
