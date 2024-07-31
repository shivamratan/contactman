package com.ratanapps.contactman.controller;

import com.ratanapps.contactman.entity.User;
import com.ratanapps.contactman.model.Message;
import com.ratanapps.contactman.repo.UserRepository;
import com.ratanapps.contactman.util.UserRole;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home - ContactMan");
        model.addAttribute("message", "This is public user");
        return "home";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(Model model) {
        model.addAttribute("title", "About - ContactMan");
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Register - ContactMan");
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                           @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                           Model model,
                           HttpSession httpSession) {

        try {

            if (bindingResult.hasErrors()) {
                System.out.println("ERROR "+bindingResult);
                model.addAttribute("user", user);
                return "signup";
            }

            String errorMessage = "You haven't agree the terms and conditions";
            if (!agreement) {
                System.out.println(errorMessage);
                throw new Exception(errorMessage);
            }

            System.out.println("Agreement " + agreement);
            System.out.println("USER " + user.toString());

            user.setRole(UserRole.USER_GENERAL.getDbValue());
            user.setEnabled(true);
            user.setImageUrl("user.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));



            User newUser = this.userRepository.save(user);
            model.addAttribute("user", new User());
            httpSession.setAttribute("message", new Message("Successfully registered !", "alert-success"));
            User user23 = userRepository.getUserByUserName(user.getEmail());
            System.out.println("Saved User ***********"+user23);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user",user);
            httpSession.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger"));
            return "signup";
        }

        return "signup";
    }

    @GetMapping("/signin")
    public String customLogin(Model model) {
        model.addAttribute("title", "Login - ContactMan");
        return "login";
    }

    @GetMapping("/testthyme")
    public String testThyme(Model model) {
        model.addAttribute("message", "This is a test message");
        return "test";
    }




}
