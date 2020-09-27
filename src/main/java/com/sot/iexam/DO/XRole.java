package com.sot.iexam.DO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "x_role", schema = "iexam", catalog = "")
public class XRole {
    private Integer id;
    private String name;
    private String remark;
    private Integer status;
    private String createTime;
    private String modifiedTime;
    private Integer level;

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public XRole withLevel(Integer level) {
        this.level = level;
        return this;
    }

    @Basic
    @Column(name = "modified_time")
    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public XRole withModifiedTime(String modifiedTime) {
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

    public XRole withCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public XRole withStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public XRole withRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XRole withName(String name) {
        this.name = name;
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

    public XRole withId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XRole xRole = (XRole) o;
        return id == xRole.id &&
                Objects.equals(name, xRole.name) &&
                Objects.equals(remark, xRole.remark) &&
                Objects.equals(status, xRole.status) &&
                Objects.equals(createTime, xRole.createTime) &&
                Objects.equals(modifiedTime, xRole.modifiedTime) &&
                Objects.equals(level, xRole.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, remark, status, createTime, modifiedTime, level);
    }
}