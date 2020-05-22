package com.sso.login.service;

import com.sso.login.entity.Permission;
import com.sso.login.entity.RolePermission;
import com.sso.login.entity.User;

public interface UserService {

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return user
     */
    public User getUserInfoByName(String username);

    /**
     * 根据角色id查询权限id
     * @param role_id
     * @return
     */
    public RolePermission getPermIdByRoleId(int role_id);

    /**
     * 根据角色id查询权限detail
     * @param perm_id
     * @return
     */
    public Permission getPermDetailByPermId(int perm_id);

    /**
     * 更新加密后的密码
     * @param username
     * @param password
     * @return
     */
    public int updatePasswordWithEncryption(String username, String password);

}

