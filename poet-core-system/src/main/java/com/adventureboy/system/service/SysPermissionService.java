package com.adventureboy.system.service;

import com.adventureboy.system.bean.SysPermission;

import java.util.List;

public interface SysPermissionService {
    List<SysPermission> selectSysPermissionsByUserId(String userId);

    List<SysPermission> selectComponentsByUserId(String userId);
}
