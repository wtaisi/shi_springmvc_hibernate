package com.neusoft.dao;

import com.neusoft.entity.Permission;


/**
 * @author Neusoft
 */
public interface PermissionDao {

    public Permission createPermission(Permission permission);

    public void deletePermission(Long permissionId);

}
