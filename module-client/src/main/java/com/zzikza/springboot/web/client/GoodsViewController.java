package com.zzikza.springboot.web.client;

import com.zzikza.springboot.web.client.annotation.LoginUser;
import com.zzikza.springboot.web.domain.enums.EProductStatus;
import com.zzikza.springboot.web.domain.enums.EQuestionCategory;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.exhibition.ExhibitionRepository;
import com.zzikza.springboot.web.domain.product.*;
import com.zzikza.springboot.web.domain.request.UserRequest;
import com.zzikza.springboot.web.domain.request.UserRequestRepository;
import com.zzikza.springboot.web.domain.reservation.Reservation;
import com.zzikza.springboot.web.domain.reservation.ReservationRepository;
import com.zzikza.springboot.web.domain.sale.Sale;
import com.zzikza.springboot.web.domain.sale.SaleRepository;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioFileRepository;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.domain.user.UserWishProduct;
import com.zzikza.springboot.web.domain.user.UserWishProductRepository;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.service.ProductReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@RequiredArgsConstructor
@Controller
public class GoodsViewController {

    final ProductRepository productRepository;
    final ExhibitionRepository exhibitionRepository;
    final UserWishProductRepository userWishProductRepository;
    final StudioFileRepository studioFileRepository;
    final SaleRepository saleRepository;
    final ReservationRepository reservationRepository;
    final UserRequestRepository userRequestRepository;
    private final ProductReplyService productReplyService;
    private final StudioRepository studioRepository;
    private final ProductFileRepository productFileRepository;

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    /**
     * 상품 목록 페이지 ( 카테고리 : productCategory /검색단어 : searchText/ )
     *
     * @param model
     * @return
     */
    @GetMapping(value = {"/goods/list"})
    public String goodsSearchList(
//            @RequestParam Map<String, Object> params,
            ProductRequestDto params,
            Pageable pageable,
            Model model) {
//


        Page<Product> paging = productRepository.findAll(pageable);
        model.addAttribute("totCount", paging.getTotalElements());
        model.addAttribute("totPage", paging.getTotalPages());

        String exhId = params.getExhId();

        if (!Objects.isNull(params.getProductCategory())) {
            String productCategory = (String) params.getProductCategory().getKey();
            if (!"".equals(productCategory)) {
                model.addAttribute("productCategory", productCategory);
            }
        }


        if (!Objects.isNull(params.getSearchText())) {
            model.addAttribute("searchText", params.getSearchText());
        }
        if (!Objects.isNull(params.getExhId())) {
            model.addAttribute("exhId", params.getExhId());
        }
        model.addAttribute("title", "찍자 - 상품목록");
        return "/client/goods_list";
    }

    /**
     * 상품 목록 페이지 ( 카테고리 : productCategory /검색단어 : searchText/ ) ajax
     *
     * @param params
     * @param model
     * @return
     */
    @GetMapping(value = {"/goods/list/ajax"})
    public String getProductsAjax(
//            @RequestParam Map<String, Object> params,
            ProductRequestDto params, Pageable pageable, Model model) {

//        Sort sort ;
//        if(params.getOrder().equals("desc")){
//            sort = Sort.by(Sort.Order.desc(params.getColumn()));
//        }else{
//            sort = Sort.by(Sort.Order.asc(params.getColumn()));
//        }
        Page<Product> paging;
        if (!Objects.isNull(params.getSearchText())) {
            if (!Objects.isNull(params.getExhId())) {
                Exhibition exhibition = exhibitionRepository.findById(params.getId()).orElseThrow(() -> new IllegalArgumentException("전시회가 없습니다."));
                paging = productRepository.findBySearchTextContainsAndExhibition(params.getSearchText(), exhibition, pageable);
            } else {
                paging = productRepository.findBySearchTextContains(params.getSearchText(), pageable);
            }

//            paging = productRepository.findBySearchTextContains(params.getSearchText(), pageable, sort);

        } else {
//            paging = productRepository.findAll(pageable, sort);
            paging = productRepository.findAll(pageable);
        }

        List<Product> products = paging.getContent();
        List<ProductResponseDto> productDtos = new ArrayList<>();
        if (params.getLatitude() != null && params.getLongitude() != null) {
            double latitude = params.getLatitude();
            double longitude = params.getLongitude();
            for (Product map : products) {
                ProductResponseDto productResponseDto = new ProductResponseDto(map);
                if (map.getStudio() != null) {
                    if (map.getStudio().getLttd() != null && map.getStudio().getLgtd() != null) {
                        Double lttd = map.getStudio().getLttd();
                        Double lgtd = map.getStudio().getLgtd();

                        Double distance = distance(latitude, longitude, lttd, lgtd, "kilometer");
                        productResponseDto.setDistance(Math.round(distance * 100) / 100.0);
                    }
                }
                if (!map.getProductFiles().isEmpty()) {
                    productResponseDto.setProductFile(map.getProductFiles().get(0));
                }
                productDtos.add(productResponseDto);
            }
        }
        model.addAttribute("list", productDtos);
        model.addAttribute("nowPage", paging.getPageable().getPageNumber());
//        model.addAttribute("pagePerOnce", );
//
        model.addAttribute("searchText", params.getSearchText());
        model.addAttribute("column", params.getColumn());
        model.addAttribute("order", params.getOrder());

        return "client/goods_list_ajax";
    }

