package com.zzikza.springboot.web;

import com.zzikza.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hell(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }

    @GetMapping("/hello/dto1")
    public List<HelloResponseDto> helloDto1(@RequestParam("name") String name, @RequestParam("amount") int amount){
        List<HelloResponseDto> dtos = new ArrayList<>();
        dtos.add(new HelloResponseDto(name, amount));
        dtos.add(new HelloResponseDto(name, amount));
        return dtos;
    }
}
