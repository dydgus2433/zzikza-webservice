package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.dto.StudioRequestDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import com.zzikza.springboot.web.service.StudioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginViewControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private StudioService studioService;

    @LocalServerPort
    private int port;

    @Test
    public void 메인페이지() {
        //when
        String body = this.restTemplate.getForObject("/", String.class);
        //then
        assertThat(body).contains("찍자 스튜디오");
    }


    @Test
    public void 회원가입_페이지() {
        //when
        String body = this.restTemplate.getForObject("/join", String.class);
        //then
        assertThat(body).contains("회원가입");
    }

    @Test
    public void 비밀번호찾기_페이지() {
        //given
        String body = this.restTemplate.getForObject("/findPassword", String.class);
        //then
        assertThat(body).contains("비밀번호");
    }

    @Test
    public void 아이디찾기_페이지() {
        //given
        String body = this.restTemplate.getForObject("/findId", String.class);
        //then
        assertThat(body).contains("아이디");
    }

    @Test
    @Transactional
    public void 로그인_프로세스() {
        StudioRequestDto params = StudioRequestDto.builder().studioId("tester1").password("tester01").build();
//        studioRepository.save(params.toEntity());
        String url = "http://localhost:" + port + "/loginProcess";
        //when

        Studio all = studioRepository.findByStudioIdAndPassword(params.getStudioId(), params.getEncodingPassword()).orElseThrow(()->new IllegalArgumentException("아이디와 비밀번호가 맞지 않습니다."));
        assertThat(all.getStudioId()).isEqualTo("tester1");
        //given
        ResponseEntity<StudioResponseDto> responseEntity = this.restTemplate.postForEntity(url,params,StudioResponseDto.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
    }
}