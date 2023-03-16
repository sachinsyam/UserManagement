package com.bct.week6.UserManagement.config;

import com.bct.week6.UserManagement.service.UserInfoProviderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//to enable method level roles
@EnableMethodSecurity
public class SecurityConfig {
    //CONFIGURING ACCESS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("/app/welcome","/app/add").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/app/**")
                .authenticated().and().formLogin().and().build();
    }

    //CONFIGURING USERS
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//        UserDetails admin = User.withUsername("aa")
//                .password(encoder.encode("Mpik|@27c("))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("sachin")
//                .password(encoder.encode("Mpik|@27c("))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user);

        return new UserInfoProviderService();
    }

    //password encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
