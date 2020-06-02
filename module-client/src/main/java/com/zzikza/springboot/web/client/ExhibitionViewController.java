package com.zzikza.springboot.web.client;

import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.exhibition.ExhibitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ExhibitionViewController {

    final ExhibitionRepository exhibitionRepository;

    /**
     * 전시회 리스트 페이지
     * @param params
     * @param model
     * @return
     */
    @GetMapping(value = {"/exh/list"})
    public String exhbitionList(
            @RequestParam Map<String, Object> params,
//            ExhibitionRequestDto params,
            Pageable pageable,
            Model model) {
//
        Page<Exhibition> exhibitions = exhibitionRepository.findAll(pageable);
        model.addAttribute("totPage", exhibitions.getTotalPages());
        model.addAttribute("productCategory", params.get("productCategory"));
        model.addAttribute("searchText", params.get("searchText"));


        model.addAttribute("title", "찍자 - 전시회 목록");
        return "/client/exh_list";
    }
    /**
     * 전시회 리스트 ajax
     * @param params
     * @param model
     * @return
     */
    @GetMapping(value = { "/exh/list/ajax" })
    public String getExhibitionsAjax(@RequestParam Map<String, Object> params,Pageable pageable, Model model) {
        String productCategory	= (String) params.get("productCategory");
        String pageNumber	= (String) params.get("pageNumber");

        Page<Exhibition> exhibitions = exhibitionRepository.findAll(pageable);
        params.put("curPage", pageNumber);
        model.addAttribute("list", exhibitions.getContent());
        return "/client/exh_list_ajax";
    }

}
