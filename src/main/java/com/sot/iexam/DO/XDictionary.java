package com.sot.iexam.DO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "x_dictionary", schema = "iexam", catalog = "")
public class XDictionary {
    private Integer id;
    private String value;
    private String type;
    private String code;
    private String detail;
    private Integer sort;
    private String createTime;
    private String modifiedTime;
    private String column10;
    private String column11;
    private String column14;
    private String column15;

    @Basic
    @Column(name = "modified_time")
    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XDictionary that = (XDictionary) o;
        return id == that.id &&
                Objects.equals(value, that.value) &&
                Objects.equals(type, that.type) &&
                Objects.equals(code, that.code) &&
                Objects.equals(detail, that.detail) &&
                Objects.equals(sort, that.sort) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(modifiedTime, that.modifiedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, type, code, detail, sort, createTime, modifiedTime);
    }

    @Basic
    @Column(name = "column_10")
    public String getColumn10() {
        return column10;
    }

    public void setColumn10(String column10) {
        this.column10 = column10;
    }

    @Basic
    @Column(name = "column_11")
    public String getColumn11() {
        return column11;
    }

    public void setColumn11(String column11) {
        this.column11 = column11;
    }

    @Basic
    @Column(name = "column_14")
    public String getColumn14() {
        return column14;
    }

    public void setColumn14(String column14) {
        this.column14 = column14;
    }

    @Basic
    @Column(name = "column_15")
    public String getColumn15() {
        return column15;
    }

    public void setColumn15(String column15) {
        this.column15 = column15;
    }

    @Override
    public String toString() {
        return "XDictionary{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", detail='" + detail + '\'' +
                ", sort=" + sort +
                ", createTime='" + createTime + '\'' +
                ", modifiedTime='" + modifiedTime + '\'' +
                ", column10='" + column10 + '\'' +
                ", column11='" + column11 + '\'' +
                ", column14='" + column14 + '\'' +
                ", column15='" + column15 + '\'' +
                '}';
    }
}