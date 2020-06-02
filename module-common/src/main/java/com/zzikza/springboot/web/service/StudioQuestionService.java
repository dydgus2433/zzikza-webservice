package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.studio.*;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.domain.user.UserRepository;
import com.zzikza.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudioQuestionService {

    private final StudioQuestionRepository studioQuestionRepository;
    private final StudioRepository studioRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addQuestionReply(StudioQuestionReplyRequestDto reply) {
        StudioQuestion studioQuestion = studioQuestionRepository.findById(reply.getId()).orElseThrow(() -> new IllegalArgumentException("본문이 없습니다."));
        StudioQuestionReply questionReply = StudioQuestionReply.builder().content(reply.getContent()).build();
        studioQuestion.addQuestionReply(questionReply);

    }


    public void insertQuestion(StudioQuestionRequestDto questionResponseDto, UserResponseDto userRequestDto){
        Optional<Studio> optionalStudio = studioRepository.findById(questionResponseDto.getStudioId());
        Optional<User> optionalUser = userRepository.findById(userRequestDto.getId());

        StudioQuestion studioQuestion  = StudioQuestion.builder()
                .user(optionalUser.orElseThrow(()-> new IllegalArgumentException("유저 정보가 없습니다.")))
                .studio(optionalStudio.orElseThrow(()-> new IllegalArgumentException("스튜디오 정보가 없습니다.")))
                .content(questionResponseDto.getContent())
                .questionCategory(questionResponseDto.getQuestionCategory())
                .build();
        studioQuestionRepository.save(studioQuestion);
    }
}
