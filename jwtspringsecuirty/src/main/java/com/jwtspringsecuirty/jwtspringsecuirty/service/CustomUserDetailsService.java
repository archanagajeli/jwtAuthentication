package com.jwtspringsecuirty.jwtspringsecuirty.service;

import com.jwtspringsecuirty.jwtspringsecuirty.entinties.UserEntity;
import com.jwtspringsecuirty.jwtspringsecuirty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      //load user form database
       UserEntity userEntity=userRepository.findByEmail(username).orElseThrow(()->new RuntimeException("user not found !!"));
        return userEntity;
    }

}
