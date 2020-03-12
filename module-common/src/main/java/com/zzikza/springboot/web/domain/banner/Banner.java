package com.zzikza.springboot.web.domain.banner;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.enums.EBannerCategory;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.enums.ETargetCategory;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tb_adv")
public class Banner extends BaseTimeEntity {
    @Id
    @Column(name = "ADV_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @Parameter(name = "table_name", value = "sequences"),
            @Parameter(name = "value_column_name", value = "currval"),
            @Parameter(name = "segment_column_name", value = "name"),
            @Parameter(name = "segment_value", value = "tb_adv"),
            @Parameter(name = "prefix_key", value = "ADV"),
            @Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;
    @Column(name = "ADV_TITLE")
    String title;

    @Column(name = "ADV_CONTENT")
    String content;

    String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADV_CATE_CD")
    EBannerCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADV_TARGET")
    ETargetCategory targetCategory;


    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_STAT_CD")
    public EShowStatus showStatus;

    @OneToMany(mappedBy = "banner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<BannerFile> bannerFiles = new ArrayList<>();

    @OneToMany(mappedBy = "banner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<BannerMobileFile> bannerMobileFiles = new ArrayList<>();

    @Builder
    public Banner(String title, String content, String url, EBannerCategory category, ETargetCategory targetCategory, EShowStatus showStatus) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.category = category;
        this.targetCategory = targetCategory;
        this.showStatus = showStatus;
    }

    public void addBannerFile(BannerFile bannerFile) {
        this.bannerFiles.add(bannerFile);
        if(bannerFile.getBanner() != this){
            bannerFile.setBanner(this);
        }
    }

    public void addBannerMobileFile(BannerMobileFile bannerMobileFile) {
        this.bannerMobileFiles.add(bannerMobileFile);
        if(bannerMobileFile.getBanner() != this){
            bannerMobileFile.setBanner(this);
        }
    }

    public void deleteBannerFile(BannerFile bannerFile) {
        this.bannerFiles.remove(bannerFile);
        bannerFile.setBanner(null);
    }
}
