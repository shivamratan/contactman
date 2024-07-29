package com.ratanapps.contactman.controller;

import com.ratanapps.contactman.entity.User;
import com.ratanapps.contactman.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    @ResponseBody
    public String testMe() {
        return "Contact Manager working !!";
    }


    @GetMapping("/testdata")
    @ResponseBody
    public String testData() {

        User user = User.builder()
                .name("Shivam Ratan1")
                .email("shivam1@ratan.com")
                .build();

        userRepository.save(user);

        return "Query ran Successfully!";
    }



}
