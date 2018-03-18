package com.monitor.orgalon.core.service;

import com.monitor.orgalon.core.config.BaseMonitorConfig;
import com.monitor.orgalon.core.config.MonitorMenuConfig;
import com.monitor.orgalon.core.constants.MonitorMenuEnum;

import java.util.List;
import java.util.Map;

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
     * @param callbackMap 监控项-回调函数 映射map
     */
    void start(BaseMonitorConfig config,Map<MonitorMenuEnum,List<ResultCallback>> callbackMap);

    /**
     * 停止监控
     */
    void stop();
}
