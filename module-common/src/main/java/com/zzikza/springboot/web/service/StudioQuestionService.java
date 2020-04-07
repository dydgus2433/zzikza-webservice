package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.studio.StudioQuestion;
import com.zzikza.springboot.web.domain.studio.StudioQuestionReply;
import com.zzikza.springboot.web.domain.studio.StudioQuestionRepository;
import com.zzikza.springboot.web.dto.StudioQuestionReplyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StudioQuestionService {

    private final StudioQuestionRepository studioQuestionRepository;

    @Transactional
    public void addQuestionReply(StudioQuestionReplyRequestDto reply) {
        StudioQuestion studioQuestion = studioQuestionRepository.findById(reply.getId()).orElseThrow(() -> new IllegalArgumentException("본문이 없습니다."));
        StudioQuestionReply questionReply = StudioQuestionReply.builder().content(reply.getContent()).build();
        studioQuestion.addQuestionReply(questionReply);

    }
}
