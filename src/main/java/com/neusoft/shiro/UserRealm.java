package com.neusoft.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.neusoft.entity.User;
import com.neusoft.service.UserService;
import com.neusoft.service.UserServiceImpl;

/**
 * 
 * @author wtaisi
 *
 */
public class UserRealm extends AuthorizingRealm {

    private UserService userService = new UserServiceImpl();
    
    /**
	 * 授权
	 * 角色是谁？具有什么权限？
	 * 分发控制
	 * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        System.out.println("授权--username---: "+username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
      //传入zhang 返回角色的admin给setRoles赋值 	和	SecurityUtils.getSubject().hasRole("admin") 对比是否具有。
        authorizationInfo.setRoles(userService.findRoles(username));
      //传入zhang 返回权限user:create  或者 user:update 或者 menu:create给setStringPermissions赋值  	和	SecurityUtils.getSubject().isPermitted("user:create") 对比是否具有。
        authorizationInfo.setStringPermissions(userService.findPermissions(username));

        return authorizationInfo;
    }
    
    /**
     * 认证
     * 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();
        System.out.println("认证--username---: "+username);
        //User{id=73, username='zhang', password='9af1fdad77ce67e603abf63c8db30077', salt='5f5d5b0ca5b70b98b5cd0bcb01f6e065', locked=false}
        User user = userService.findByUsername(username);
        Session session = SecurityUtils.getSubject().getSession();
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }
        session.setAttribute("user", user);
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;//zhang
    }
}
