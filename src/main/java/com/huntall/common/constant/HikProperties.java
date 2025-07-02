package com.huntall.common.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * 海康设备的连接参数
 * @author wangpeng
 * @since 2025年06月23日 15:31
 */
@ConfigurationProperties(prefix = "hik-app")
public class HikProperties {

    /**
     * lib文件夹的前缀
     */
    private final String libPathPrefix = System.getProperty("user.dir");

    private final String deviceIp;
    private final short devicePort;
    private final String deviceUsername;
    private final String devicePassword;

    @ConstructorBinding
    public HikProperties(String deviceIp, short devicePort, String deviceUsername, String devicePassword) {
        this.deviceIp = deviceIp;
        this.devicePort = devicePort;
        this.deviceUsername = deviceUsername;
        this.devicePassword = devicePassword;
    }

    public String getLibPathPrefix() {
        return libPathPrefix;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public short getDevicePort() {
        return devicePort;
    }

    public String getDeviceUsername() {
        return deviceUsername;
    }

    public String getDevicePassword() {
        return devicePassword;
    }
}
