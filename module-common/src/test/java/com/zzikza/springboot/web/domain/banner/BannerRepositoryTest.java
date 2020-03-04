package com.zzikza.springboot.web.domain.banner;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BannerRepositoryTest {

    @Autowired
    BannerRepository bannerRepository;



    @Test
    public void 테이블시퀀스_테스트() {

        //given
        String title = "banner1";

        bannerRepository.save(Banner.builder().title(title).build());
        //when
        List<Banner> bannerList = bannerRepository.findAll();

        //then
        Banner banner = bannerList.get(0);
        assertThat(banner.getTitle()).isEqualTo(title);

    }

}