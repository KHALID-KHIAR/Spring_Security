package com.example.demoSec.Config;

import com.example.demoSec.Service.UserInfoDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.withUsername("khalid")
//                .password(encoder.encode("pwd")).roles("ADMIN").build();
//        UserDetails client = User.withUsername("bmc").password(encoder.encode("pwd"))
//                .roles("ADMIN","USER","CLIENT").build();
//    return new InMemoryUserDetailsManager(admin,client);
        return new UserInfoDetailsService();
    }

    //Authorisation
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable().authorizeHttpRequests()
            .requestMatchers("/","/addUser").permitAll()
            .and().formLogin().loginPage("/login").permitAll()
                                        // to custome the default login page of sprngSecurity,
                                        // and dont forget to add the getMapping endPoint to the "/login"
                                        // "/login" has two methods : GET and POST to the same url
            .and()
            .authorizeHttpRequests().requestMatchers("/about","/hello","/user","/admin")
            .authenticated().and().formLogin()
            .and().build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
