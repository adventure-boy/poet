package com.adventureboy.system.config;

import com.adventureboy.system.service.impl.PoetAuthenticationProvider;
import com.adventureboy.system.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;
import java.time.YearMonth;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login/*");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .anyRequest()
//                .permitAll();
//    }
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/vc.jpg").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .successHandler((req, resp, auth) -> {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString("success"));
                out.flush();
                out.close();
            })
            .failureHandler((req, resp, e) -> {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString("failure"));
                out.flush();
                out.close();
            })
            .permitAll()
            .and()
            .csrf().disable();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PoetAuthenticationProvider poetAuthenticationProvider() {
        PoetAuthenticationProvider provider = new PoetAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = new UserDetailsServiceImpl();
        return userDetailsService;
    }
}
