package com.huntall.domain.service;

import org.springframework.stereotype.Service;

/**
 * @author wangpeng
 * @since 2025年06月30日 18:00
 */
@Service
public class MainService {

    private final HikService hikService;

    public MainService(HikService hikService) {
        this.hikService = hikService;
    }

    /**
     * 获取设备上报的信息并插入天阙的表里
     * @return
     */
    public boolean insertInfo() {
        return false;
    }
}
