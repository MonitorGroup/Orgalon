package com.github.monitorgroup.service.impl;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import com.github.monitorgroup.bean.ThreadInfoCallbackBean;
import com.github.monitorgroup.service.AbstractMonitorHandler;
import com.github.monitorgroup.service.ResultCallback;

/**
 * 线程监控Handler
 *
 * @author niujunlong
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class ThreadMonitorHandler extends AbstractMonitorHandler<ThreadInfoCallbackBean> {

  @Override
  protected void doAction(ResultCallback resultCallback) {
    // 当前监控时间的线程对象。每个监控周期会更新
    ThreadInfoCallbackBean threadInfoEntity = new ThreadInfoCallbackBean();

    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    threadInfoEntity.setActivityCount(threadMXBean.getThreadCount());
    threadInfoEntity.setPeakCount(threadMXBean.getPeakThreadCount());
    threadInfoEntity.setTotalStartedThreadCount(threadMXBean.getTotalStartedThreadCount());
    threadInfoEntity.setDaemonCount(threadMXBean.getDaemonThreadCount());
    this.defaultDoCallback(resultCallback, threadInfoEntity);
  }
}
