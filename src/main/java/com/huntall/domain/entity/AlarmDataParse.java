package com.huntall.domain.entity;

import com.huntall.common.constant.Gender;
import com.huntall.common.constant.Nation;
import com.huntall.common.sdk.HCNetSDK;
import com.huntall.common.util.CommonUtil;
import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author wangpeng
 * @since 2025年06月30日 17:50
 */
@Component
public class AlarmDataParse {

    public static void alarmDataHandle(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        final Logger logger = LoggerFactory.getLogger(AlarmDataParse.class);

        logger.info("报警事件类型:  lCommand:{}", Integer.toHexString(lCommand));

        //lCommand是传的报警类型
        if (lCommand == HCNetSDK.COMM_ID_INFO_ALARM) {
            // 身份证信息
            HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM strIDCardInfo = new HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM();
            strIDCardInfo.write();
            Pointer pIDCardInfo = strIDCardInfo.getPointer();
            pIDCardInfo.write(0, pAlarmInfo.getByteArray(0, strIDCardInfo.size()), 0, strIDCardInfo.size());
            strIDCardInfo.read();
            logger.info("报警主类型: {}，报警次类型: {}", Integer.toHexString(strIDCardInfo.dwMajor), Integer.toHexString(strIDCardInfo.dwMinor));

            // 身份证信息
            String IDnum = new String(strIDCardInfo.struIDCardCfg.byIDNum).trim();
            Date alarmDate;

            // 报警时间
            try {
                alarmDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .parse(Integer.toString(strIDCardInfo.struSwipeTime.wYear) + "-"
                                + strIDCardInfo.struSwipeTime.byMonth + "-"
                                + strIDCardInfo.struSwipeTime.byDay + " "
                                + strIDCardInfo.struSwipeTime.byHour + ":"
                                + strIDCardInfo.struSwipeTime.byMinute + ":"
                                + strIDCardInfo.struSwipeTime.bySecond);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            HCNetSDK.NET_DVR_ID_CARD_INFO idCardInfo = strIDCardInfo.struIDCardCfg;
            logger.info("报警时间: {} 【身份证信息】: 身份证号码: {}，姓名: {}，住址: {}, 出生日期: {}, 性别: {}-{}, 民族: {}-{}",
                    alarmDate, IDnum, new String(idCardInfo.byName, StandardCharsets.UTF_8).trim(),
                    new String(idCardInfo.byAddr, StandardCharsets.UTF_8).trim(),
                    String.valueOf(idCardInfo.struBirth.wYear) + "-" + String.format("%02d", idCardInfo.struBirth.byMonth) + "-" + String.format("%02d", idCardInfo.struBirth.byDay),
                    idCardInfo.bySex, Gender.getGenderName(idCardInfo.bySex),
                    idCardInfo.byNation, Nation.getNationName(idCardInfo.byNation));

            // 保存身份证图片
            // if (strIDCardInfo.dwPicDataLen > 0 || strIDCardInfo.pPicData != null) {}

            // 人脸图片
            // if (strIDCardInfo.dwCapturePicDataLen > 0 || strIDCardInfo.pCapturePicData != null) {}
        } else {
            logger.info("报警类型{}, 暂无处理逻辑", Integer.toHexString(lCommand));
        }
    }

}
