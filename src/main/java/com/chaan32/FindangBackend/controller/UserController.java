package com.chaan32.FindangBackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/findang/user")
public class UserController {

    @ResponseBody
    @GetMapping
    public String user() {
        log.info("[General] :user controller");
        return "user";
    }
}
