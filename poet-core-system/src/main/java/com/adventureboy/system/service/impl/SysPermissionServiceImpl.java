package com.adventureboy.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.adventureboy.system.bean.SysPermission;
import com.adventureboy.system.mapper.SysPermissionMapper;
import com.adventureboy.system.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    SysPermissionMapper sysPermissionMapper;
    @Override
    public List<SysPermission> selectSysPermissionsByUserId(String userId) {
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectSysPermissionsByUserId(userId);
        List<SysPermission> fatherPermission = sysPermissionList.stream().filter(sysPer -> !ObjectUtil.isNotEmpty(sysPer.getParentId())).collect(Collectors.toList());
        ArrayList<SysPermission> Permissions = new ArrayList<>();
        for (SysPermission sysPermission : fatherPermission) {
            SysPermission sysPermissionResults = getSysPermissionResults(sysPermissionList, sysPermission);
            Permissions.add(sysPermissionResults);
        }

        return fatherPermission;
    }

    /**
     * @param sysPermissionList 此用户所有的菜单
     * @param permission    父级菜单
     * @return 递归出口(终止递归的条件) 无子级菜单
     * 递归表达式(规律) 传入 用户总菜单和父级菜单 并且把子菜单放入父菜单下面
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
