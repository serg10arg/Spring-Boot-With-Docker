package com.mycompany.applicationone.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GrettingsController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the world of Containers!";
    }
}
