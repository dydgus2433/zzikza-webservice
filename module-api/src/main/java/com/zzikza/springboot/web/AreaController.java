package com.zzikza.springboot.web;

import com.zzikza.springboot.web.domain.area.AreaRepository;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.service.AreaService;
import com.zzikza.springboot.web.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AreaController {

    private final AreaService areaService;

    @GetMapping("/client/api/guguns")
    public List<AreaResponseDto> findBySido(@RequestParam("sido") String sido){
        return areaService.findBySido(sido);
    }

//    @PostMapping("/api/v1/posts")
//    public Long save(@RequestBody PostsSaveRequestDto requestDto){
//        return postsService.save(requestDto);
//    }
//
//    @PutMapping("/api/v1/posts/{id}")
//    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
//        return postsService.update(id, requestDto);
//    }
//
//    @GetMapping("/api/v1/posts/{id}")
//    public PostsResponseDto findById(@PathVariable Long id){
//        return postsService.findById(id);
//    }
//
//    @DeleteMapping("/api/v1/posts/{id}")
//    public Long delete(@PathVariable Long id){
//        postsService.delete(id);
//        return id;
//    }
}
