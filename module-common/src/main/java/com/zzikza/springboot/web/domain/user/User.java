package com.zzikza.springboot.web.domain.user;

import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "tb_user")
public class User {
    @Id
    @Column(name = "USER_SEQ")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @Parameter(name = "table_name", value = "sequences"),
            @Parameter(name = "value_column_name", value = "currval"),
            @Parameter(name = "segment_column_name", value = "name"),
            @Parameter(name = "segment_value", value = "tb_user"),
            @Parameter(name = "prefix_key", value = "USR"),
            @Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;
    @Column(name = "USER_ID", unique = true)
    String userId;

    @Builder
    public User(String userId) {
        this.userId = userId;
    }
}
