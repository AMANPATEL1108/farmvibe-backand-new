package com.example.farmvibe_backand_new.api.controller.userController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome to User Panel!";
    }
}
