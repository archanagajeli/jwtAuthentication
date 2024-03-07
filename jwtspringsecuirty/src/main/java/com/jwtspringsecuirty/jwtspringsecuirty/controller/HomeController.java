package com.jwtspringsecuirty.jwtspringsecuirty.controller;

import com.jwtspringsecuirty.jwtspringsecuirty.entinties.UserEntity;
import com.jwtspringsecuirty.jwtspringsecuirty.helper.JWThelper;
import com.jwtspringsecuirty.jwtspringsecuirty.login.JwtRequest;
import com.jwtspringsecuirty.jwtspringsecuirty.login.JwtResponse;
import com.jwtspringsecuirty.jwtspringsecuirty.service.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
   private UserServices userServices;
    //http://localhost:8081/home/user
    @GetMapping("/user")
    public List<UserEntity> getUser(){
        System.out.println("getting user");
        return this.userServices.getUsers();
    }
    @GetMapping("/current-user")
    public String getLoggedUser(Principal principal){
        return principal.getName();
    }

    @RestController
    @RequestMapping("/auth")
    public static class AuthController {
        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private AuthenticationManager manager;
       @Autowired
        private UserServices userServices;

        @Autowired
        private JWThelper helper;

        private Logger logger = LoggerFactory.getLogger(AuthController.class);
        //this authController used to generate token


        @PostMapping("/login")
        public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

            this.doAuthenticate(request.getEmail(), request.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = this.helper.generateToken(userDetails);

            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        private void doAuthenticate(String email, String password) {

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
            try {
                manager.authenticate(authentication);


            } catch (BadCredentialsException e) {
                throw new BadCredentialsException(" Invalid Username or Password  !!");
            }

        }

        @ExceptionHandler(BadCredentialsException.class)
        public String exceptionHandler() {
            return "Credentials Invalid !!";
        }
        @PostMapping("/create-user")
        public UserEntity createUser(@RequestBody UserEntity userEntity){
            return userServices.createUser(userEntity);

        }


    }
}
