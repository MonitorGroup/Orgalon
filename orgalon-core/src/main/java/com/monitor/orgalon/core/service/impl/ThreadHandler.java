package com.monitor.orgalon.core.service.impl;

import com.monitor.orgalon.core.entity.ThreadInfoEntity;
import com.monitor.orgalon.core.service.ResultCallback;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.List;

/**
 * 线程监控Handler
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
public class ThreadHandler extends AbstractDefaultMonitorHandler<ThreadInfoEntity> {
    /**
     * 当前监控时间的线程对象。每个监控周期会更新
     */
    private static volatile ThreadInfoEntity threadInfoEntity = new ThreadInfoEntity();

    @Override
    protected void doAction(List<ResultCallback> callbackList) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        threadInfoEntity.setActivityCount(threadMXBean.getThreadCount());
        threadInfoEntity.setPeakCount(threadMXBean.getPeakThreadCount());
        threadInfoEntity.setTotalStartedThreadCount(threadMXBean.getTotalStartedThreadCount());
        threadInfoEntity.setDaemonCount(threadMXBean.getDaemonThreadCount());
        defaultDoCallback(callbackList, threadInfoEntity);
    }
}
