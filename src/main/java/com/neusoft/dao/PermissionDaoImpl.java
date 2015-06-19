package com.neusoft.dao;


import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.neusoft.entity.Permission;

/**
 * @author Neusoft
 */
@Repository
public class PermissionDaoImpl implements PermissionDao {

	@Resource
	private SessionFactory sessionFactory;
	
	private final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
    public Permission createPermission(final Permission permission) {
        final String sql = "insert into sys_permissions(permission, description, available) values(:permission,:description,:available)";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setString("permission", permission.getPermission());
        query.setString("description", permission.getDescription());
        query.setBoolean("available", permission.getAvailable());
        query.executeUpdate();
		return permission;
    }
    @Override
    public void deletePermission(Long permissionId) {
        //首先把与permission关联的相关表的数据删掉 角色权限表
        String sql = "delete from sys_roles_permissions where permission_id=:permission_id";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setLong("permission_id", permissionId);
        query.executeUpdate();
        //权限表
        sql = "delete from sys_permissions where id=?";
        SQLQuery query2=getCurrentSession().createSQLQuery(sql);
        query2.setLong("permission_id", permissionId);
        query2.executeUpdate();
    }

}
