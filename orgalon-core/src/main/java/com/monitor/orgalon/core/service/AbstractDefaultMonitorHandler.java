package com.monitor.orgalon.core.service;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 监控接口
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractDefaultMonitorHandler<T> {
    /**
     * 执行具体监控动作
     * @param callback
     * @return
     */
    abstract T doAction(ResultCallback callback);

    public void startMonitor(ThreadPoolExecutor executor,ResultCallback callback){
        if(executor instanceof ScheduledThreadPoolExecutor){
            (ScheduledThreadPoolExecutor)((ScheduledThreadPoolExecutor) executor).scheduleWithFixedDelay()
        }
    }

    private class MonitorProcess implements Runnable{
        private ResultCallback callback;

        public MonitorProcess(ResultCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            doAction(callback);
        }
    }
}
