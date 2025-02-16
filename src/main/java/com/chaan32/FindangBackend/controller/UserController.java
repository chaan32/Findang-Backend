package com.chaan32.FindangBackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class UserController {
    @ResponseBody
    @GetMapping("/user")
    public String user() {
        log.info("user controller");
        return "user";
    }
}
