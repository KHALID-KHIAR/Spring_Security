package com.example.demoSec.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping("")
    public String omee(){
        return "<h2>Hello, Its the Default Page</h2>" ;
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

    @GetMapping("client")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    public String client(){
        return "<h1>Client Page</h1>" ;
    }

}
