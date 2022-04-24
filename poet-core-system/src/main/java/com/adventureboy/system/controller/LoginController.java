package com.adventureboy.system.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.adventureboy.constant.CommonConstant;
import com.adventureboy.system.bean.SysLoginModel;
import com.adventureboy.system.bean.SysPermission;
import com.adventureboy.system.bean.SysUser;
import com.adventureboy.system.service.SysPermissionService;
import com.adventureboy.system.service.SysUserService;
import com.adventureboy.utils.RedisUtil;
import com.adventureboy.vo.Result;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system")
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

//    @PostMapping("/login")
//    public Result<JSONObject> login(@RequestBody SysLoginModel loginModel) {
//        Result<JSONObject> result = new Result<>();
//        String username = loginModel.getUsername();
//        String verificationCode = loginModel.getVerificationCode();//验证码
//        if (verificationCode == null) {
//            Result<JSONObject> error500 = result.error500("验证码错误");
//            return error500;
//        }
//        //如果redis中可以找到验证码
//        if (redisUtil.get(verificationCode) != null) {
//            //验证用户正确性:存在,是否冻结
//            SysUser sysUser = sysUserService.selectByUsername(username);
//            Result checkAccount = sysUserService.checkAccount(sysUser);
//            //如果查不到,或者冻结,直接返回
//            if (!checkAccount.getIsSuccess()) {
//                return checkAccount;
//            }
//            //验证密码正确性
//
//            return null;
//        } else {
//            Result<JSONObject> error500 = result.error500("验证码错误");
//
//            return error500;
//        }
//
//    }


    @GetMapping("/getMenu")
    @JsonIgnoreProperties({"permissionId","parentId"})
    public Result<List<SysPermission>> getMenu(String userId) {
        logger.info("userId: "+userId+"  正在获取菜单及路由");
        Result<List<SysPermission>> result = new Result<>();
        List<SysPermission> sysPermissions = sysPermissionService.selectSysPermissionsByUserId(userId);
        logger.info(sysPermissions.toString());
        result.success200(sysPermissions);
        return result;
    }

    @GetMapping("/getComponents")
    public Result<List<SysPermission>> getComponents() {
        Result<List<SysPermission>> result = new Result<>();
        List<SysPermission> sysPermissions = sysPermissionService.selectComponentsByUserId("288f00cc-1a8a-4196-a323-839491275b73");
        result.success200(sysPermissions);
        return result;
    }
}