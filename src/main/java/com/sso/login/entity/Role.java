package com.sso.login.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class Role {

    private int roleId;

    private String roleName;

}

