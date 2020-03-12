package com.zzikza.springboot.web.domain.user;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "tb_user_req_fl")
public class UserRequestFile  extends BaseTimeEntity {

    @Id
    @Column(name = "REQ_FL_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_user_req_fl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "URF"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;


    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "TITLE", column = @Column(name = "")),
//            @AttributeOverride(name = "CONTENT", column = @Column(name = ""))
//    })
    private FileAttribute file;

    @ManyToOne
    @JoinColumn(name = "REQ_ID")
    UserRequest userRequest;

    @Builder
    public UserRequestFile(String fileName) {
        this.file.fileName = fileName;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }
}
