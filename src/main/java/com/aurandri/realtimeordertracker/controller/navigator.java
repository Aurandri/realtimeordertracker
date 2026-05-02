package com.aurandri.realtimeordertracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class navigator {
    @RequestMapping("/")
    public String index() {
        return "playground.html";
    }
}
