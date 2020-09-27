package com.sot.iexam.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class defaultPage {
    @RequestMapping("/")
    public String welComePage() {
        return "login";
    }
}
    