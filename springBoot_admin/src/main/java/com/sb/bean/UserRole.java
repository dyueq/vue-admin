package com.sb.bean;


/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
public class UserRole {
    private Long id;
    private Long userId;
    private Long RoleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return RoleId;
    }

    public void setRoleId(Long roleId) {
        RoleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", RoleId=" + RoleId +
                '}';
    }
}
