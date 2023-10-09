package com.example.demoSec.Controller;

import com.example.demoSec.Entity.UserInfo;
import com.example.demoSec.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @Autowired
    private UserRepo userRepo ;
    @Autowired
    private PasswordEncoder passwordEncoder ;

    @GetMapping("/login")
    public String omee(){
        return "<h2>Hello, Its the LOGIN Page</h2>" ;
    }

    @PostMapping("addUser")
    public String addUser(@RequestBody UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword() ));
        userRepo.save(userInfo);
        return "User Added Successfully";
    }

    @GetMapping("hello")
    public String Hello(){
        return "<h1>Hello, Its the Home Page</h1>" ;
    }
    @GetMapping("about")
    public String about(){
        return "About Page" ;
    }

    @GetMapping("admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin(){
        return "<h1>Admin Page</h1>" ;
    }
    @GetMapping("user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String user(){
        return "<h1>User Page</h1>" ;
    }


}
