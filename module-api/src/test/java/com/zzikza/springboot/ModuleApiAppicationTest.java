package com.zzikza.springboot;

import com.zzikza.springboot.web.domain.member.Member;
import com.zzikza.springboot.web.service.MemberServiceCustom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModuleApiAppicationTest {
    @Autowired
    private MemberServiceCustom memberServiceCustom;

    @Test
    public void save() {
        Member member = new Member("jojoldu", "jojoldu@gmail.com");
        Long id = memberServiceCustom.signup(member);
        assertThat(id, is(1L));
    }
}