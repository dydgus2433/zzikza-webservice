package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity(name = "tb_stdo")
public class Studio {
    @Id
    @Column(name = "STDO_SEQ")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
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

    @OneToOne
    @JoinColumn(name = "STDO_DTL_ID", nullable = false)
    StudioDetail studioDetail;

    //사업자등록증 , 상품공유파일 구분자 필요
    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<StudioFile> studioFiles = new ArrayList<StudioFile>();

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<StudioBoard> studioBoards = new ArrayList<StudioBoard>();

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<StudioQuestion> studioQuestions = new ArrayList<>();



    @Builder
    public Studio(String studioId, StudioDetail studioDetail) {
        this.studioId = studioId;
        this.studioDetail = studioDetail;
    }

    public void addStudioBoard(StudioBoard studioBoard){
        this.studioBoards.add(studioBoard);
        if(studioBoard.getStudio() != this){
            studioBoard.setStudio(this);
        }
    }

    public void addProudct(Product product){
        this.products.add(product);
        if(product.getStudio() != this){
            product.setStudio(this);
        }
    }

    public void addStudioQuestion(StudioQuestion studioQuestion) {
        this.studioQuestions.add(studioQuestion);
        if(studioQuestion.getStudio() != this){
            studioQuestion.setStudio(this);
        }
    }
}