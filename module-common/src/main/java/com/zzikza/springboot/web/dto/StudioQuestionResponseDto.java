package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.studio.StudioQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StudioQuestionResponseDto {
    String id;
    String content;
    StudioResponseDto studio;
    UserResponseDto user;
    List<StudioQuestionReplyResponseDto> studioQuestionReplies = new ArrayList<>();
    String createDate;
    boolean isAnswer = false;
    public StudioQuestionResponseDto(StudioQuestion entity) {
        this.id = entity.getId();
        this.createDate = entity.getCreatedDate();
        this.content = entity.getId();
        this.studio = new StudioResponseDto(entity.getStudio()) ;
        this.user =  new UserResponseDto(entity.getUser()) ;
        this.isAnswer = !entity.getStudioQuestionReplies().isEmpty();
        this.studioQuestionReplies = entity.getStudioQuestionReplies().stream().map(StudioQuestionReplyResponseDto::new).collect(Collectors.toList());
    }

}
