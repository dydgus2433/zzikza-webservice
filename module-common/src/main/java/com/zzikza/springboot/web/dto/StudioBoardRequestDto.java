package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import com.zzikza.springboot.web.domain.studio.StudioBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StudioBoardRequestDto {
    private String studioSeq;
    private String title;
    private String content;
    private int fileLength;
    private EBoardCategory brdCateCd;

    @Builder
    public StudioBoardRequestDto(String title, String content, int fileLength, EBoardCategory brdCateCd) {
        this.title = title;
        this.content = content;
        this.fileLength = fileLength;
        this.brdCateCd = brdCateCd;
    }

    public StudioBoard toEntity() {
        return StudioBoard.builder()
                .title(getTitle())
                .content(getContent())
                .boardCategoryCode(getBrdCateCd())
                .build();
    }
    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }
}
