package com.sot.iexam.DO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "x_role_node", schema = "iexam", catalog = "")
public class XRoleNode {
    private Integer id;
    private Integer nodeId;
    private Integer roleId;
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

    public XRoleNode withModifiedTime(String modifiedTime) {
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

    public XRoleNode withCreateTime(String createTime) {
        this.createTime = createTime;
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

    public XRoleNode withRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    @Basic
    @Column(name = "node_id")
    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public XRoleNode withNodeId(Integer nodeId) {
        this.nodeId = nodeId;
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

    public XRoleNode withId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XRoleNode xRoleNode = (XRoleNode) o;
        return id == xRoleNode.id &&
                Objects.equals(nodeId, xRoleNode.nodeId) &&
                Objects.equals(roleId, xRoleNode.roleId) &&
                Objects.equals(createTime, xRoleNode.createTime) &&
                Objects.equals(modifiedTime, xRoleNode.modifiedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nodeId, roleId, createTime, modifiedTime);
    }
}