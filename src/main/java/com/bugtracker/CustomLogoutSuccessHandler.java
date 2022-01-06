package com.bugtracker;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
 
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
         
        //CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();
        //String username = userDetails.getUsername();
         
        //Customer customer = customerService.getCustomerByEmail(username);
         
        // process logics with customer
         
        super.onLogoutSuccess(request, response, authentication);
    }  
}