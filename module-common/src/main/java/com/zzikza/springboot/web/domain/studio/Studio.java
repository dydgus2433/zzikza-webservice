package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.enums.EStudioStatus;
import com.zzikza.springboot.web.domain.enums.ETermStatus;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.request.UserRequestProduct;
import com.zzikza.springboot.web.domain.reservation.Reservation;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.dto.StudioInfoRequestDto;
import com.zzikza.springboot.web.util.PasswordUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity(name = "tb_stdo")
public class Studio extends BaseTimeEntity {
    @Id
    @Column(name = "STDO_SEQ", length = 15)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @Parameter(name = "table_name", value = "sequences"),
            @Parameter(name = "value_column_name", value = "currval"),
            @Parameter(name = "segment_column_name", value = "name"),
            @Parameter(name = "segment_value", value = "tb_stdo"),
            @Parameter(name = "prefix_key", value = "STD"),
            @Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;
    @Column(name = "STDO_ID", unique = true)
    String studioId;

    @Column(name = "STDO_NM", unique = true)
    String studioName;

    @Column(name = "PW")
    String password;


    @Column(name = "BSNS_NO")
    String businessNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "AC_STAT_CD")
    EStudioStatus accountStatus;

    @Column
    String tel;

    @Column(name = "MNG")
    String managerName;

    @Column(name = "MNG_TEL")
    String managerTel;

    @Column(name = "MNG_EMAIL")
    String managerEmail;


    @Column(name = "POST_CD")
    String postCode;

    @Column(name = "ADDR")
    String address;

    @Column(name = "ADDR_DTL")
    String addressDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "RQRD_TERM_YN")
    ETermStatus requiredTermStatus;

    @Column(name = "SIDO")
    String sido;

    @Column(name = "GUGUN")
    String gugun;

    @Column(name = "DONG")
    String dong;

    @Column(name = "LTTD")
    Double lttd;

    @Column(name = "LGTD")
    Double lgtd;


    @OneToOne(mappedBy = "studio")
//    @JoinColumn(name = "STDO_DTL_ID", nullable = false)
    @JoinColumn(name = "STDO_DTL_ID", nullable = true)
    StudioDetail studioDetail;


    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<BusinessFile> businessFiles  = new ArrayList<>();

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<StudioKeywordMap> studioKeywordMaps = new HashSet<>();

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<StudioHoliday> studioHolidays = new ArrayList<>();


    //사업자등록증 , 상품공유파일 구분자 필요
    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<StudioFile> studioFiles = new ArrayList<>();

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<StudioBoard> studioBoards = new ArrayList<>();

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<StudioQuestion> studioQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Reservation> reservations = new ArrayList<>();

    //    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    List<Payment> payments = new ArrayList<>();
