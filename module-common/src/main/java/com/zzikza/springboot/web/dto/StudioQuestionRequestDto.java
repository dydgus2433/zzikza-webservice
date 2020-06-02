package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EQuestionCategory;
import com.zzikza.springboot.web.domain.studio.StudioQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudioQuestionRequestDto {
    String content;
    String title;
    String studioId;
    EQuestionCategory questionCategory;
}
