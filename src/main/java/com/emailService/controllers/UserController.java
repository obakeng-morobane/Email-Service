package com.emailService.controllers;

import com.emailService.models.User;
import com.emailService.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    //just displaying all the users information in the table and the columns
    //used as demonstration
    @GetMapping()
    public  String userController(){ return "User access level";}
    //public List<User> getUsers(){
    //    return userService.getUsers(); }
}


