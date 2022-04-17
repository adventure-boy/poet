package com.adventureboy.test;

import cn.hutool.core.util.ObjectUtil;
import com.adventureboy.system.bean.SysLoginModel;
import com.adventureboy.system.bean.SysPermission;
import com.adventureboy.system.bean.SysUser;
import com.adventureboy.system.mapper.SysPermissionMapper;
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
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
@Component
public class Test01 {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Test
    public void test01() {
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

    @Test
    public void test07() {
        List<SysPermission> sysPermissions = sysPermissionMapper.selectSysPermissionsByUserId("7e06d237-87af-497c-87f0-65ec8bcd200c");
        List<SysPermission> collect = sysPermissions.stream().filter(sysPer -> !ObjectUtil.isNotEmpty(sysPer.getParentId())).collect(Collectors.toList());
        List<SysPermission> sysPermissionResult = getSysPermissionResult(sysPermissions, collect);
        System.out.println(sysPermissionResult);

    }

    @Test
    public void test08() {
        List<SysPermission> sysPermissions = sysPermissionMapper.selectSysPermissionsByUserId("7e06d237-87af-497c-87f0-65ec8bcd200c");
        List<SysPermission> collect = sysPermissions.stream().filter(sysPer -> !ObjectUtil.isNotEmpty(sysPer.getParentId())).collect(Collectors.toList());
        ArrayList<SysPermission> Permissions = new ArrayList<>();

        for (SysPermission sysPermission : collect) {
            SysPermission sysPermissionResults = getSysPermissionResults(sysPermissions, sysPermission);
            Permissions.add(sysPermissionResults);
        }
        System.out.println(Permissions);

    }
    /**
     * @param sysPermissionList 此用户所有的菜单
     * @param permissionList    父级菜单
     * @return 递归出口(终止递归的条件) ParentId为空
     * 递归表达式(规律)
     */
    public List<SysPermission> getSysPermissionResult(List<SysPermission> sysPermissionList, List<SysPermission> permissionList) {
        for (int i = 0; i < permissionList.size(); i++) {
            List<SysPermission> children = new ArrayList<>();
            for (int j = 0; j < sysPermissionList.size(); j++) {
                if (permissionList.get(i).getPermissionId().equals(sysPermissionList.get(j).getParentId())) {
                    children.add(sysPermissionList.get(j));
                }
            }
            permissionList.get(i).setChildren(children);
        }
        return permissionList;
    }

    /**
     * @param sysPermissionList 此用户所有的菜单
     * @param permission    父级菜单
     * @return 递归出口(终止递归的条件) 无子级菜单
     * 递归表达式(规律) 传入 用户总菜单和父级菜单
     */
    public SysPermission getSysPermissionResults(List<SysPermission> sysPermissionList, SysPermission permission) {
        List<SysPermission> children = getChildren(sysPermissionList, permission);
        permission.setChildren(children);
        for (SysPermission child : children) {
            if (hasChildren(sysPermissionList,child)) {
                getSysPermissionResults(sysPermissionList, child);
            }
            return permission;
        }
        return permission;
    }

    /**
     * 获取子菜单列表
     * @param sysPermissionList 此用户所有的菜单
     * @param permission    父级菜单
     * @return 子菜单列表
     *
     */
    public List<SysPermission> getChildren(List<SysPermission> sysPermissionList, SysPermission permission) {
        ArrayList<SysPermission> sysPermissions = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissionList) {
            if (permission.getPermissionId().equals(sysPermission.getParentId())) {
                sysPermissions.add(sysPermission);
            }
        }
        return sysPermissions;
    }

    /**
     * 判断此父菜单是否有子菜单
     * @param sysPermissionList
     * @param sysPermission
     * @return
     */
    public boolean hasChildren(List<SysPermission> sysPermissionList, SysPermission sysPermission) {
        for (SysPermission permission : sysPermissionList) {
            if (sysPermission.getParentId().equals(permission.getPermissionId())) {
                return true;
            }
        }
        return false;
    }


}
