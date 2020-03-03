package com.zzikza.springboot.web.domain.member;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void add(){
        memberRepository.save(new Member("lee","lee@4themoment.co.kr"));
        Member saved = memberRepository.findById(1L).orElseThrow(()-> new IllegalArgumentException("아이디가 없습니다"));
        assertThat(saved.getName()).isEqualTo("lee");
    }
}