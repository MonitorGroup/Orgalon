package com.monitor.orgalon.core.service.impl;

import com.monitor.orgalon.core.config.BaseMonitorConfig;
import com.monitor.orgalon.core.config.MonitorMenuConfig;
import com.monitor.orgalon.core.service.OrgalonService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 监控服务实现类
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrgalonServiceImpl implements OrgalonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrgalonServiceImpl.class);

    @Override
    public void start(BaseMonitorConfig config, List<MonitorMenuConfig> monitorMenuConfigList) {
        if (!config.isMainSwitch() || CollectionUtils.isEmpty(monitorMenuConfigList)) {
            return;
        }
        LOGGER.info("Orgalon监控开始初始化");
        for (MonitorMenuConfig monitorMenuConfig : monitorMenuConfigList) {
            switch (monitorMenuConfig.getMonitorMenu()) {
                case THREAD:
                    System.out.println(11);
                    break;
                case GC:
                    System.out.println(22);
                    break;
                case MEMORY:
                    System.out.println(33);
                    break;
                default:
                    LOGGER.error("Orgalon监控初始化异常,异常配置: {}",monitorMenuConfig);
                    break;
            }
        }
    }


    @Override
    public void stop() {

    }
}
