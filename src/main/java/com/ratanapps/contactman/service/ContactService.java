package com.ratanapps.contactman.service;

import com.ratanapps.contactman.entity.Contact;
import com.ratanapps.contactman.entity.User;
import com.ratanapps.contactman.repo.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public List<Contact> getContactByUser(User user) {
        return contactRepository.getContactByUser(user);
    }

    public List<Contact> getContactByUserId(Long userId) {
        return contactRepository.getContactByUserId(userId);
    }

}
