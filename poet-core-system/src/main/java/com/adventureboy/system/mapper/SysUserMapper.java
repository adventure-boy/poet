package com.adventureboy.system.mapper;

import com.adventureboy.system.bean.SysLoginModel;
import com.adventureboy.system.bean.SysUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByUsername(String username);

    @Select("select username,password from sys_user where username = #{password}")
    SysLoginModel selectPasswordByUsername(String password);

}