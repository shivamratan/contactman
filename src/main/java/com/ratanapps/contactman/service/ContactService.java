package com.ratanapps.contactman.service;

import com.ratanapps.contactman.entity.Contact;
import com.ratanapps.contactman.entity.User;
import com.ratanapps.contactman.repo.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public List<Contact> getContactByUser(User user) {
        return contactRepository.getContactByUser(user);
    }

    public Page<Contact> getContactByUserId(Long userId, Integer curpage, Integer countPerPage) {

        Pageable pageable = PageRequest.of(curpage, countPerPage);
        return contactRepository.getContactByUserId(userId, pageable);
    }

}
