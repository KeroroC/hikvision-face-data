package com.huntall.domain.entity;

import com.huntall.common.sdk.HCNetSDK;
import com.sun.jna.Pointer;
import org.springframework.stereotype.Component;

/**
 * @author wangpeng
 * @since 2025年06月30日 17:49
 */
@Component
public class FMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31 {

    private final AlarmDataParse alarmDataParse;

    public FMSGCallBack_V31(AlarmDataParse alarmDataParse) {
        this.alarmDataParse = alarmDataParse;
    }

    // 报警信息回调函数
    public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        alarmDataParse.alarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
        return true;
    }
}
