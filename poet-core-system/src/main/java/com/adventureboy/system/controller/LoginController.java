package com.adventureboy.system.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import com.adventureboy.system.bean.SysLoginModel;
import com.adventureboy.system.bean.SysPermission;
import com.adventureboy.system.bean.SysUser;
import com.adventureboy.system.service.SysPermissionService;
import com.adventureboy.system.service.SysUserService;
import com.adventureboy.utils.RedisUtil;
import com.adventureboy.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    SysPermissionService sysPermissionService;

    @GetMapping("/getVerificationCode")
    public Result<String> getVerificationCode() {
        Result<String> result = new Result<>();
        try {
            //生成4位由数字和字母组成的字符串
            String verificationCode = RandomUtil.randomString(4);
            logger.info("验证码为:" + verificationCode);
            redisUtil.set(verificationCode, verificationCode, 300);
            result.success200(verificationCode);
            return result;
        } catch (Exception e) {
            result.error500("错误原因为" + e.getMessage());
            return result;
        }
    }

    @PostMapping("/confirm")
    public Result<JSONObject> login(@RequestBody SysLoginModel loginModel) {
        Result<JSONObject> result = new Result<>();
        String username = loginModel.getUsername();
        String password = loginModel.getPassword();
        String verificationCode = loginModel.getVerificationCode();//验证码
        if (verificationCode == null) {
            Result<JSONObject> error500 = result.error500("验证码错误");
            return error500;
        }
        //如果redis中可以找到验证码
        if (redisUtil.get(verificationCode) != null) {
            //验证用户正确性:存在,是否冻结
            SysUser sysUser = sysUserService.selectByUsername(username);
            Result checkAccount = sysUserService.checkAccount(sysUser);
            //如果查不到,或者冻结,直接返回
            if (!checkAccount.getIsSuccess()) {
                return checkAccount;
            }
            //验证密码正确性

            return null;
        } else {
            Result<JSONObject> error500 = result.error500("验证码错误");

            return error500;
        }

    }


    @GetMapping("/getMenu")
    public JSONObject getMenu() {
        JSONObject jsonObject = new JSONObject();
        SysUser sysUser = (SysUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SysPermission> sysPermissions = sysPermissionService.selectSysPermissionsByUserId(sysUser.getUserId());
        return null;

    }
}
