package com.sot.iexam.DO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "x_node", schema = "iexam", catalog = "")
public class XNode {
    private Integer id;
    private String iconClass;
    private String email;
    private Integer isShow;
    private Integer isMenu;
    private Integer level;
    private String name;
    private Integer pid;
    private Integer sortId;
    private String url;
    private String createTime;
    private String modifiedTime;
    private Byte canDelete;

    @Basic
    @Column(name = "modified_time")
    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public XNode withModifiedTime(String modifiedTime) {
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

    public XNode withCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public XNode withUrl(String url) {
        this.url = url;
        return this;
    }

    @Basic
    @Column(name = "sort_id")
    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public XNode withSortId(Integer sortId) {
        this.sortId = sortId;
        return this;
    }

    @Basic
    @Column(name = "pid")
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public XNode withPid(Integer pid) {
        this.pid = pid;
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

    public XNode withName(String name) {
        this.name = name;
        return this;
    }

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public XNode withLevel(Integer level) {
        this.level = level;
        return this;
    }

    @Basic
    @Column(name = "is_menu")
    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public XNode withIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
        return this;
    }

    @Basic
    @Column(name = "is_show")
    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public XNode withIsShow(Integer isShow) {
        this.isShow = isShow;
        return this;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public XNode withEmail(String email) {
        this.email = email;
        return this;
    }

    @Basic
    @Column(name = "icon_class")
    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public XNode withIconClass(String iconClass) {
        this.iconClass = iconClass;
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

    public XNode withId(Integer id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "can_delete")
    public Byte getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Byte canDelete) {
        this.canDelete = canDelete;
    }

    public XNode withCanDelete(Byte canDelete) {
        this.canDelete = canDelete;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XNode xNode = (XNode) o;

        if (id != xNode.id) return false;
        if (iconClass != null ? !iconClass.equals(xNode.iconClass) : xNode.iconClass != null)
            return false;
        if (email != null ? !email.equals(xNode.email) : xNode.email != null)
            return false;
        if (isShow != null ? !isShow.equals(xNode.isShow) : xNode.isShow != null)
            return false;
        if (isMenu != null ? !isMenu.equals(xNode.isMenu) : xNode.isMenu != null)
            return false;
        if (level != null ? !level.equals(xNode.level) : xNode.level != null)
            return false;
        if (name != null ? !name.equals(xNode.name) : xNode.name != null)
            return false;
        if (pid != null ? !pid.equals(xNode.pid) : xNode.pid != null)
            return false;
        if (sortId != null ? !sortId.equals(xNode.sortId) : xNode.sortId != null)
            return false;
        if (url != null ? !url.equals(xNode.url) : xNode.url != null)
            return false;
        if (createTime != null ? !createTime.equals(xNode.createTime) : xNode.createTime != null)
            return false;
        if (modifiedTime != null ? !modifiedTime.equals(xNode.modifiedTime) : xNode.modifiedTime != null)
            return false;
        return canDelete != null ? canDelete.equals(xNode.canDelete) : xNode.canDelete == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (iconClass != null ? iconClass.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (isShow != null ? isShow.hashCode() : 0);
        result = 31 * result + (isMenu != null ? isMenu.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (sortId != null ? sortId.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifiedTime != null ? modifiedTime.hashCode() : 0);
        result = 31 * result + (canDelete != null ? canDelete.hashCode() : 0);
        return result;
    }
}