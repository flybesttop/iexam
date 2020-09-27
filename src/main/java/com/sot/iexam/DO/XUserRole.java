package com.sot.iexam.DO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "x_user_role", schema = "iexam", catalog = "")
public class XUserRole {
    private Integer id;
    private Integer roleId;
    private Integer userId;
    private String createTime;
    private String modifiedTime;

    @Basic
    @Column(name = "modified_time")
    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public XUserRole withModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public XUserRole withCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public XUserRole withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public XUserRole withRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public XUserRole withId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XUserRole xUserRole = (XUserRole) o;
        return id == xUserRole.id &&
                Objects.equals(roleId, xUserRole.roleId) &&
                Objects.equals(userId, xUserRole.userId) &&
                Objects.equals(createTime, xUserRole.createTime) &&
                Objects.equals(modifiedTime, xUserRole.modifiedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId, userId, createTime, modifiedTime);
    }
}
    