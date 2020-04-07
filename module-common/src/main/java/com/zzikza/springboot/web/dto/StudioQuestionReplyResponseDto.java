package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.studio.StudioQuestionReply;

public class StudioQuestionReplyResponseDto {
    String content;

    public StudioQuestionReplyResponseDto(StudioQuestionReply reply) {
        this.content = reply.getContent();
    }
}
