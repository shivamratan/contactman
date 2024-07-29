package com.ratanapps.contactman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/index")
    public String dashboard() {
        return "general/user_dashboard";
    }


}
