package com.huntall.common.constant;

/**
 * RabbitMQ常量
 * @author wangpeng
 * @since 2025年07月09日 16:30
 */
public class RabbitConstant {

    /**
     * 队列名
     */
    public static final String HIK_QUEUE_NAME = "HikIdCardQueue";

    /**
     * 交换机名
     */
    public static final String HIK_EXCHANGE_NAME = "HikDirectExchange";

    /**
     * 海康设备身份证告警事件的路由
     */
    public static final String HIK_ID_CARD_ROUTE_KEY = "IdCard";
}
