//package com.zzikza.springboot.web.domain.portpolio;
//
//import com.zzikza.springboot.web.domain.BaseTimeEntity;
//import com.zzikza.springboot.web.domain.enums.EBannerCategory;
//import com.zzikza.springboot.web.domain.enums.EShowStatus;
//import com.zzikza.springboot.web.domain.enums.ETargetCategory;
//import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
//import com.zzikza.springboot.web.domain.studio.Studio;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@NoArgsConstructor
//@Entity
//@Table(name = "tb_prt")
//public class Portfolio extends BaseTimeEntity {
//
//    @Id
//    @Column(name = "PRT_ID", length = 15)
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
//    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
//            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
//            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
//            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
//            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_prt"),
//            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "PRT"),
//            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
//    String id;
//    @Column(name = "PRT_TITLE")
//    String title;
//    @Column(name = "PRT_CONTENT")
//    String content;
//
//    @OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    List<PortfolioFile> portfolioFiles = new ArrayList<>();
//
//    @ManyToOne
//    @JoinColumn(name = "REQ_PRD_ID")
//    UserRequestProduct userRequestProduct;
//
//    @ManyToOne
//    @JoinColumn(name = "STDO_SEQ")
//    Studio studio;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "SHOW_STAT_CD")
//    public EShowStatus showStatus;
//
//    @Builder
//    public Portfolio(String title, String content, String url, EBannerCategory category, ETargetCategory targetCategory, EShowStatus showStatus) {
//        this.title = title;
//        this.content = content;
//        this.showStatus = showStatus;
//    }
//
//    public void addPortpolioFile(PortfolioFile portpolioFile) {
//        this.portfolioFiles.add(portpolioFile);
//        if (portpolioFile.getPortfolio() != this) {
//            portpolioFile.setPortfolio(this);
//        }
//    }
//
//
//    public void deletePortpolioFile(PortfolioFile portpolioFile) {
//        this.portfolioFiles.remove(portpolioFile);
//        portpolioFile.setPortfolio(null);
//    }
//
//    public void setStudio(Studio studio) {
//        //기존 스튜디오와 관계 제거
//        if(this.studio != null){
//            this.studio.getPortfolios().remove(this);
//        }
//
//        this.studio = studio;
//        studio.getPortfolios().add(this);
//    }
//}
