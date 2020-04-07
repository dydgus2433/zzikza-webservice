package com.zzikza.springboot.web.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioQuestionReplyRepository extends JpaRepository<StudioQuestionReply, String> {
    List<StudioQuestionReply> findAllByStudioQuestion(StudioQuestion studioQuestion);
}
