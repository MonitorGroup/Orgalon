package com.monitor.orgalon.core.service;

import com.monitor.orgalon.core.config.BaseMonitorConfig;
import com.monitor.orgalon.core.config.MonitorMenuConfig;

import java.util.List;

/**
 * 监控接口
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OrgalonService {
    /**
     * 开启监控
     * @param config  监控配置
     * @param monitorMenuConfigList 监控项列表
     */
    void start(BaseMonitorConfig config, List<MonitorMenuConfig> monitorMenuConfigList);

    /**
     * 停止监控
     */
    void stop();
}
