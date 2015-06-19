package com.neusoft.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.dao.PermissionDao;
import com.neusoft.dao.PermissionDaoImpl;
import com.neusoft.entity.Permission;


/**
 * 
 * @author wtaisi
 *
 */
@Transactional
@Service
public class PermissionServiceImpl implements PermissionService {
	@Resource
    private PermissionDao permissionDao;

    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }
}
