package com.huntall.domain;

import com.huntall.domain.service.HikService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * Spring容器关闭事件，在bean容器销毁之前
 * @author wangpeng
 * @since 2025年07月01日 10:01
 */
@Component
public class ContextClosedListener implements ApplicationListener<ContextClosedEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HikService hikService;

    public ContextClosedListener(HikService hikService) {
        this.hikService = hikService;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // 海康设备注销用户，释放sdk资源
        logger.info("========== start logout hikvision device");
        hikService.closedAlarmChan(hikService.lAlarmHandle);
        hikService.logout();
        logger.info("========== logout hikvision device success!");
    }
}
