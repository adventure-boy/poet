package com.adventureboy.system.service.impl;

import com.adventureboy.system.bean.SysPermission;
import com.adventureboy.system.mapper.SysPermissionMapper;
import com.adventureboy.system.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    SysPermissionMapper sysPermissionMapper;
    @Override
    public List<SysPermission> selectSysPermissionsByUserId(String userId) {
        return sysPermissionMapper.selectSysPermissionsByUserId(userId);
    }
}
