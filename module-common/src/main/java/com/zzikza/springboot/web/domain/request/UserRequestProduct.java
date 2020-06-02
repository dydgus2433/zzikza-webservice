package com.zzikza.springboot.web.domain.request;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.EProductStatus;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.request.UserRequest;
import com.zzikza.springboot.web.domain.request.UserRequestProductFile;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.dto.UserRequestProductRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "tb_user_req_prd")
public class UserRequestProduct extends BaseTimeEntity {

    @Id
    @Column(name = "REQ_PRD_ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_user_req_prd"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "URP"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column(name = "PRD_PRC")
    Integer price;

    @ManyToOne
    @JoinColumn(name = "STDO_SEQ")
    Studio studio;

    @ManyToOne
    @JoinColumn(name = "REQ_ID")
    UserRequest userRequest;

    @Column
    String title;
    @Column(name = "PRD_DSC")
    String productDescription;
    @Column(name = "PRD_BRF_DSC")
    String productBriefDescription;


    @Enumerated(EnumType.STRING)
    @Column(name = "PRD_STAT_CD")
    EProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRD_CATE_CD")
    EProductCategory productCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_STAT_CD")
    EShowStatus showStatus;

    @Column(name = "PRD_HOUR")
    Integer hour;

    @Column(name = "PRD_MIN")
    Integer minute;

    @OneToMany(mappedBy = "userRequestProduct", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserRequestProductFile> userRequestProductFiles = new ArrayList<>();


//    @OneToOne
//    @JoinColumn(name = "PRT_ID")
//    Portfolio portfolio;

    @Builder
    public UserRequestProduct(String id, String title, Studio studio, Integer price, EProductStatus productStatus,
                   EProductCategory productCategory, EShowStatus showStatus, Integer productHour, Integer productMinuteute,
                   String productDescription, String productBriefDescription) {
        this.id = id;
        this.title = title;
        this.studio = studio;
        this.price = price;
        this.productStatus = productStatus;
        this.productCategory = productCategory;
        this.showStatus = showStatus;
        this.hour = productHour;
        this.minute = productMinuteute;
        this.productDescription = productDescription;
        this.productBriefDescription = productBriefDescription;
    }
//    public void addProtpolio(Portfolio portfolio) {
//        this.portfolio = portfolio;
//    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public void addRequestProductFile(UserRequestProductFile addRequesrProductFile) {
        addRequesrProductFile.setUserRequestProduct(this);
        userRequestProductFiles.add(addRequesrProductFile);
    }

    public void update(UserRequestProductRequestDto dto) {
//        this.id = id;
        this.title = dto.getTitle();
        this.price = dto.getPrice();
//        this.productStatus = dto.getProductStatus;
        this.productCategory = dto.getProductCategory();
        this.showStatus = dto.getShowStatusCode();
        this.hour = dto.getProductHour();
        this.minute = dto.getProductMinute();
        this.productDescription = dto.getProductDescription();
        this.productBriefDescription = dto.getProductBriefDesc();
    }
}
