package com.rehman.elearning.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/success")
public class TestController {

    @GetMapping
    public String success() {
        return "success";  // This will return the success.html file from static folder
    }
}
