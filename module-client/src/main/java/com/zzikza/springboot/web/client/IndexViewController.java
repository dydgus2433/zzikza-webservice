package com.zzikza.springboot.web.client;


import com.zzikza.springboot.web.client.annotation.LoginUser;
import com.zzikza.springboot.web.domain.banner.BannerRepository;
import com.zzikza.springboot.web.domain.enums.EBannerCategory;
import com.zzikza.springboot.web.domain.enums.ESnsType;
import com.zzikza.springboot.web.domain.exhibition.ExhibitionRepository;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.product.ProductExhibition;
import com.zzikza.springboot.web.domain.product.ProductExhibitionRepository;
import com.zzikza.springboot.web.domain.product.ProductRepository;
import com.zzikza.springboot.web.dto.ProductResponseDto;
import com.zzikza.springboot.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.zzikza.springboot.web.client.LoginClientViewController.REDIRECT;

@RequiredArgsConstructor
@Controller
public class IndexViewController {
    private final BannerRepository bannerRepository;
    private final ProductRepository productRepository;
    private final ProductExhibitionRepository productExhibitionRepository;
    private final ExhibitionRepository exhibitionRepository;
    @Value("${login.naver.client.id}")
    String NAVER_CLIENT_ID;
    @Value("${login.naver.client.secret}")
    String NAVER_CLIENT_SECRET;
    @Value("${login.kakao.client.id}")
    String KAKAO_CLIENT_ID;
    @Value("${login.kakao.client.secret}")
    String KAKAO_CLIENT_SECRET;
    @Value("${login.google.client.id}")
    String GOOGLE_CLIENT_ID;
    @Value("${login.google.client.secret}")
    String GOOGLE_CLIENT_SECRET;
    @Value("${login.google.SCOPE_URL}")
    String GOOGLE_SCOPE_URL;
    @Value("${login.facebook.app.id}")
    String FACEBOOK_APP_ID;
    @Value("${login.facebook.app.secret}")
    String FACEBOOK_APP_SECRET;
    @Value("${login.facebook.scope.url}")
    String FACEBOOK_SCOPE_URL;
    @Value("${login.facebook.client.id}")
    String FACEBOOK_CLIENT_ID;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("topAdvs", bannerRepository.findAllByCategory(EBannerCategory.TOP));
        model.addAttribute("midAdvs", bannerRepository.findAllByCategory(EBannerCategory.MID));
        model.addAttribute("botAdvs", bannerRepository.findAllByCategory(EBannerCategory.BOT));

        List<Product> productList = productRepository.findAll();
        List<ProductResponseDto> productDtoList = new ArrayList<>();
        for (Product product : productList) {
            ProductResponseDto productResponseDto = new ProductResponseDto(product);
            if (!product.getProductFiles().isEmpty()) {
                productResponseDto.setProductFile(product.getProductFiles().get(0));
            }
            productDtoList.add(productResponseDto);
        }
        model.addAttribute("list", productDtoList);
        List<ProductExhibition> productExhibition = productExhibitionRepository.findAll();
        HashSet<Product> products = (HashSet<Product>) productExhibition.stream().map(ProductExhibition::getProduct).collect(Collectors.toSet());
        model.addAttribute("is-empty-exh-goods", true);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (ProductExhibition pe : productExhibition) {
            Product p = pe.getProduct();
            ProductResponseDto dto = new ProductResponseDto(p);
            model.addAttribute("is-empty-exh-goods", p.getProductFiles().isEmpty());
            if (!p.getProductFiles().isEmpty()) {
                dto.setProductFile(p.getProductFiles().get(0));
            }
            productResponseDtos.add(dto);
        }

        model.addAttribute("exhGoodslist", productResponseDtos);
        model.addAttribute("exhibitions", exhibitionRepository.findAll());

