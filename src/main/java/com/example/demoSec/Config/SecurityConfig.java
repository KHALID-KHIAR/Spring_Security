package com.example.demoSec.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //Authentication
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        UserDetails admin = User.withUsername("khalid")
                .password(encoder.encode("pwd"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("walid")
                .password(encoder.encode("pwd"))
                .roles("USER").build();
        UserDetails client = User.withUsername("bmc").password(encoder.encode("pwd"))
                .roles("ADMIN","USER","CLIENT").build();
    return new InMemoryUserDetailsManager(admin,user,client);
    }

    //Authorisation
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable().authorizeHttpRequests()
            .requestMatchers("/").permitAll()
            .and()
            .authorizeHttpRequests().requestMatchers("/about","/hello","/user","/admin","/client")
            .authenticated().and().formLogin()
            .and().build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
