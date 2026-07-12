package com.sabina.jobtracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController
{
    @GetMapping("/api/hello")
    public String hello()
    {
        return "hello from job tracker API";
    }
}
