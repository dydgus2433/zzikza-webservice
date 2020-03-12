package com.zzikza.springboot.web.studio;


import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.dto.PageRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardViewControllerTest {

    @Autowired
    StudioRepository studioRepository;

    @Test
    public void pageTest(){
        int rows = 100;
        int nowPage = 2;
        int pagePerSize = 10; 
    }
    
    @Test
    public void 뷰페이지테스트(){
        int pegeableSize = 10;
        int page = 2;
        PageRequestDto requestDto = new PageRequestDto();
        requestDto.setPage(page);
        requestDto.setSize(pegeableSize);
//        requestDto.setDirection(Sort.Direction.DESC);
        Pageable pageable = requestDto.of();
        Page<Studio> paging = studioRepository.findAll(pageable);
        int totPage = 3;
        int totCount = 30;
        assertThat(totPage).isEqualTo(paging.getTotalPages());
        assertThat(totCount).isEqualTo(paging.getTotalElements());

        assertThat(paging.getTotalElements() > 0).isEqualTo(true);
        assertThat(paging.getPageable().getPageNumber() <= 0).isEqualTo(false);
        int nowPage = pageable.getPageNumber() ;
        assertThat(pegeableSize).isEqualTo(paging.getSize());

        int prevPage = nowPage - 1;
        int nextPage = nowPage + 1;

        int lastPageNo = (int) (((paging.getTotalElements() - 1) / paging.getSize()) + 1);
        if(lastPageNo > nowPage){
            nextPage = nowPage + 1;
        }
        int pagePerOnce = paging.getSize();
        assertThat(nowPage).isEqualTo(page); //현재 페이지
        assertThat(nextPage).isEqualTo(3); //다음 페이지
        assertThat(prevPage).isEqualTo(1); //이전 페이지
        assertThat(pagePerOnce).isEqualTo(pegeableSize);//페이징 넘버 개수 10
        assertThat(lastPageNo).isEqualTo(totPage);
        assertThat(pageable.getOffset()).isEqualTo(20);
        assertThat(pageable.getPageNumber()).isEqualTo(page);
    }
}