package com.ratanapps.contactman.controller;

import com.ratanapps.contactman.entity.User;
import com.ratanapps.contactman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String dashboard(Model model, Principal principal) {


        String userName = principal.getName();
        System.out.println("USERNAME "+userName);

        User user = userService.getUserByUserEmail(userName);
        System.out.println("USER "+user);

        model.addAttribute("message", "Sample message");
        model.addAttribute("user", user);

        return "general/user_dashboard";
    }


}