    /**
     * 상품 상세 페이지
     *
     * @param prdId
     * @param model
     * @return
     */
    @Transactional(readOnly = true)
    @GetMapping(value = {"/goods/view/{prdId}"})
    public String goodsView(@PathVariable("prdId") String prdId,
                            Model model) {
        String webTitle = "상품 상세";
        Product product = productRepository.findById(prdId).orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));

        if (EShowStatus.N.equals(product.getShowStatus())) {
            model.addAttribute("msg", "마감된 상품입니다.");
            model.addAttribute("small_msg", "다른 상품을 선택해 보세요.");
            model.addAttribute("returl", "/");
            return "/client/alert";
        }

        // 스튜디오 삭제여부
        if (EProductStatus.DELETE.equals(product.getProductStatus())) {
            model.addAttribute("msg", "마감된 상품입니다.");
            model.addAttribute("small_msg", "다른 상품을 선택해 보세요.");
            model.addAttribute("returl", "/");
            return "/client/alert";
        }
        webTitle = product.getTitle();
        ProductResponseDto productResponseDto = new ProductResponseDto(product);
        model.addAttribute("product", productResponseDto);

        if (!Objects.isNull(product.getExhibition())) {
            Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(product.getExhibition().getId());
            if (optionalExhibition.isPresent()) {
                Exhibition exhibition = optionalExhibition.get();
                List<Sale> sales = saleRepository.findAllByExhibition(exhibition);
                model.addAttribute("sales", sales);
            }
        }

        // 상품 카테고리 리스트
        model.addAttribute("categories", product.getProductCategory());
        model.addAttribute("reservationFee", product.getPrice() / 10);
        List<ProductFile> productFiles = product.getProductFiles();

        model.addAttribute("productFileList", productFileRepository.findAllByProduct(product));
        model.addAttribute("productFileSize", productFiles.size());


        if (!productFiles.isEmpty()) {
            model.addAttribute("firstFileName", productFiles.get(0).getFile().getFileMidsizePath());
        }

        Studio studio = product.getStudio();
        model.addAttribute("studio", productResponseDto.getStudio());

        model.addAttribute("photoList", studioFileRepository.findAllByStudio(studio));
        model.addAttribute("photoListSize", studioFileRepository.findAllByStudio(studio).size());
        List<ProductReply> productReplies = product.getProductReplies();
        double sumGrade = 0;
        int count = 0;
        for (ProductReply productReply : productReplies) {
            sumGrade += productReply.getGrade();
            count++;
        }
        if (count == 0) {
            model.addAttribute("grade", 0);
        } else {
            model.addAttribute("grade", sumGrade / count);
        }
        model.addAttribute("replList", product.getProductReplies());

        //찜하기랑 리스트의 파라미터들 가져오면 될듯
//        model.addAttribute("wish", userWishProductRepository.findByUserAndProduct(loginUser, product).isPresent());
        model.addAttribute("prdId", prdId);
