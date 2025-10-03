package com.msansar.ReportingAPI.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // Bu, tüm endpointlerin base path'i olacak
public class DenemeController {

    @GetMapping("/merhaba")
    public String merhaba() {
        return "Merhaba Mustafa! Spring Boot çalışıyor.";
    }
}
