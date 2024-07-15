package org.lzq.nyy.mapper;

import org.lzq.nyy.domain.RolePermissions;

public interface RolePermissionsMapper {
    int deleteByPrimaryKey(Integer rolePermissionId);

    int insert(RolePermissions record);

    int insertSelective(RolePermissions record);

    RolePermissions selectByPrimaryKey(Integer rolePermissionId);

    int updateByPrimaryKeySelective(RolePermissions record);

    int updateByPrimaryKey(RolePermissions record);
}