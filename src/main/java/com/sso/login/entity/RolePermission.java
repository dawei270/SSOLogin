package com.sso.login.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class RolePermission {

    private int id;

    private int roleId;

    private int permId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPermId() {
        return permId;
    }

    public void setPermId(int permId) {
        this.permId = permId;
    }
}

