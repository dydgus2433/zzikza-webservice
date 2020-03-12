package com.zzikza.springboot.web.domain.exhibition;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.sale.Sale;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "tb_exh")
public class Exhibition  extends BaseTimeEntity {
    @Id
    @Column(name = "EXH_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_exh"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "EXH"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;
    String title;

    @OneToMany(mappedBy = "exhibition", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Sale> sales = new ArrayList<>();

    @Builder
    public Exhibition(String title) {
        this.title = title;
    }

    public void addSale(Sale sale){
        this.sales.add(sale);
    }
}
