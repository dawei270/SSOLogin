package com.sso.login.config;

import com.sso.login.encryption.ThreeDES;
import com.sso.login.entity.Permission;
import com.sso.login.entity.RolePermission;
import com.sso.login.entity.User;
import com.sso.login.redis.ShiroUtils;
import com.sso.login.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        User dbUser = userService.getUserInfoByName(user.getUsername());
        RolePermission rolePermission = userService.getPermIdByRoleId(dbUser.getRoleId());
        Permission permission = userService.getPermDetailByPermId(rolePermission.getPermId());
        System.out.println("AuthorizationInfo->" + permission.getPermDetail());
        //为用户添加权限
        info.addStringPermission(permission.getPermDetail());

        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.getUserInfoByName(token.getUsername());
        if (user == null) {
            System.out.println(("用户名不存在"));
            //用户名不存在
            //shiro底层会抛出UnKnowAccountException
            return null;
        }

        //判断密码
        try {
            ThreeDES threeDES = new ThreeDES();
            String key = "uatspdbcccgame2014061800";

            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, threeDES.decryptThreeDESECB(user.getPassword(), key), "");
            //验证成功开始踢人(清除缓存和Session)
            ShiroUtils.deleteCache(token.getUsername(),true);
            return simpleAuthenticationInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        //判断密码
//        try {
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), "");
//            return simpleAuthenticationInfo;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}

