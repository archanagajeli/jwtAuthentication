package com.jwtspringsecuirty.jwtspringsecuirty.service;

import com.jwtspringsecuirty.jwtspringsecuirty.entinties.UserEntity;
import com.jwtspringsecuirty.jwtspringsecuirty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserServices {
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }
    public UserEntity createUser(UserEntity userEntity){
        userEntity.setUserId(UUID.randomUUID().toString());
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
       return userRepository.save(userEntity);
    }

}
