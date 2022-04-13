package com.adventureboy.test;

import com.adventureboy.system.bean.SysLoginModel;
import com.adventureboy.system.mapper.SysUserMapper;
import com.adventureboy.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

@SpringBootTest
@Component
public class Test01 {
    @Autowired
    SysUserMapper sysUserMapper;
    @Test
    public void test01() {
        SysLoginModel zhangsan = sysUserMapper.selectPasswordByUsername("zhangsan");
        System.out.println(zhangsan);
    }

    @Test
    public void test02() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");

        System.out.println(encode);
        boolean matches = encoder.matches("123456", "$2a$10$R4wEt7W9u.lo6ARU8H93fOjt3wh6S/5LdNo30Foc8PC/2aAOdytXC");
        System.out.println(matches);
    }

    @Test
    public void test03() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication =
                new TestingAuthenticationToken("username", "password", "ROLE_USER");
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    @Test
    public void test04() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    }

    @Autowired
    RedisUtil redisUtil;
    @Test
    public void test06() {
        String ymex = redisUtil.get("ymex");
        System.out.println(ymex);
    }

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }

}
