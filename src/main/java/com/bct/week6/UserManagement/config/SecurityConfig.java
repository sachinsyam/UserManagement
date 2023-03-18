package com.bct.week6.UserManagement.config;

import com.bct.week6.UserManagement.service.UserInfoProviderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//to enable method level roles
@EnableMethodSecurity
public class SecurityConfig {

    //CONFIGURING USERS
    @Bean
    public UserDetailsService userDetailsService(){
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

    //CONFIGURING ACCESS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/app/welcome","/app/add","/app/signup","/app/registerUser","/app/404")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/app/**")
                .authenticated().and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/app/home",true)
                .and()
                .build();
    }


    //password encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        //giving info about who is the user details service and password encoder
        //these info can be used to generate user details object and set it to authentication object.
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

}
