package com.sso.login.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class Permission {

    public int getPermId() {
        return permId;
    }

    public void setPermId(int permId) {
        this.permId = permId;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPermDetail() {
        return permDetail;
    }

    public void setPermDetail(String permDetail) {
        this.permDetail = permDetail;
    }

    private int permId;

    private String permName;

    private String permDetail;

}

