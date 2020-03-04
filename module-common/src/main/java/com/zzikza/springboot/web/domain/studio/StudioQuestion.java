package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.domain.user.User;
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
@Entity(name = "tb_stdo_qstn")
public class StudioQuestion {
    @Id
    @Column(name = "STDO_QSTN_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator",
            parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_stdo_qstn"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "SQI"),
            @Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
            })
    String id;

    @Column(name = "QSTN_CONTENT")
    String content;

    @ManyToOne
    @JoinColumn(name = "STDO_SEQ")
    Studio studio;

    @ManyToOne
    @JoinColumn(name = "USER_SEQ")
    User user;

    @OneToMany(mappedBy = "studioQuestion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<StudioQuestionReply> studioQuestionReplies = new ArrayList<>();

    @Builder
    public StudioQuestion(String content, Studio studio, User user) {
        this.content = content;
        this.studio = studio;
        this.user = user;
    }

    public void addQuestionReply(StudioQuestionReply studioQuestionReply){
        this.studioQuestionReplies.add(studioQuestionReply);
        if(studioQuestionReply.getStudioQuestion() != this){
            studioQuestionReply.setStudioQuestion(this);
        }
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}
