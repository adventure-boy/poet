package com.adventureboy.system.config;

import com.adventureboy.system.bean.SysUser;
import com.adventureboy.system.service.impl.PoetAuthenticationProvider;
import com.adventureboy.system.service.impl.UserDetailsServiceImpl;
import com.adventureboy.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Map;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login/*","/beans");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll()
                .and()
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

    }
//@Override
//protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests()
//            .antMatchers("/vc.jpg").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin()
//            .successHandler((req, resp, auth) -> {
//                resp.setContentType("application/json;charset=utf-8");
//                PrintWriter out = resp.getWriter();
//                out.write(new ObjectMapper().writeValueAsString("success"));
//                out.flush();
//                out.close();
//            })
//            .failureHandler((req, resp, e) -> {
//                resp.setContentType("application/json;charset=utf-8");
//                PrintWriter out = resp.getWriter();
//                out.write(new ObjectMapper().writeValueAsString("failure"));
//                out.flush();
//                out.close();
//            })
//            .permitAll()
//            .and()
//            .csrf().disable();
//}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
    public PoetAuthenticationProvider poetAuthenticationProvider() {
        PoetAuthenticationProvider provider = new PoetAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationManager(super.authenticationManagerBean());
        loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                SysUser sysUser = (SysUser) authentication.getPrincipal();
                Result<SysUser> result = new Result<>();
                response.setContentType("application/json;charset=utf-8");
                sysUser.setPassword(null);
                result.success200(sysUser);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
                //关闭jackson中日期转为时间戳功能,
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                mapper.setDateFormat(sdf);
                String s = mapper.writeValueAsString(result);
                PrintWriter writer = response.getWriter();
                writer.write(s);
                writer.flush();
                writer.close();
            }
        });
        loginFilter.setAuthenticationFailureHandler((request,response,exception)->{
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            Result<String> result = new Result<>();
            result.error500(exception.getMessage());
            String s = new ObjectMapper().writeValueAsString(result);
            writer.write(s);
            writer.flush();
            writer.close();
        });
        return loginFilter;
    }
//    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = new UserDetailsServiceImpl();
        return userDetailsService;
    }
}
