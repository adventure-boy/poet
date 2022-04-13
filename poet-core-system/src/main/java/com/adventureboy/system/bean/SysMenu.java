package com.adventureboy.system.bean;

import lombok.Data;

import java.sql.Date;

@Data
public class SysMenu {
    private String id;

    private String username;

    private String realname;

    private Date birthday;

    private Boolean sex;

    private String email;

    private String phone;

    private Integer status;

    private String roleName;

    private String roleCode;

    private String description;

    private String parentId;

    private String menuName;

    private String url;

    private String component;

    private String componentName;
}
