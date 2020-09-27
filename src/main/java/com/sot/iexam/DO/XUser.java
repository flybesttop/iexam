package com.sot.iexam.DO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "x_user", schema = "iexam", catalog = "")
public class XUser {
    private Integer id;
    private Integer isEnabled;
    private String password;
    private String phone;
    private String realName;
    private String username;
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

    public XUser withModifiedTime(String modifiedTime) {
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

    public XUser withCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public XUser withUsername(String username) {
        this.username = username;
        return this;
    }

    @Basic
    @Column(name = "real_name")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public XUser withRealName(String realName) {
        this.realName = realName;
        return this;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public XUser withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public XUser withPassword(String password) {
        this.password = password;
        return this;
    }

    @Basic
    @Column(name = "is_enabled")
    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public XUser withIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public XUser withId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XUser xUser = (XUser) o;
        return id == xUser.id &&
                Objects.equals(isEnabled, xUser.isEnabled) &&
                Objects.equals(password, xUser.password) &&
                Objects.equals(phone, xUser.phone) &&
                Objects.equals(realName, xUser.realName) &&
                Objects.equals(username, xUser.username) &&
                Objects.equals(createTime, xUser.createTime) &&
                Objects.equals(modifiedTime, xUser.modifiedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isEnabled, password, phone, realName, username, createTime, modifiedTime);
    }
}