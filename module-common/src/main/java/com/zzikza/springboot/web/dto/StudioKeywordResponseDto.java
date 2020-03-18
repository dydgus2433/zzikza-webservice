package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.studio.StudioKeyword;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class StudioKeywordResponseDto {
    String id;
    String keywordName;

    @Builder
    public StudioKeywordResponseDto(String id, String keywordName) {
        this.id = id;
        this.keywordName = keywordName;
    }

    public StudioKeywordResponseDto(StudioKeyword entity) {
        this.id = entity.getId();
        this.keywordName = entity.getKeywordName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudioKeywordResponseDto that = (StudioKeywordResponseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(keywordName, that.keywordName);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id, keywordName);
//    }
}
