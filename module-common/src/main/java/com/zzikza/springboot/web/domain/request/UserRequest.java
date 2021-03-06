package com.zzikza.springboot.web.domain.request;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.enums.EUserRequestCategory;
import com.zzikza.springboot.web.domain.enums.EUserRequestStatus;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity(name = "tb_user_req")
public class UserRequest extends BaseTimeEntity {

    @Id
    @Column(name = "REQ_ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_user_req"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "REQ"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne
    @JoinColumn(name = "USER_SEQ")
    User user;

    @Column(name = "REQ_TITLE")
    String title;
    @Column(name = "REQ_CONTENT")
    String content;
    @Column(name = "SIDO")
    String sido;
    @Column(name = "GUGUN")
    String gugun;

    @Enumerated(EnumType.STRING)
    @Column(name = "REQ_STAT_CD")
    EUserRequestStatus requestStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "REQ_CATE_CD")
    EUserRequestCategory requestCategory;

    @OneToMany(mappedBy = "userRequest", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<UserRequestFile> userRequestFiles = new ArrayList<>();

    @OneToMany(mappedBy = "userRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserRequestProduct> userRequestProducts = new ArrayList<>();

    @Builder
    public UserRequest(String content) {
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addUserRequestFile(UserRequestFile userRequestFile) {
        this.userRequestFiles.add(userRequestFile);
        if (userRequestFile.getUserRequest() != this) {
            userRequestFile.setUserRequest(this);
        }
    }

    public void addUserRequestProduct(UserRequestProduct userRequestProduct) {
        this.userRequestProducts.add(userRequestProduct);
        if (userRequestProduct.getUserRequest() != this) {
            userRequestProduct.setUserRequest(this);
        }
    }
}
