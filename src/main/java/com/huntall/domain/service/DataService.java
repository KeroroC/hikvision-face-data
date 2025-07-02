package com.huntall.domain.service;

import com.huntall.common.constant.Gender;
import com.huntall.common.constant.Nation;
import com.huntall.common.sdk.HCNetSDK;
import com.huntall.dao.HikIdCardInfoComponent;
import com.huntall.dao.po.HikIdCardInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangpeng
 * @since 2025年06月30日 18:00
 */
@Service
public class DataService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HikIdCardInfoComponent hikIdCardInfoComponent;

    public DataService(HikIdCardInfoComponent hikIdCardInfoComponent) {
        this.hikIdCardInfoComponent = hikIdCardInfoComponent;
    }

    /**
     * 将获取到的信息插入到数据库表里
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertInfo(HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM alarmInfo) {
        HikIdCardInfo hikIdCardInfo = generateHikIdCardInfo(alarmInfo);
        hikIdCardInfoComponent.getBaseMapper().insert(hikIdCardInfo);
    }

    private HikIdCardInfo generateHikIdCardInfo(HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM alarmInfo) {
        // 身份证信息
        HCNetSDK.NET_DVR_ID_CARD_INFO idCardInfo = alarmInfo.struIDCardCfg;

        String idCardNo = new String(idCardInfo.byIDNum).trim();
        Date alarmDate;
        String name = new String(idCardInfo.byName, StandardCharsets.UTF_8).trim();
        String address = new String(idCardInfo.byAddr, StandardCharsets.UTF_8).trim();
        String birth = String.valueOf(idCardInfo.struBirth.wYear) + "-" + String.format("%02d", idCardInfo.struBirth.byMonth) + "-" + String.format("%02d", idCardInfo.struBirth.byDay);
        byte gender = idCardInfo.bySex;
        byte nation = idCardInfo.byNation;

        // 报警时间
        try {
            alarmDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(Integer.toString(alarmInfo.struSwipeTime.wYear) + "-"
                            + alarmInfo.struSwipeTime.byMonth + "-"
                            + alarmInfo.struSwipeTime.byDay + " "
                            + alarmInfo.struSwipeTime.byHour + ":"
                            + alarmInfo.struSwipeTime.byMinute + ":"
                            + alarmInfo.struSwipeTime.bySecond);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        logger.info("报警时间: {} 【身份证信息】: 身份证号码: {}，姓名: {}，住址: {}, 出生日期: {}, 性别: {}-{}, 民族: {}-{}",
                alarmDate, idCardNo, name, address, birth, gender, Gender.getGenderName(gender), nation, Nation.getNationName(nation));

        HikIdCardInfo hikIdCardInfo = new HikIdCardInfo();
        hikIdCardInfo.setIdCardNo(idCardNo);
        hikIdCardInfo.setAlarmDate(alarmDate);
        hikIdCardInfo.setName(name);
        hikIdCardInfo.setAddress(address);
        hikIdCardInfo.setBirth(birth);
        hikIdCardInfo.setGender((short) gender);
        hikIdCardInfo.setGenderName(Gender.getGenderName(gender));
        hikIdCardInfo.setNation((short) nation);
        hikIdCardInfo.setNationName(Nation.getNationName(nation));

        return hikIdCardInfo;
    }
}
