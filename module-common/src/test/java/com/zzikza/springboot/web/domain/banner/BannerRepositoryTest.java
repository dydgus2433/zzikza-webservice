package com.zzikza.springboot.web.domain.banner;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BannerRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    BannerRepository bannerRepository;

    @Before
    public void 비포() {
//        bannerRepository.deleteAll();
    }


    @Test
    public void 배너생성() {

        //given
        String title = "banner1";

        bannerRepository.save(Banner.builder().title(title).build());
        //when
        List<Banner> bannerList = bannerRepository.findAll();

        //then
        Banner banner = bannerList.get(0);
        assertThat(banner.getTitle()).isEqualTo(title);

    }

    @Test
    @Transactional
    public void 배너_파일등록() {

        //given
        String title = "banner1";
        Banner banner = Banner.builder().title(title).build();
        em.persist(banner);

        BannerFile bannerFile = BannerFile.builder().fileName("파일명1").filePath("/test/").build();
        banner.addBannerFile(bannerFile);

        BannerMobileFile bannerMobileFile = BannerMobileFile.builder().fileName("파일명2").filePath("/test/").build();
        banner.addBannerMobileFile(bannerMobileFile);

        em.flush();
        em.clear();

        //when
        List<Banner> bannerList = bannerRepository.findAll();

        //then
        Banner expectedBanner = bannerList.get(0);
        assertThat(expectedBanner.getBannerFiles().get(0).getFileName()).isEqualTo("파일명1");
        assertThat(expectedBanner.getBannerMobileFiles().get(0).getFileName()).isEqualTo("파일명2");
    }

    @Test
    @Transactional
    public void 배너_파일삭제() {

        //given
        String title = "banner1";
        Banner banner = Banner.builder().title(title).build();
        em.persist(banner);

        BannerFile bannerFile = BannerFile.builder().fileName("파일명1").filePath("/test/").build();
        banner.addBannerFile(bannerFile);

        BannerMobileFile bannerMobileFile = BannerMobileFile.builder().fileName("파일명2").filePath("/test/").build();
        em.persist(bannerMobileFile);
        bannerMobileFile.setRegistedId("작자미상");
        banner.addBannerMobileFile(bannerMobileFile);



        Banner savedBanner = bannerRepository.findById(banner.getId()).orElseThrow(()->new IllegalArgumentException("배너 찾을 수 없어요"));
        em.persist(savedBanner);

        savedBanner.deleteBannerFile(bannerFile);

        bannerMobileFile.setRegistedId("작자미상1");
        em.flush();
        em.clear();

        //when
        List<Banner> bannerList = bannerRepository.findAll();


        //then
        Banner expectedBanner = bannerList.get(0);
        assertThat(expectedBanner.getBannerFiles().size()).isEqualTo(0);
        assertThat(expectedBanner.getBannerMobileFiles().get(0).getFileName()).isEqualTo("파일명2");
    }
}