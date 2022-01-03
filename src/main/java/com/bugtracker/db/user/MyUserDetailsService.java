package com.bugtracker.db.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.javabrains.springsecurityjpa.UserRepository;
import io.javabrains.springsecurityjpa.models.MyUserDetails;
import io.javabrains.springsecurityjpa.models.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    	    	
        return user.map(MyUserDetails::new).get();
    }
}
