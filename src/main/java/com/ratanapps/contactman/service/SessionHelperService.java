package com.ratanapps.contactman.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelperService {

    @Bean
    public void removeMessageFromSession() {
        try {
            if (RequestContextHolder.getRequestAttributes() != null) {
                System.out.println("Removing message from the session");
                HttpSession httpSession = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
                httpSession.removeAttribute("message");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
