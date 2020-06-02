package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import com.zzikza.springboot.web.domain.enums.EUserRequestStatus;
import com.zzikza.springboot.web.domain.request.*;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioBoard;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class UserRequestViewController {

    private static final String REQUEST_PRODUCT_DETAIL_PAGE = "/request/requestProductView";
    private static final String REQUEST_PRODUCT_WRITE_PAGE = "/request/requestProductWrite";
    private final UserRequestRepository requestRepository;
    private final UserRequestProductRepository userRequestProductRepository;
    private final StudioRepository studioRepository;
    private final String REQUEST_LIST = "/request/requestList";
    private final String REQUEST_VIEW = "/request/requestView";
    private final String REQUEST_PRD_LIST = "/request/requestProductList";

    @GetMapping(value = {"/request/list"})
    public String requestListPage(@LoginStudio StudioResponseDto loginStudio, Pageable pageable, @RequestParam Map<String, Object> params, Model model) {


        model.addAttribute("detail", loginStudio);
        Page<UserRequest> paging = requestRepository.findAll(pageable);

        List<UserRequest> requests = (List<UserRequest>) PagingUtil.setPagingParameters(model, paging);
        model.addAttribute("list", requests
                .stream().map(UserRequestResponseDto::new).collect(Collectors.toList()));

        model.addAttribute("title", "찍자사장님 사이트 - 요청하기 목록");
        return REQUEST_LIST;
    }


    @GetMapping(value = {"/request/view"})
    public String requestViewPage(@LoginStudio StudioResponseDto loginStudio, UserRequestRequestDto params, Model model) {

        UserRequest request = requestRepository.findById(params.getId()).orElseThrow(() -> new IllegalArgumentException("요청이 없습니다."));

        List<Map<String, Object>> commonCodes = new ArrayList<>();
        for (EUserRequestStatus commonCode : EUserRequestStatus.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("commCd", commonCode.getKey());
            map.put("commCdNm", commonCode.getValue());

            if (commonCode.equals(request.getRequestStatus())) {
                map.put("selected", true);
                model.addAttribute("commonCode", map);
            }
            //selected 옵션
            commonCodes.add(map);
        }
        model.addAttribute("commonCodes", commonCodes);

        List<UserRequestFile> files = request.getUserRequestFiles();
        model.addAttribute("files", request.getUserRequestFiles());
        model.addAttribute("detail", new UserRequestResponseDto(request));
        model.addAttribute("title", "찍자사장님 사이트 - 요청 상세");
        return REQUEST_VIEW;
    }

    @Transactional(readOnly = true)
    @GetMapping(value = {"/request/product/write"})
    public String requestProductWritePage(UserRequestRequestDto params, Model model) {

        model.addAttribute("reqId", params.getId());
        String tempKey = UUID.randomUUID().toString();
        model.addAttribute("tempKey", tempKey);
        model.addAttribute("title", "찍자사장님 사이트 - 요청상품 등록");
        return REQUEST_PRODUCT_WRITE_PAGE;
    }

    @Transactional(readOnly = true)
    @GetMapping(value = {"/request/product/view"})
    public String requestProductViewPage(UserRequestProductRequestDto params, Model model) {

        String reqId = (String) params.getId();
        model.addAttribute("reqId", reqId);

        UserRequestProduct detail = userRequestProductRepository.findById(params.getId()).orElseThrow(() -> new IllegalArgumentException("요청이 없습니다."));
        model.addAttribute("detail",new UserRequestProductResponseDto(detail)  );
        List<UserRequestProductFile> files = detail.getUserRequestProductFiles();
        if (!files.isEmpty()) {
            List<FileResponseDto> userRequestProductFiles = files.stream().map(FileResponseDto::new).sorted().collect(Collectors.toList());
            model.addAttribute("files", userRequestProductFiles);
            model.addAttribute("file-size", userRequestProductFiles.size());
        }
        model.addAttribute("file-empty", files.isEmpty());
        model.addAttribute("title", "찍자사장님 사이트 - 요청상품 등록");
        return REQUEST_PRODUCT_DETAIL_PAGE;
    }

    @GetMapping(value = {"/request/product/list"})
    public String requestProductListPage(@LoginStudio StudioResponseDto loginStudio,Pageable pageable,  Model model) {

        if (loginStudio == null) {
            try {
                throw new IllegalAccessException("로그인 해주세요.");
//        에러시 페이지 이동 해야함
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //detail
//        assert loginStudio != null;
        Studio studio = studioRepository.findById(loginStudio.getId()).orElseThrow(() -> new IllegalArgumentException("해당 스튜디오가 존재하지 않습니다."));

        Page<UserRequestProduct> paging = userRequestProductRepository.findAllByStudio(studio, pageable);
        List<UserRequestProduct> list = (List<UserRequestProduct>) PagingUtil.setPagingParameters(model, paging);
        model.addAttribute("list",list.stream().map(UserRequestProductResponseDto::new).collect(Collectors.toList()));
//
        model.addAttribute("title", "찍자사장님 사이트 - 등록한 요청상품 목록");
        return REQUEST_PRD_LIST;
    }

}