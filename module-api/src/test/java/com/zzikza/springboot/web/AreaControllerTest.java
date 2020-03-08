package com.zzikza.springboot.web;

import com.zzikza.springboot.web.domain.area.Area;
import com.zzikza.springboot.web.domain.area.AreaRepository;
import com.zzikza.springboot.web.domain.posts.Posts;
import com.zzikza.springboot.web.domain.posts.PostsRepository;
import com.zzikza.springboot.web.dto.AreaRequestDto;
import com.zzikza.springboot.web.dto.AreaResponseDto;
import com.zzikza.springboot.web.dto.PostsSaveRequestDto;
import com.zzikza.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AreaControllerTest {
    ///client/api/guguns

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AreaRepository areaRepository;

    @Test
    public void getArea() {

        //given
        String sido = "서울";
        String gugun = "종로구";
        AreaRequestDto areaRequestDto = AreaRequestDto.builder().sido(sido).gugun(gugun).build();

        String url = "http://localhost:" + port + "/client/api/guguns";

        //when
        ResponseEntity<AreaResponseDto> responseEntity = restTemplate.getForEntity(url, AreaResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Area> all = areaRepository.findAll();
        assertThat(all.get(0).getSido()).isEqualTo(sido);
        assertThat(all.get(0).getGugun()).isEqualTo(gugun);
    }

}