//    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    List<Portfolio> portfolios = new ArrayList<>();

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserRequestProduct> userRequestProducts = new ArrayList<>();

    @Builder
    public Studio(String studioId, String password, String studioName, EStudioStatus accountStatus,
                  StudioDetail studioDetail, String businessNumber, String tel, String managerName,
                  String managerTel, String managerEmail, String postCode, String sido, String gugun, String dong, Double lttd, Double lgtd,
                  String address, String addressDetail, ETermStatus requiredTermStatus
    ) {
        this.studioId = studioId;
        this.password = password;
        this.studioName = studioName;
        this.accountStatus = accountStatus;
        this.studioDetail = studioDetail;
        this.businessNumber = businessNumber;
        this.tel = tel;
        this.managerName = managerName;
        this.managerTel = managerTel;
        this.managerEmail = managerEmail;
        this.sido = sido;
        this.gugun = gugun;
        this.dong = dong;
        this.lttd = lttd;
        this.lgtd = lgtd;
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.requiredTermStatus = requiredTermStatus;
    }

    public void addStudioBoard(StudioBoard studioBoard) {
        this.studioBoards.add(studioBoard);
        if (studioBoard.getStudio() != this) {
            studioBoard.setStudio(this);
        }
    }

    public void addStudioFile(StudioFile studioFile) {
        this.studioFiles.add(studioFile);
        if (studioFile.getStudio() != this) {
            studioFile.setStudio(this);
        }
    }

    public void addProudct(Product product) {
        this.products.add(product);
        if (product.getStudio() != this) {
            product.setStudio(this);
        }
    }

    public void addStudioQuestion(StudioQuestion studioQuestion) {
        this.studioQuestions.add(studioQuestion);
        if (studioQuestion.getStudio() != this) {
            studioQuestion.setStudio(this);
        }
    }


    public void addStudioKeywordMap(StudioKeywordMap studioKeywordMap) {
        this.studioKeywordMaps.add(studioKeywordMap);
        if (studioKeywordMap.getStudio() != this) {
            studioKeywordMap.setStudio(this);
        }
    }


    public void addStudioHoliday(StudioHoliday studioHoliday) throws IllegalAccessException {
        boolean isContain = studioHolidays.stream().anyMatch(registedHoliday ->
                registedHoliday.getDateCode().equals(studioHoliday.getDateCode())
                        && registedHoliday.getDateValue().equals(studioHoliday.getDateValue()));
        if (!isContain) {
            this.studioHolidays.add(studioHoliday);
        } else {
            throw new IllegalAccessException("이미 추가된 값입니다.");
        }
        if (studioHoliday.getStudio() != this) {
            studioHoliday.setStudio(this);
        }
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        if (reservation.getStudio() != this) {
            reservation.setStudio(this);
        }
    }

    //키워드맵 전체삭제(초기화해버림)
    public void removeAllStudioKeywordMaps() {
        studioKeywordMaps = new HashSet<>();
    }

//    public void addPortpolio(Portfolio portfolio) {
//        this.portfolios.add(portfolio);
//        if (portfolio.getStudio() != this) {
//            portfolio.setStudio(this);
//        }
//    }

    public void addUserRequestProduct(UserRequestProduct userRequestProduct) {
        this.userRequestProducts.add(userRequestProduct);
        if (userRequestProduct.getStudio() != this) {
            userRequestProduct.setStudio(this);
        }
    }

    public void update(StudioInfoRequestDto entity) {
        if (entity.getChangePassword() != null && !"".equals(entity.getChangePassword())) {
            this.password = PasswordUtil.getEncriptPassword(entity.getChangePassword(), this.studioId);
        }
        if (entity.getBusinessNumber() != null && !"".equals(entity.getBusinessNumber())) {
            this.businessNumber = entity.getBusinessNumber();
        }
        if (entity.getAccountStatus() != null) {
            this.accountStatus = entity.getAccountStatus();
        }
        if (entity.getTel() != null && !"".equals(entity.getTel())) {
            this.tel = entity.getTel();
        }
        if (entity.getManagerName() != null && !"".equals(entity.getManagerName())) {
            this.managerName = entity.getManagerName();
        }
        if (entity.getManagerTel() != null && !"".equals(entity.getManagerTel())) {
            this.managerTel = entity.getManagerTel();
        }
        if (entity.getSido() != null && !"".equals(entity.getSido())) {
            this.sido = entity.getSido();
        }
        if (entity.getGugun() != null && !"".equals(entity.getGugun())) {
            this.gugun = entity.getGugun();
        }
        if (entity.getDong() != null && !"".equals(entity.getDong())) {
            this.dong = entity.getDong();
        }
        if (entity.getLttd() != null) {
            this.lttd = entity.getLttd();
        }
        if (entity.getLgtd() != null) {
            this.lgtd = entity.getLgtd();
        }
    }

    public void addBusinessFile(BusinessFile businessFile) {
         if (businessFile.getStudio() != this) {
            businessFile.setStudio(this);
        }
        this.businessFiles.add(businessFile);

    }

    public void withdrawal() {
        this.accountStatus = EStudioStatus.N;
    }

    public void setStudioDetail(StudioDetail studioDetail) {
        this.studioDetail = studioDetail;
        if (studioDetail.getStudio() != this) {
            studioDetail.setStudio(this);
        }
    }

//    public void addPayment(Payment payment) {
//        this.payments.add(payment);
//        if (payment.getStudio() != this) {
//            payment.setStudio(this);
//        }
//    }
}
