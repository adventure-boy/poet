package com.adventureboy.system.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermission {
    private String id;

    private String permissionId;

    private String parentId;

    private String name;

    private String path;

    private String component;

    private String componentName;

    private List<SysPermission> children;


}