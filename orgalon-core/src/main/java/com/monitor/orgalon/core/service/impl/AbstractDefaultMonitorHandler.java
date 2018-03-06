package com.monitor.orgalon.core.service.impl;


import com.monitor.orgalon.core.config.MonitorMenuConfig;
import com.monitor.orgalon.core.service.ResultCallback;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 监控接口
 *
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractDefaultMonitorHandler<T> {
    /**
     * 执行具体监控动作
     *
     * @param callbackList
     * @return
     */
    protected abstract void doAction(List<ResultCallback> callbackList);

    public void startMonitor(ExecutorService executor, List<ResultCallback> callbackList, MonitorMenuConfig monitorMenuConfig) {
        MonitorProcess monitorProcess = new MonitorProcess(callbackList);
        if (executor instanceof ScheduledThreadPoolExecutor) {
            //定时任务线程,定时监控
            ((ScheduledThreadPoolExecutor) executor).scheduleWithFixedDelay(monitorProcess, monitorMenuConfig.getInitialDelay(), monitorMenuConfig.getDelay(), TimeUnit.MILLISECONDS);
        } else {
            //单独线程,被动获取监控
            executor.execute(monitorProcess);
        }

    }

    /**
     * 默认回调方法处理
     * 遍历用户声明的callback,执行每个bean的doCallback方法.
     * @param callbackList
     * @param monitorResult
     */
    protected void defaultDoCallback(List<ResultCallback> callbackList,T monitorResult){
        if(CollectionUtils.isNotEmpty(callbackList)){
            for(ResultCallback callback: callbackList){
                callback.doCallback(monitorResult);
            }
        }
    }


    private class MonitorProcess implements Runnable {
        private List<ResultCallback> callbackList;

        public MonitorProcess(List<ResultCallback> callbackList) {
            this.callbackList = callbackList;
        }

        @Override
        public void run() {
            doAction(callbackList);
        }
    }
}
