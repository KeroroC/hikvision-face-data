package com.huntall.domain.entity;

import com.huntall.common.sdk.HCNetSDK;
import com.huntall.domain.service.DataService;
import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wangpeng
 * @since 2025年06月30日 17:50
 */
@Component
public class AlarmDataParse {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DataService dataService;

    public AlarmDataParse(DataService dataService) {
        this.dataService = dataService;
    }

    public void alarmDataHandle(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
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

            // 插入到数据库表
            dataService.insertInfo(strIDCardInfo);

            // 保存身份证图片
            // if (strIDCardInfo.dwPicDataLen > 0 || strIDCardInfo.pPicData != null) {}

            // 人脸图片
            // if (strIDCardInfo.dwCapturePicDataLen > 0 || strIDCardInfo.pCapturePicData != null) {}
        } else {
            logger.info("报警类型{}, 暂无处理逻辑", Integer.toHexString(lCommand));
        }
    }

}
