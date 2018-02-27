package com.monitor.orgalon.core.config;

import java.util.List;

/**
 * 监控配置
 * 为了使用spring/spring boot各自独有的注解，所以声明一个配置基类，不同环境下的配置类，需要继承BaseonitorConfig,然后子类中去使用@Configuration/@ConfigurationProperties
 * 若有更好的解决办法，欢迎反馈
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
public class BaseMonitorConfig {
    /**
     * 监控项
     */
    private List<MonitorMenuConfig> monitorMenuList;
    /**
     * 总开关，若为false，则关闭所有监控
     */
    private boolean mainSwitch;

    public List<MonitorMenuConfig> getMonitorMenuList() {
        return monitorMenuList;
    }

    public void setMonitorMenuList(List<MonitorMenuConfig> monitorMenuList) {
        this.monitorMenuList = monitorMenuList;
    }

    public boolean isMainSwitch() {
        return mainSwitch;
    }

    public void setMainSwitch(boolean mainSwitch) {
        this.mainSwitch = mainSwitch;
    }
}
