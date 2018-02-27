package com.monitor.orgalon.core.config;

import com.monitor.orgalon.core.constants.MonitorMenuEnum;

/**
 * 单项监控配置
 *
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
public class MonitorMenuConfig {
    /**
     * 监控项
     */
    private MonitorMenuEnum monitorMenu;


    /**
     * 初始延迟时间，单位：毫秒
     * 默认初始延迟时间：0秒
     */
    private long initialDelay;
    /**
     * 监控间隔时间，单位：毫秒
     * 默认间隔时间：1秒
     */
    private long delay = 1000;

    public MonitorMenuEnum getMonitorMenu() {
        return monitorMenu;
    }

    public void setMonitorMenu(MonitorMenuEnum monitorMenu) {
        this.monitorMenu = monitorMenu;
    }


    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
