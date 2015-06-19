package com.neusoft.dao;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.neusoft.entity.Role;

/**
 * @author wtaisi
 */
@Repository
public class RoleDaoImpl implements RoleDao {

	@Resource
	private SessionFactory sessionFactory;
	
	private final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

    public Role createRole(final Role Role) {
        final String sql = "insert into sys_roles(role, description, available) values(:role,:description,:available)";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setString("permission", Role.getRole());
        query.setString("description", Role.getDescription());
        query.setBoolean("available", Role.getAvailable());
        query.executeUpdate();
		return Role;
    }

    public void deleteRole(Long roleId) {
        //首先把和role关联的相关表数据删掉
        String sql = "delete from sys_users_roles where role_id=:role_id";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setLong("role_id", roleId);
        query.executeUpdate();
        sql = "delete from sys_roles where id=?";
        SQLQuery query2=getCurrentSession().createSQLQuery(sql);
        query2.setLong("role_id", roleId);
        query2.executeUpdate();
    }

    @Override
    public void correlationPermissions(Long roleId, Long... permissionIds) {//一个角色对应多个权限，可变参数
        if(permissionIds == null || permissionIds.length == 0) {
            return;
        }
        String sql = "insert into sys_roles_permissions(role_id, permission_id) values(:role_id,:permission_id)";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        for(Long permissionId : permissionIds) {
            if(!exists(roleId, permissionId)) {
                query.setLong("role_id", roleId);
                query.setLong("permission_id", permissionId);
                query.executeUpdate();
            }
        }
    }


    @Override
    public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
        if(permissionIds == null || permissionIds.length == 0) {
            return;
        }
        String sql = "delete from sys_roles_permissions where role_id=:role_id and permission_id=:permission_id";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        for(Long permissionId : permissionIds) {
            if(exists(roleId, permissionId)) {
                query.setLong("role_id", roleId);
                query.setLong("permission_id", permissionId);
                query.executeUpdate();
            }
        }
    }

    private boolean exists(Long roleId, Long permissionId) {
    	String sql = "select count(1) from sys_roles_permissions where role_id=:role_id and permission_id=:permission_id";
        SQLQuery query2=getCurrentSession().createSQLQuery(sql);
        query2.setLong("role_id", roleId);
        query2.setLong("permission_id", permissionId);
        int var=query2.executeUpdate();
        return var != 0;//如果大于0,就可以认为有1条或者多条记录.
    }

}
