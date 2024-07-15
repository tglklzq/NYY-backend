package org.lzq.nyy.domain;

import lombok.Data;

@Data
public class RolePermissions {
    /**
    * 角色权限ID（唯一标识）

    */
    private Integer rolePermissionId;

    /**
    * 角色名称

    */
    private String roleName;

    /**
    * 权限名称

    */
    private String permissionName;
}