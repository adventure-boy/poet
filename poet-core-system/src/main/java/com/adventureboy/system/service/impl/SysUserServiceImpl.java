package com.adventureboy.system.service.impl;

import com.adventureboy.constant.CommonConstant;
import com.adventureboy.system.bean.SysUser;
import com.adventureboy.system.mapper.SysUserMapper;
import com.adventureboy.system.service.SysUserService;
import com.adventureboy.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.spi.LoginModule;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser selectByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public Result<?> checkAccount(SysUser sysUser) {
        Result<?> result = new Result<Object>();
        if (sysUser == null) {
            return result.error500("该用户名不存在");
        }
        //如果账户被冻结
        if (CommonConstant.STATUS_DISABLE == sysUser.getStatus()) {
            return result.error500("用户被冻结");
        }
        //账号正常
        return result;
    }

    @Override
    public Result<?> checkPassword(SysUser sysUser, LoginModule loginModule) {
//        if ()
        return null;
    }
}
