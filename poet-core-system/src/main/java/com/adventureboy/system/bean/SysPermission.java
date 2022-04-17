package com.adventureboy.system.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
public class SysPermission {
    private String id;

    private String permissionId;

    private String parentId;

    private String name;

    private String url;

    private String component;

    private String componentName;

    private List<SysPermission> children;


}