package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.member.Member;
import com.zzikza.springboot.web.domain.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceCustom {

    @Autowired
    private MemberRepository memberRepository;

    public MemberServiceCustom(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Long signup(Member member){
        return memberRepository.save(member).getId();
    }
}