//        // 스튜디오 사진 리스트
//        List<Map<String, Object>> studioPhotoList = studioService.selectStudioFiles(param);
//
//        List<Map<String, Object>> replList = getList("selectProductRepls", param);
//        int totCount = 0;
//        int totPage = 1;
//        int pagePerOnce = 10;
//        Map<String, Object> totalMap = getOne("selectProductRepls" + "Total", param);
//        String total = StringUtil.nvl(totalMap.get("total"), "1");
//        totCount = Integer.parseInt(total);
//        totPage = ((totCount - 1) / pagePerOnce) + 1;
//        model.addAttribute("totCount", totCount);
//        model.addAttribute("totPage", totPage);
//
//        setUserSeq(request, param);
//        Map<String, Object> wish = getOne("selectProductWish", param);
//        model.addAttribute("wish", wish);
//        model.addAttribute("prdId", prdId);
//        model.addAttribute("list", list);
//        model.addAttribute("photoList", studioPhotoList);
//
//        model.addAttribute("replList", replList);
//
        model.addAttribute("title", "찍자 - " + webTitle);
        return "/client/view";
    }

    protected double distance(Double lat1, Double lon1, Double lat2, Double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit.equals("kilometer")) {
            dist = dist * 1.609344;
        } else if (unit.equals("meter")) {
            dist = dist * 1609.344;
        }

        return (dist);
    }

    @GetMapping(value = {"/goods/detailView/{prdId}/{index}"})
    public String goodsDetailFiles(@PathVariable("prdId") String prdId, @PathVariable("index") String index, Model model) {
        model.addAttribute("prdId", prdId);
        model.addAttribute("index", index);
//        // 스튜디오 사진 리스트
        Product product = productRepository.findById(prdId).orElseThrow(() -> new IllegalArgumentException("상품 정보가 없습니다."));
        model.addAttribute("list", productFileRepository.findAllByProduct(product));
//        model.addAttribute("index", index);
        return "client/view_modal";
    }

    @GetMapping(value = {"/studio/detailView/{studioId}/{index}"})
    public String studioDetailFiles(@PathVariable("studioId") String studioId, @PathVariable("index") String index, Model model) {
        model.addAttribute("studioId", studioId);
        model.addAttribute("index", index);
//        // 스튜디오 사진 리스트
        Studio studio = studioRepository.findById(studioId).orElseThrow(() -> new IllegalArgumentException("스튜디오 정보가 없습니다."));
        model.addAttribute("list", studioFileRepository.findAllByStudio(studio));
//        model.addAttribute("index", index);
        return "client/view_modal";
    }

    /**
     * 주문하기(예약하기)
     *
     * @param prdId
     * @param params
     * @param model
     * @return
     */
    @GetMapping(value = {"/goods/order/{prdId}"})
    public String order(@PathVariable("prdId") String prdId, @RequestParam Map<String, Object> params, Model model) {
        // {saleCode=S, exhId=EXH0000000001, reservPrice=6500, productPrice=65000}
        params.put("prdId", prdId);
        model.addAttribute("prdId", prdId);
        // 상품정보 가져가야함
//        List<Map<String, Object>> productList = getList("selectProduct", params);
//        if (!productList.isEmpty()) {
//            Map<String, Object> product = productList.get(0);
//            model.addAttribute("product", product);
//            String productExhId = (String) product.get("exhId");
//            if (product.get("exhId") == null) {
//                productExhId = "";
//            }
//            /*
//             *
//             * exhId 같은건지 비교후 연산코드 가져와서 select연산코드 연산코드의 row 가져와서 prdPrice 가격에 기획전 할인한 만큼이랑
//             * productPrice랑 비교해서 같으면 order 페이지로 이동 아니면 가격이 다르다. 알려줘야함
//             *
//             */
//            if (productExhId.equals(params.get("exhId"))) {
//                Map<String, Object> saleCode = getOne("selectSale", params);
//                if (saleCode != null) {
//                    boolean salePossible = false;
//                    int result = 0;
//                    String productPrice = (String) params.get("productPrice");
//                    if ("".equals(productPrice)) {
//                        productPrice = "0";
//                    }
//                    if (saleCode.get("calcCd").equals("/")) {
//                        double percent = (double) Integer.parseInt((String) saleCode.get("saleVal")) / 100;
//                        double calcPrice = Integer.parseInt((String) product.get("prdPrc")) * percent;
//                        result = (int) Math.round(calcPrice);
//                        salePossible = (result == Integer.parseInt(productPrice));
//                    } else if (saleCode.get("calcCd").equals("-")) {
//                        result = Integer.parseInt((String) product.get("prdPrc")) - Integer.parseInt((String) saleCode.get("saleVal"));
//                        salePossible = (result == Integer.parseInt(productPrice));
//                    } else if (saleCode.get("calcCd").equals("=")) {
//                        result = Integer.parseInt((String) saleCode.get("saleVal"));
//                        salePossible = (result == Integer.parseInt(productPrice));
//                    }
//                    if (salePossible) {
//                        model.addAttribute("productPrice", result);
//                        model.addAttribute("reservPrice", params.get("reservPrice"));
//                        // params : {saleCode=M, exhId=EXH0000000001, reservPrice=8000,
//                        // productPrice=80000, prdId=44}
//                        model.addAttribute("saleCd", params.get("saleCd"));
//                        model.addAttribute("exhId", params.get("exhId"));
//
//                    }
//                }
//
//            }
//        }
//
//        List<Map<String, Object>> list = getList("selectProductFiles", params);
//        // 상품리스트의 studioId로 selectStudio 해서 스튜디오 정보를 가져옴
//        if (!list.isEmpty()) {
//            params.put("studioId", list.get(0).get("studioId"));
//            model.addAttribute("studioId", list.get(0).get("studioId"));
//            Map<String, Object> photo = list.get(0);
//            model.addAttribute("photo", photo);
//        }
//
//        // 상품 카테고리 리스트
//        List<Map<String, Object>> categories = getList("selectCategories", params);
//        if (!categories.isEmpty()) {
//            model.addAttribute("categories", categories);
//        }
//
//        // 스튜디오 정보 (스튜디오명, 좌표, 스튜디오설명, 주소, 연락처)
//        List<Map<String, Object>> studioList = studioService.selectStudio(params);
//        if (!studioList.isEmpty()) {
//            Map<String, Object> studio = studioList.get(0);
//            String tel = (String) studio.get("tel");
//            studio.put("tel", formatPhone(tel));
//            model.addAttribute("studio", studio);
//        }
//
//        model.addAttribute("prdId", prdId);
//        model.addAttribute("list", list);

        model.addAttribute("title", "찍자 - 주문하기");

//        if (profiles.equals("local")) {
        // TEST 아엠포트
        model.addAttribute("impInitNumber", "imp15108074");
//        } else {
        // REAL 아엠포트
//            model.addAttribute("impInitNumber", "imp46686250");
//        }

        return "/client/order";
    }

    /**
     * 주문하기(예약하기)
     *
     * @param prdId
     * @param params
     * @param model
     * @return
     */
    @GetMapping(value = {"/goods/order/event/{prdId}"})
    public String eventOrder(@PathVariable("prdId") String prdId, @RequestParam Map<String, Object> params, Model model) {
        // {saleCode=S, exhId=EXH0000000001, reservPrice=6500, productPrice=65000}
//        params.put("prdId", prdId);
//        model.addAttribute("prdId", prdId);
//        // 상품정보 가져가야함
//        List<Map<String, Object>> productList = getList("selectProduct", params);
//        if (!productList.isEmpty()) {
//            Map<String, Object> product = productList.get(0);
//            model.addAttribute("product", product);
//            String productExhId = (String) product.get("exhId");
//            /*
//             *
//             * exhId 같은건지 비교후 연산코드 가져와서 select연산코드 연산코드의 row 가져와서 prdPrice 가격에 기획전 할인한 만큼이랑
//             * productPrice랑 비교해서 같으면 order 페이지로 이동 아니면 가격이 다르다. 알려줘야함
//             *
//             */
//            if (params.get("exhId").equals(productExhId)) {
//                Map<String, Object> saleCode = getOne("selectSale", params);
//                String productPrice = (String) params.get("productPrice");
//                if ("".equals(productPrice)) {
//                    productPrice = "0";
//                }
//                if (saleCode != null) {
//                    boolean salePossible = false;
//                    int result = 0;
//                    if (saleCode.get("calcCd").equals("/")) {
//                        double percent = (double) Integer.parseInt((String) saleCode.get("saleVal")) / 100;
//                        double calcPrice = Integer.parseInt((String) product.get("prdPrc")) * percent;
//                        result = (int) Math.round(calcPrice);
//                        salePossible = (result == Integer.parseInt(productPrice));
//                    } else if (saleCode.get("calcCd").equals("-")) {
//                        result = Integer.parseInt((String) product.get("prdPrc")) - Integer.parseInt((String) saleCode.get("saleVal"));
//                        salePossible = (result == Integer.parseInt(productPrice));
//                    } else if (saleCode.get("calcCd").equals("=")) {
//                        result = Integer.parseInt((String) saleCode.get("saleVal"));
//                        salePossible = (result == Integer.parseInt(productPrice));
//                    }
//                    if (salePossible) {
//                        model.addAttribute("productPrice", result);
//                        model.addAttribute("reservPrice", params.get("reservPrice"));
//                        // params : {saleCode=M, exhId=EXH0000000001, reservPrice=8000,
//                        // productPrice=80000, prdId=44}
//                        model.addAttribute("saleCd", params.get("saleCd"));
//                        model.addAttribute("exhId", params.get("exhId"));
//
//                    }
//                }
//
//            }
//        }
//
//        List<Map<String, Object>> list = getList("selectProductFiles", params);
//        // 상품리스트의 studioId로 selectStudio 해서 스튜디오 정보를 가져옴
//        if (!list.isEmpty()) {
//            params.put("studioId", list.get(0).get("studioId"));
//            model.addAttribute("studioId", list.get(0).get("studioId"));
//            Map<String, Object> photo = list.get(0);
//            model.addAttribute("photo", photo);
//        }
//
//        // 상품 카테고리 리스트
//        List<Map<String, Object>> categories = getList("selectCategories", params);
//        if (!categories.isEmpty()) {
//            model.addAttribute("categories", categories);
//        }
//
//        // 스튜디오 정보 (스튜디오명, 좌표, 스튜디오설명, 주소, 연락처)
//        List<Map<String, Object>> studioList = studioService.selectStudio(params);
//        if (!studioList.isEmpty()) {
//            Map<String, Object> studio = studioList.get(0);
//            String tel = (String) studio.get("tel");
//            studio.put("tel", formatPhone(tel));
//            model.addAttribute("studio", studio);
//        }
//
//        model.addAttribute("prdId", prdId);
//        model.addAttribute("list", list);

        model.addAttribute("title", "찍자 - 주문하기");
        return "/client/orderFreeEvent";
    }

    /**
     * 상품 댓글 목록 ajax
     *
     * @param model
     * @return
     */
    @GetMapping(value = {"/goods/repl/list/ajax"}, produces = "application/json;charset=UTF-8")
    public String getProductReplsAjax(ProductReplyRequestDto productReplyRequestDto, Pageable pageable, Model model) {
        model.addAttribute("list", productReplyService.findAllByProduct(productReplyRequestDto, pageable).getContent());
        return "client/goods_repl_list_ajax";
    }

    /**
     * 내가 찜한 목록 페이지
     *
     * @param params
     * @param model
     * @return
     */
    @GetMapping(value = {"/wish_list"})
    public String wish_list(@LoginUser UserResponseDto user, @RequestParam("nowPage") String nowPage, Pageable pageable, Model model) {
        Page<UserWishProduct> pages = userWishProductRepository.findAll(pageable);
        model.addAttribute("totCount", pages.getTotalElements());
        model.addAttribute("totPage", pages.getTotalPages());
        model.addAttribute("list", pages.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("title", "찍자 - 찜목록");
        return "client/wish_list";
    }

    /**
     * 내가 찜한 목록 ajax
     *
     * @param params
     * @param model
     * @return
     */
    @GetMapping(value = {"/goods/wish/list/ajax"})
    public String getProductWishAjax(@LoginUser UserResponseDto user, @RequestParam Map<String, Object> params,Pageable pageable,  Model model) {
        Page<UserWishProduct> pages = userWishProductRepository.findAll(pageable);
        model.addAttribute("totCount", pages.getTotalElements());
        model.addAttribute("totPage", pages.getTotalPages());
        model.addAttribute("list", pages.getContent());
        return "client/goods_list_ajax";
    }

    /**
     * 상품 문의하기
     *
     * @param prdId
     * @param model
     * @return
     */
    @GetMapping(value = {"/goods/question/{prdId}"})
    public String question(@PathVariable String prdId, Model model) {
        model.addAttribute("productId", prdId);
        List<Map<String, Object>> commonCodes = new ArrayList<>();
        for (EQuestionCategory commonCode : EQuestionCategory.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("commCd", commonCode.getKey());
            map.put("commCdNm", commonCode.getValue());
            //selected 옵션
            commonCodes.add(map);
        }
        model.addAttribute("cateList", commonCodes);

        // 스튜디오 정보 (스튜디오명, 좌표, 스튜디오설명, 주소, 연락처)
        Optional<Product> oProduct = productRepository.findById(prdId);
        oProduct.ifPresent(product -> model.addAttribute("studioId", product.getStudio().getStudioId()));

        model.addAttribute("title", "찍자 - 문의하기");
        return "/client/question";
    }

    /**
     * 마이페이지
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/edit_memberMain")
    public String edit_memberMain(
            ModelMap model, @RequestParam Map<String, Object> params) {
        model.addAttribute("title", "찍자 - 마이페이지");
        return "/client/edit_memberMain";
    }

    /**
     * 예약 목록 페이지
     *
     * @param user
     * @param model
     * @return
     */
    @GetMapping(value = {"/reservation/list"})
    public String reservationList(@LoginUser UserResponseDto user, Pageable pageable, Model model) {
        Page<Reservation> list = reservationRepository.findAll(pageable);
        model.addAttribute("totCount", list.getTotalElements());
        model.addAttribute("totPage", list.getTotalPages());
        model.addAttribute("title", "찍자 - 예약내역");
        return "/client/reservation_list";
    }

    /**
     * 예약 목록 페이지
     *
     * @param model
     * @return
     */
    @GetMapping(value = {"/reservation/list/ajax"})
    public String reservationAjaxList(@LoginUser UserResponseDto user, @RequestParam("searchText") String searchText, Pageable pageable, Model model) {
        Page<Reservation> list = reservationRepository.findAll(pageable);
        List<ReservationResposeDto> reservationResposeDtos = new ArrayList<>();
        for(Reservation reservation : list.getContent()){
            reservationResposeDtos.add(new ReservationResposeDto(reservation));
        }
        model.addAttribute("list", reservationResposeDtos);
        model.addAttribute("searchText", searchText);
        return "/client/reservation_list_ajax";
    }

    /**
     * 내 요청내역 페이지
     *
     * @param param
     * @param model
     * @return
     */
    @GetMapping(value = {"/request/my_list"})
    public String requestMyList(@LoginUser UserResponseDto user, @RequestParam Map<String, Object> param, Pageable pageable, Model model) {
        Page<UserRequest> requests = userRequestRepository.findAll(pageable);
        model.addAttribute("totCount", requests.getTotalElements());
        model.addAttribute("totPage", requests.getTotalPages());
        model.addAttribute("title", "찍자 - 내 요청목록");
        return "/client/request_my_list";
    }

    /**
     * 상세 및 리스트 같은 페이지
     *
     * @param param
     * @param model
     * @return
     */
    @GetMapping(value = {"/request/my_list/ajax"})
    public String requestMyListAjax(@RequestParam Map<String, Object> param, Pageable pageable, Model model) {
        Page<UserRequest> requests = userRequestRepository.findAll(pageable);
        List<UserRequestResponseDto> requestResponseDtos = new ArrayList<>();
        for(UserRequest request : requests.getContent()){
            requestResponseDtos.add(new UserRequestResponseDto(request));
        }
        model.addAttribute("list", requestResponseDtos);
        model.addAttribute("isEmpty", requests.isEmpty());
        model.addAttribute("pageNumber", param.get("pageNumber"));
        return "/client/request_my_list_ajax";
    }


    /**
     * 회원 탈퇴 페이지
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/withdrawal")
    public String withdrawal(
            @LoginUser UserResponseDto user,
            ModelMap model) {
        model.addAttribute("title", "찍자 - 회원탈퇴");
        return "/client/withdrawal";
    }

    /**
     * 내요청에 입찰한 상품 리스트(TODO: 해야함)
     * @param user
     * @param model
     * @return
     */
    @GetMapping(value = "/request/my_list/{requestId}")
    public String myRequestAddProduct(@LoginUser UserResponseDto user, @PathVariable String requestId, ModelMap model){

        UserRequest userRequest = userRequestRepository.findById(requestId).orElseThrow(()-> new IllegalArgumentException("요청사항의 정보가 없습니다."));
        model.addAttribute("requestId", requestId);
        model.addAttribute("userRequest", userRequest);
        model.addAttribute("title", "찍자 - 내요청 입찰리스트");
        return "/client/request_my_list_product";
    }

    @GetMapping(value = "/request/my_list/{requestId}/ajax")
    public String myRequestAddProductAjax(@LoginUser UserResponseDto user, @PathVariable String requestId,  ModelMap model){
//tb_user_req_prd
//        userService.
//        userRequestProductRepository.findByUserRequest(userRequest)

        model.addAttribute("title", "찍자 - 내요청 입찰리스트");
        return "/client/requestReplyMyListAjax";
    }
}
