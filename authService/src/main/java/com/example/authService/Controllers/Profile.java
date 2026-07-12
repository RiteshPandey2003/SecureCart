package com.example.authService.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hide")
public class Profile {

    @GetMapping("/profile")
    public String ProfilePage(){
        return "profile Page";
    }
}
