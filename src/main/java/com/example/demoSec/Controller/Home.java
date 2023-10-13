package com.example.demoSec.Controller;

import com.example.demoSec.Dto.AuthRequest;
import com.example.demoSec.Entity.UserInfo;
import com.example.demoSec.Repository.UserRepo;
import com.example.demoSec.Service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Home {

    @Autowired
    private UserRepo userRepo ;
    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;



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

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserInfo>> getUsers(){
        System.err.println("---------------------------------------------");
        System.out.println("The first JSON return to web in my whole life with SpringBoot");
        System.err.println("---------------------------------------------");
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userRepo.findAll());
    }
    @PostMapping("/jwt")
    public String getJwtToken(@RequestBody AuthRequest request){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        if(authentication.isAuthenticated()){
        return jwtService.generateToken(request.getUsername());
        }
        else{
            System.out.println("Invalid request for : \n username : "+request.getUsername()+
                    "password : "+request.getPassword());
            return "Invalid Request !!!" ;
        }
    }

}
