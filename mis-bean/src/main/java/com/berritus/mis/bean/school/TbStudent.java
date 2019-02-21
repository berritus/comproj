package com.berritus.mis.bean.school;


import com.berritus.mis.core.bean.MisBean;

public class TbStudent extends MisBean {
    private static final long serialVersionUID = -2597490144092227311L;
    private Integer id;

    private Integer age;

    private String stuName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }
}