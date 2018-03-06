package com.monitor.orgalon.core.service.impl;

import com.monitor.orgalon.core.config.BaseMonitorConfig;
import com.monitor.orgalon.core.config.MonitorMenuConfig;
import com.monitor.orgalon.core.constants.MonitorMenuEnum;
import com.monitor.orgalon.core.service.OrgalonService;
import com.monitor.orgalon.core.service.ResultCallback;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 监控服务实现类
 * spring/spring boot 工程中,需要将此实现类声明为bean
 *
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrgalonServiceImpl implements OrgalonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrgalonServiceImpl.class);
    /**
     * 监控项-监控线程池 映射关系map
     */
    private static volatile Map<MonitorMenuEnum,ExecutorService> monitorMenuEnumExecutorServiceMap;

    @Override
    public void start(BaseMonitorConfig config, List<MonitorMenuConfig> monitorMenuConfigList,List<ResultCallback> callbackList) {
        if (!config.isMainSwitch() || CollectionUtils.isEmpty(monitorMenuConfigList)) {
            return;
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Orgalon监控开始初始化");
        }
        monitorMenuEnumExecutorServiceMap = new HashedMap(monitorMenuConfigList.size());
        for (MonitorMenuConfig monitorMenuConfig : monitorMenuConfigList) {
            switch (monitorMenuConfig.getMonitorMenu()) {
                case THREAD:
                    ScheduledExecutorService threadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
                    monitorMenuEnumExecutorServiceMap.put(MonitorMenuEnum.THREAD,threadScheduledExecutor);
                    new ThreadHandler().startMonitor(threadScheduledExecutor, callbackList, monitorMenuConfig);
                    break;
                case GC:
                    ExecutorService gcThreadExecutor = Executors.newSingleThreadExecutor();
                    monitorMenuEnumExecutorServiceMap.put(MonitorMenuEnum.GC,gcThreadExecutor);
                    new GcMonitorHandler().startMonitor(gcThreadExecutor,callbackList,monitorMenuConfig);
                    break;
                case MEMORY:
                    ScheduledExecutorService memoryScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
                    monitorMenuEnumExecutorServiceMap.put(MonitorMenuEnum.MEMORY,memoryScheduledExecutor);
                    new ThreadHandler().startMonitor(memoryScheduledExecutor,callbackList,monitorMenuConfig);
                    break;
                default:
                    LOGGER.error("Orgalon监控初始化异常,异常配置: {}", monitorMenuConfig);
                    break;
            }
        }
    }


    @Override
    public void stop() {

    }
}
