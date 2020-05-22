package com.sso.login.impl;

import com.sso.login.entity.Permission;
import com.sso.login.entity.RolePermission;
import com.sso.login.entity.User;
import com.sso.login.mapper.UserMapper;
import com.sso.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserInfoByName(String username) {
        return userMapper.getUserInfoByName(username);
    }

    @Override
    public RolePermission getPermIdByRoleId(int role_id) {
        return userMapper.getPermIdByRoleId(role_id);
    }

    @Override
    public Permission getPermDetailByPermId(int perm_id) {
        return userMapper.getPermDetailByPermId(perm_id);
    }

    @Override
    public int updatePasswordWithEncryption(String username, String password) {
        return userMapper.updatePasswordWithEncryption(username, password);
    }

}

