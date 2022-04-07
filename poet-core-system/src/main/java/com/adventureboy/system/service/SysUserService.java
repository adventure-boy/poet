package com.adventureboy.system.service;

import cn.hutool.json.JSONObject;
import com.adventureboy.system.bean.SysUser;
import com.adventureboy.vo.Result;

import javax.security.auth.spi.LoginModule;

public interface SysUserService {
    SysUser selectByUsername(String username);

    Result<?> checkAccount(SysUser sysUser);

    Result<?> checkPassword(SysUser sysUser, LoginModule loginModule);
}