        model.addAttribute("title", "찍자");
        return "client/index";
    }

    @GetMapping(value = {"/login"})
    public String login(HttpSession session, Model model, HttpServletRequest request)
            throws UnsupportedEncodingException {
        setModelCallbackUrl(model, request);

        model.addAttribute("title", "찍자 - 로그인");
        return "/client/login";
    }

    public String generateState() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    /**
     * CSRF 방지를 위한 상태 토큰 생성 코드
     * 상태 토큰은 추후 검증을 위해 세션에 저장되어야 한다.
     *
     * @param req
     * @return
     */
    public String setState(HttpServletRequest req) {
        String state = generateState();
        req.getSession().setAttribute("state", state);
        return state;
    }

    /**
     * 비밀번호 수정 페이지
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/edit_password")
    public String edit_password(@LoginUser UserResponseDto user,
            Model model) {

        // TODO : SNS type이면 리다이렉트
        if(!user.getSnsType().equals(ESnsType.NORMAL)){
            return REDIRECT + "/edit_memberMain";
        }
        model.addAttribute("title", "찍자 - 비밀번호 설정");
        return "/client/edit_password";
    }

    /**
     * 아이디 찾기 페이지
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/find_id")
    public String find_id(Model model) {
        model.addAttribute("title", "찍자 - 아이디 찾기");
        return "/client/find_id";
    }

    /**
     * 비밀번호 찾기 페이지
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/find_password")
    public String find_password(
            Model model) {
        model.addAttribute("title", "찍자 - 비밀번호 찾기");
        return "/client/find_password";
    }

    @GetMapping(value = "/withdrawal")
    public String withdrawal(
            Model model) {
        model.addAttribute("title", "찍자 - 회원탈퇴");
        return "/client/withdrawal";
    }

    /**
     * 회원정보 수정 페이지
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/edit_memberInfo")
    public String edit_memberInfo(Model model) {

        model.addAttribute("title", "찍자 - 회원정보 수정");
        return "/client/edit_memberInfo";
    }

    @GetMapping(value = {"/join"})
    public String join(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        setModelCallbackUrl(model, request);

        model.addAttribute("title", "찍자 - 회원가입");
        return "/client/join";
    }

    private void setModelCallbackUrl(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String HTTPS = "http://";
        String portNumber = ":" + request.getLocalPort();
        if (request.isSecure()) {
            HTTPS = "https://";
            portNumber = "";
        }
        String naverCallbackUrl = setCallbackUrl(request, HTTPS, portNumber, "/oauth2/naver");
        String kakaoCallbackUrl = setCallbackUrl(request, HTTPS, portNumber, "/oauth2/kakao");
        String googleCallbackUrl = setCallbackUrl(request, HTTPS, portNumber, "/oauth2/google");
        String facebookCallbackUrl = setCallbackUrl(request, HTTPS, portNumber, "/oauth2/facebook");
//		String naverCallbackUrl = "http://zzikza.com/oauth2/naver";

        String state = setState(request);
        String naverUrl = "https://nid.naver.com/oauth2.0/authorize?client_id=" + NAVER_CLIENT_ID
                + "&response_type=code&redirect_uri=" + naverCallbackUrl + "&state=" + state;
        String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + KAKAO_CLIENT_ID + "&redirect_uri="
                + kakaoCallbackUrl + "&response_type=code" + "&state=" + state;
        String googleUrl = "https://accounts.google.com/o/oauth2/auth?client_id=" + GOOGLE_CLIENT_ID + "&redirect_uri="
                + googleCallbackUrl + "&response_type=code" + "&state=" + state + "&scope="
                + URLEncoder.encode(GOOGLE_SCOPE_URL, "UTF-8") + "&access_type=offline";
        String facebookUrl = "https://www.facebook.com/v4.0/dialog/oauth?client_id=" + FACEBOOK_APP_ID
                + "&redirect_uri=" + facebookCallbackUrl + "&state=" + state + "&response_type=code";
        System.out.println("facebookUrl : " + facebookUrl);
        model.addAttribute("naverUrl", naverUrl);
        model.addAttribute("kakaoUrl", kakaoUrl);
        model.addAttribute("googleUrl", googleUrl);
        model.addAttribute("facebookUrl", facebookUrl);
    }

    private String setCallbackUrl(HttpServletRequest request, String HTTPS, String portNumber, String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(HTTPS + request.getServerName() + portNumber + s,
                "UTF-8");
    }

    /**
     * 더보기 페이지(아이디 필요) - 아이디 정보에 대한 것 넣어져있음. 아니면 전화번호 인증 후 볼 수 있게
     *
     * @param model
     * @return
     */
    @GetMapping(value = {"/more"})
    public String morePage(Model model) {
//        setUserSeq(request, params);
//        // 예약건
//        List<Map<String, Object>> reservations = scheduleService.selectReservations(params);
//        // 결제건
//
//        // 찜한 건수
//        List<Map<String, Object>> wishs = productService.selectProductWishs(params);
//        model.addAttribute("reservationCount", reservations.size());
//        model.addAttribute("wishCount", wishs.size());

        model.addAttribute("title", "찍자 - 더보기");
        return "/client/more";
    }

    /**
     * 일반회원가입 페이지
     *
     * @param model
     * @return
     */
    @GetMapping(value = {"/join_normal"})
    public String join2(Model model) {

        model.addAttribute("title", "찍자 - 일반 회원가입");
        return "/client/join_normal";
    }
}
