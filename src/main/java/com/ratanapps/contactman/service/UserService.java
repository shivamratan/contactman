package com.ratanapps.contactman.service;

import com.ratanapps.contactman.entity.User;
import com.ratanapps.contactman.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User getUserByUserEmail(String email) {
        return userRepository.getUserByUserName(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


}
