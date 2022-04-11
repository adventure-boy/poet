package com.adventureboy.system.service.impl;

import com.adventureboy.system.bean.SysLoginModel;
import com.adventureboy.system.bean.SysUser;
import com.adventureboy.system.mapper.SysUserMapper;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysLoginModel sysLoginModel = sysUserMapper.selectPasswordByUsername(username);

        if (Objects.isNull(sysLoginModel)) {
            throw new RuntimeException("用户名或者密码错误");
        }

        return sysLoginModel;
    }
}
