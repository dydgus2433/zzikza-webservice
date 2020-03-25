package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ExhibitionResponseDto {

    String title;
    String id;
    boolean selected = false;

    @Builder
    public ExhibitionResponseDto(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public ExhibitionResponseDto(Exhibition entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
