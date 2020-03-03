package com.zzikza.springboot.web;

import com.zzikza.springboot.web.domain.member.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/member")
    public Member get(){
        return new Member("lee", "lee@4themoment.co.kr");
    }

}
