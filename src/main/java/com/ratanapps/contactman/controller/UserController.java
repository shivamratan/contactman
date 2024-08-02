package com.ratanapps.contactman.controller;

import com.ratanapps.contactman.entity.Contact;
import com.ratanapps.contactman.entity.User;
import com.ratanapps.contactman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    // Adding common data for every handler
    @ModelAttribute
    public void addCommonData(Model m, Principal principal) {

        String userName = principal.getName();
        User user = userService.getUserByUserEmail(userName);

        m.addAttribute("user", user);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String dashboard(Model model, Principal principal) {

        model.addAttribute("message", "Sample message");

        return "general/user_dashboard";
    }

    // open add Contact form handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title","Add Contact - ContactMan");
        model.addAttribute("contact", new Contact());
        return "general/add_contact_form";
    }


}
