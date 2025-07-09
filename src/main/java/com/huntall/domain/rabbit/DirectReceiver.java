package com.huntall.domain.rabbit;

import com.huntall.common.constant.RabbitConstant;
import com.huntall.common.sdk.HCNetSDK;
import com.huntall.domain.service.DataService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听海康设备身份证告警队列
 * @author wangpeng
 * @since 2025年07月09日 16:28
 */
@Component
@RabbitListener(queues = RabbitConstant.HIK_QUEUE_NAME)
public class DirectReceiver {

    private final DataService dataService;

    public DirectReceiver(DataService dataService) {
        this.dataService = dataService;
    }

    @RabbitHandler
    public void process(HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM alarmInfo) {
        System.out.println("收到消息：" + alarmInfo);
        dataService.insertInfo(alarmInfo);
    }
}
