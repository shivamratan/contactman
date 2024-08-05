package com.ratanapps.contactman.controller;

import com.ratanapps.contactman.entity.Contact;
import com.ratanapps.contactman.entity.User;
import com.ratanapps.contactman.model.Message;
import com.ratanapps.contactman.service.ContactService;
import com.ratanapps.contactman.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ratanapps.contactman.util.ContactManConst;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;


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

    @RequestMapping(value = "/process-contact", method = RequestMethod.POST)
    public String processContactForm(@ModelAttribute Contact contact,
                                     @RequestParam("profileImage") MultipartFile file,
                                     Principal principal,
                                     HttpSession session
                                     ) {

        try {
            String userName = principal.getName();
            User user = userService.getUserByUserEmail(userName);

            // Processing and uploading file..
            if (file.isEmpty()) {
                //if the file is empty then try our message
                System.out.println("File is Empty");
                contact.setImage("contact.png");
            } else {
                // upload the file to the folder and update the name to contact
                contact.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            }


            contact.setUser(user);
            user.getContacts().add(contact);
            userService.saveUser(user);


            List<Contact> contactList = contactService.getContactByUser(user);
            for (Contact c : contactList)
                System.out.println("Contact Data " + c.printObj());

            session.setAttribute("message", new Message("Your contact successfully added !","alert-success"));
        } catch (Exception e) {
            System.out.println("ERROR "+e.getMessage());
            e.printStackTrace();
            session.setAttribute("message", new Message("Something went wrong, Try again !!","alert-danger"));

        }

        return "general/add_contact_form";
    }


    // Per page contact n= 10
    // current page = 0
    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal) {
        model.addAttribute("title", "Show Contacts - ContactMan");

        String userName = principal.getName();
        User user = userService.getUserByUserEmail(userName);

        Page<Contact> contactList = contactService.getContactByUserId(
                user.getId(),
                page,
                ContactManConst.SHOW_CONTACT_PAGE_ITEM_COUNT);

        model.addAttribute("contacts", contactList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", contactList.getTotalPages());

        return "general/show_contacts";
    }

}
