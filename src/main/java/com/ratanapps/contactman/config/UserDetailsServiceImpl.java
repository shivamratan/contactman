package com.ratanapps.contactman.config;

import com.ratanapps.contactman.entity.User;
import com.ratanapps.contactman.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.getUserByUserName(username);

        if ( user == null) {
            throw new UsernameNotFoundException("Could not found user !!");
        } else {
            System.out.println("User successfully Found ---->"+user.getEmail());
        }

        UserDetailsImpl userDetail = new UserDetailsImpl(user);
        return userDetail;
    }
}
