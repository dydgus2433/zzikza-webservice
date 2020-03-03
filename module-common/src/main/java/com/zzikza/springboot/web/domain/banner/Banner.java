package com.zzikza.springboot.web.domain.banner;

import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tb_adv")
public class Banner {
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
    String title;

    @Builder
    public Banner(String title) {
        this.title = title;
    }
}
