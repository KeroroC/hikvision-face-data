package com.huntall.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author wangpeng
 * @since 2025年07月01日 10:42
 */
@TableName("hik_id_card_info")
public class HikIdCardInfo {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 报警时间(刷卡时间)
     */
    @TableField("alarm_date")
    private Date alarmDate;

    /**
     * 身份证号
     */
    @TableField("id_card_no")
    private String idCardNo;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 住址
     */
    @TableField("address")
    private String address;

    /**
     * 出生日期(yyyy-MM-dd)
     */
    @TableField("birth")
    private String birth;

    /**
     * 性别代码
     */
    @TableField("gender")
    private Short gender;

    /**
     * 性别中文名
     */
    @TableField("gender_name")
    private String genderName;

    /**
     * 民族代码
     */
    @TableField("nation")
    private Short nation;

    /**
     * 民族中文名
     */
    @TableField("nation_name")
    private String nationName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public Short getNation() {
        return nation;
    }

    public void setNation(Short nation) {
        this.nation = nation;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }
}
