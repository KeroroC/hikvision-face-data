package com.huntall.domain;

import com.huntall.domain.service.HikService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Spring应用启动完成的事件
 * @author wangpeng
 * @since 2025年06月30日 18:12
 */
@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HikService hikService;

    public ApplicationReadyListener(HikService hikService) {
        this.hikService = hikService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("========== start init Hikvision device");
        try {
            hikService.init();
            hikService.setAlarmChan(hikService.lUserID);
        } catch (Exception e) {
            logger.error("========== init Hikvision device fail {}", e.getMessage());
        }
        logger.info("========== end init Hikvision device");
    }

}
