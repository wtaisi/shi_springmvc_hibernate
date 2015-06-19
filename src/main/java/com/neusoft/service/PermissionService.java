package com.neusoft.service;

import com.neusoft.entity.Permission;

/**
 * 
 * @author wtaisi
 *
 */
public interface PermissionService {//实现基本的创建/删除权限。
    public Permission createPermission(Permission permission);
    public void deletePermission(Long permissionId);
}
