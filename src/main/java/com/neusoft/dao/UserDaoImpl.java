package com.neusoft.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.neusoft.entity.User;

/**
 * @author wtaisi
 */
@Repository
public class UserDaoImpl implements UserDao {

	@Resource
	private SessionFactory sessionFactory;
	
	private final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

    public User createUser(final User user) {
        final String sql = "insert into sys_users(username, password, salt, locked) values(:username, :password, :salt, :locked)";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setString("username", user.getUsername());
        query.setString("password", user.getPassword());
        query.setString("salt", user.getSalt());
        query.setBoolean("locked", user.getLocked());
        query.executeUpdate();
		return user;
    }

    public void updateUser(User user) {
        String sql = "update sys_users set username=:username, password=:password, salt=:salt, locked=:locked where id=:id";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setLong("id", user.getId());
        query.setString("username", user.getUsername());
        query.setString("password", user.getPassword());
        query.setString("salt", user.getSalt());
        query.setBoolean("locked", user.getLocked());
        query.executeUpdate();
    }

    public void deleteUser(Long userId) {
        String sql = "delete from sys_users where id=:id";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setLong("id", userId);
        query.executeUpdate();
    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        if(roleIds == null || roleIds.length == 0) {
            return;
        }
        String sql = "insert into sys_users_roles(user_id, role_id) values(?,?)";
        for(Long roleId : roleIds) {
            if(!exists(userId, roleId)) {
            	SQLQuery query=getCurrentSession().createSQLQuery(sql);
                query.setLong("id", userId);
                query.executeUpdate();
            }
        }
    }

    @Override
    public void uncorrelationRoles(Long userId, Long... roleIds) {
        if(roleIds == null || roleIds.length == 0) {
            return;
        }
        String sql = "delete from sys_users_roles where user_id=:user_id and role_id=:role_id";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        for(Long roleId : roleIds) {
            if(exists(userId, roleId)) {
            	query.setLong("user_id", userId);
                query.setLong("role_id", roleId);
                query.executeUpdate();
            }
        }
    }

    private boolean exists(Long userId, Long roleId) {
        String sql = "select count(1) from sys_users_roles where user_id=:user_id and role_id=:role_id";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setLong("user_id", userId);
        query.setLong("role_id", roleId);
        int var=query.executeUpdate();
        return var != 0;
    }


    @SuppressWarnings("unchecked")
	@Override
    public User findOne(Long userId) {
    	 String sql = "select id, username, password, salt, locked from sys_users where id=:id";
         SQLQuery query=getCurrentSession().createSQLQuery(sql);
         query.setLong("id", userId);
        List<User> userList = query.list();
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @SuppressWarnings("unchecked")
	@Override
    public User findByUsername(String username) {
        String sql = "select id, username, password, salt, locked from sys_users where username=:username";
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setString("username", username);
       List<User> userList = query.list();
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

	@Override
    public Set<String> findRoles(String username) {
        String sql = "select role from sys_users u, sys_roles r,sys_users_roles ur where u.username=:username and u.id=ur.user_id and r.id=ur.role_id";
        Set<String> set=new HashSet<String>();
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setString("username", username);
        List<String> lists=query.list();
        for(String list:lists){
        	set.add(list);
        }
        return set;
    }

    @Override
    public Set<String> findPermissions(String username) {
        // 此处可以优化，比如查询到role后，一起获取roleId，然后直接根据roleId获取即可
        String sql = "select permission from sys_users u, sys_roles r, sys_permissions p, sys_users_roles ur, sys_roles_permissions rp where u.username=:username and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and p.id=rp.permission_id";
        Set<String> set=new HashSet<String>();
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setString("username", username);
        List<String> lists=query.list();
        for(String list:lists){
        	set.add(list);
        }
        return set;
    }
}
