package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "tb_stdo_qstn_repl")
public class StudioQuestionReply {
    @Id
    @Column(name = "STDO_QSTN_REPL_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
                    @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
                    @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
                    @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_stdo_qstn_repl"),
                    @org.hibernate.annotations.Parameter(name = "prefix_key", value = "SQR"),
                    @Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
            })
    String id;

    @Column(name = "REPL_CONTENT")
    String content;

    @ManyToOne
    @JoinColumn(name = "STDO_QSTN_ID")
    StudioQuestion studioQuestion;

    @Builder
    public StudioQuestionReply(String content, StudioQuestion studioQuestion) {
        this.content = content;
        this.studioQuestion = studioQuestion;
    }

    public void setStudioQuestion(StudioQuestion studioQuestion) {
        //기존 스튜디오와 관계 제거
        if(this.studioQuestion != null){
            this.studioQuestion.getStudioQuestionReplies().remove(this);
        }

        studioQuestion.getStudioQuestionReplies().add(this);
        this.studioQuestion = studioQuestion;
    }
}
