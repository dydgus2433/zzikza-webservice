package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.exhibition.ExhibitionRepository;
import com.zzikza.springboot.web.domain.product.*;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioBoard;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class ProductViewController {

    private final ProductRepository productRepository;
    private final StudioRepository studioRepository;
    private final ProductKeywordRepository productKeywordRepository;
    private final ProductKeywordMapRepository productKeywordMapRepository;
    private final ExhibitionRepository exhibitionRepository;

    @GetMapping(value = "/product/write")
    public String productWritePage(@LoginStudio StudioResponseDto loginStudio, @RequestParam(required = false) String prdId, Model model) {
        //prdId
        if (loginStudio == null) {
            try {
                throw new IllegalAccessException("로그인 해주세요.");
//        에러시 페이지 이동 해야함
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String tempKey = UUID.randomUUID().toString();
        model.addAttribute("tempKey", tempKey);
        model.addAttribute("file-empty", true);

        List<Map<String, Object>> minutes = new ArrayList<>();
        for (int i = 0; i < 60; i += 10) {
            Map<String, Object> minute = new HashMap<>();
            minute.put("minute", i);
            minutes.add(minute);
        }
        model.addAttribute("minutes", minutes);

        //키워드도 줘야함
//        List<ProductKeywordMap> productKeywordMaps = new ArrayList<>();// studio.getStudioKeywordMaps();

        List<ProductKeywordResponseDto> productKeywords = new ArrayList<>();

        List<ProductKeyword> productNoKeywordMaps = productKeywordRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        List<ProductKeywordResponseDto> collect = productNoKeywordMaps.stream()
                .map(ProductKeywordResponseDto::new)
                .collect(Collectors.toList());

        List<ProductKeywordResponseDto> noChoiceKeywords = collect.stream()
                .map((a) -> new ProductKeywordResponseDto(a.getId(), a.getKeywordName()))
                .collect(Collectors.toList());
        model.addAttribute("keywordMaps", productKeywords);
        model.addAttribute("NoKeywordMaps", noChoiceKeywords);
        model.addAttribute("title", "찍자사장님 사이트 - 상품 추가");

        List<Map<String, Object>> commonCodes = new ArrayList<>();
        for (EProductCategory commonCode : EProductCategory.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("commCd", commonCode.getKey());
            map.put("commCdNm", commonCode.getValue());
            //selected 옵션
            commonCodes.add(map);
        }
        model.addAttribute("codes", commonCodes);

        List<Map<String, Object>> showCodes = new ArrayList<>();
        for (EShowStatus commonCode : EShowStatus.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("commCd", commonCode.getKey());
            map.put("commCdNm", commonCode.getValue());
            //selected 옵션
            showCodes.add(map);
        }
        model.addAttribute( "showCodes", showCodes);

        List<Exhibition> exhibitions = exhibitionRepository.findAll(Sort.by(Sort.Order.asc("id")));
        model.addAttribute("exhibitions", exhibitions);
        return "product/productWrite";
    }

    @Transactional(readOnly = true)
    @GetMapping(value = "/product/view")
    public String productViewPage(@LoginStudio StudioResponseDto loginStudio, @RequestParam(required = false) String id, Model model) {
        //prdId
        if (loginStudio == null) {
            try {
                throw new IllegalAccessException("로그인 해주세요.");
//        에러시 페이지 이동 해야함
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String tempKey = UUID.randomUUID().toString();
        model.addAttribute("tempKey", tempKey);
        EProductCategory selected = null;
        EShowStatus selectedShowStatus = null;
        if (id != null) {
            Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

            model.addAttribute("product", new ProductResponseDto(product));


            List<ProductFile> files = product.getProductFiles();


            if (!files.isEmpty()) {
                List<FileResponseDto> productFiles = files.stream().map(FileResponseDto::new).sorted().collect(Collectors.toList());
                model.addAttribute("files", productFiles);
                model.addAttribute("file-size", productFiles.size());
            }
            model.addAttribute("file-empty", files.isEmpty());


            selected = product.getProductCategory();
            selectedShowStatus = product.getShowStatus();


            List<Map<String, Object>> minutes = new ArrayList<>();
            for (int i = 0; i < 60; i += 10) {
                Map<String, Object> minute = new HashMap<>();
                minute.put("minute", i);
                minute.put("is-selected", product.getMinute() == i);
                minutes.add(minute);
            }
            model.addAttribute("minutes", minutes);

            //키워드도 줘야함
            List<ProductKeywordMap> productKeywordMaps = productKeywordMapRepository.findAllByProduct(product);// studio.getStudioKeywordMaps();

            List<ProductKeywordResponseDto> productKeywords = productKeywordMaps.stream()
                    .filter(Objects::nonNull).map((keywordMap) -> new ProductKeywordResponseDto(keywordMap.getProductKeyword()))
                    .collect(Collectors.toList());

            List<ProductKeyword> productNoKeywordMaps = productKeywordRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
            List<ProductKeywordResponseDto> collect = productNoKeywordMaps.stream()
                    .map(ProductKeywordResponseDto::new)
                    .collect(Collectors.toList());

            List<ProductKeywordResponseDto> noChoiceKeywords = collect.stream().filter((a) -> !productKeywords.contains(a))
                    .map((a) -> new ProductKeywordResponseDto(a.getId(), a.getKeywordName()))
                    .collect(Collectors.toList());
            model.addAttribute("keywordMaps", productKeywords);
            model.addAttribute("NoKeywordMaps", noChoiceKeywords);

            List<Exhibition> exhibitions = exhibitionRepository.findAll(Sort.by(Sort.Order.asc("id")));

            List<ExhibitionResponseDto> exhibitionResponseDtos = exhibitions.stream().map(ExhibitionResponseDto::new).collect(Collectors.toList());

            for (ExhibitionResponseDto exhibitionResponseDto : exhibitionResponseDtos) {
                if(product.getExhibition() != null){
                    if (exhibitionResponseDto.getId().equals(product.getExhibition().getId())) {
                        exhibitionResponseDto.setSelected(true);
                    }
                }

            }

            model.addAttribute("exhibitions", exhibitionResponseDtos);


        }

        List<Map<String, Object>> commonCodes = new ArrayList<>();
        for (EProductCategory commonCode : EProductCategory.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("commCd", commonCode.getKey());
            map.put("commCdNm", commonCode.getValue());
            //selected 옵션

            if (commonCode.equals(selected)) {
                map.put("is-selected", true);
            }
            commonCodes.add(map);
        }
        model.addAttribute("codes", commonCodes);

        List<Map<String, Object>> showCodes = new ArrayList<>();
        for (EShowStatus commonCode : EShowStatus.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("commCd", commonCode.getKey());
            map.put("commCdNm", commonCode.getValue());

            if (commonCode.equals(selectedShowStatus)) {
                map.put("is-selected", true);
            }
            showCodes.add(map);
        }
        model.addAttribute("showCodes", showCodes);


        model.addAttribute("title", "찍자사장님 사이트 - 상품 수정");
        return "product/productView";
    }

    @Transactional(readOnly = true)
    @GetMapping(value = "/product/list")
    public String productListViewPage(@LoginStudio StudioResponseDto loginStudio,  Pageable pageable,  Model model) {
//        Page<StudioBoard> findAllByBoardCategoryCodeEquals(EBoardCategory boardCategory, Pageable pageable);

        Studio studio = studioRepository.findById(loginStudio.getId()).orElseThrow(()-> new IllegalArgumentException("스튜디오가 없습니다."));
        Page<Product> paging = productRepository.findAllByStudio(studio, pageable);
        PagingUtil.setPagingParameters(model, paging);

        model.addAttribute("title", "찍자사장님 사이트 - 상품 목록");
        return "product/productList";
    }

}
