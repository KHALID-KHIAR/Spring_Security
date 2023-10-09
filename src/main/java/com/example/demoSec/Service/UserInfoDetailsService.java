package com.example.demoSec.Service;

import com.example.demoSec.Config.UserInfoToUserDetails;
import com.example.demoSec.Entity.UserInfo;
import com.example.demoSec.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static ch.qos.logback.core.joran.spi.ConsoleTarget.findByName;

@Component
public class UserInfoDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo ;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userRepo.findByName(username);
           return  userInfo.map(UserInfoToUserDetails::new)
                    .orElseThrow(()->new UsernameNotFoundException("User Not Found !!")) ;

    }
}
