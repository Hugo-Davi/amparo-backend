package com.tatu.mulher.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("hello")
    static public String hello(@RequestParam(value="text", defaultValue="Nenhum valor inserido") String text){
        System.out.println(text);
        return String.format("The params passed to the API: " + text);
    }
}
