package com.duplicate.requests.avoid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {

    @GetMapping
    public String index(Model model) {
        log.info("Start Main Page");
        return "main";
    }
}
