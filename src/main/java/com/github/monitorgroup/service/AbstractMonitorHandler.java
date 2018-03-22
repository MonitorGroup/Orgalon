package com.github.monitorgroup.service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.monitorgroup.bean.CallbackBean;
import com.github.monitorgroup.bean.MonitorBean;

/**
 * 监控接口
 *
 * @author niujunlong
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractMonitorHandler<T> {

  public void startMonitor(ScheduledExecutorService scheduledExecutorService,
      MonitorBean monitorBean) {
    MonitorProcess monitorProcess = new MonitorProcess(monitorBean.getResultCallback());
    if (monitorBean.getMonitor().asyn) {
      // 定时任务线程,定时监控
      scheduledExecutorService.scheduleWithFixedDelay(monitorProcess, monitorBean.getInitialDelay(),
          monitorBean.getDelay(), TimeUnit.MILLISECONDS);
    } else {
      // 直接执行监控
      monitorProcess.run();
    }
  }

  public void stopMonitor() {}

  /**
   * 执行具体监控动作
   *
   * @param resultCallback
   */
  protected abstract void doAction(ResultCallback resultCallback);

  /**
   * 默认回调方法处理 遍历用户声明的callback,执行每个bean的doCallback方法.
   *
   * @param callbackList
   * @param monitorResult
   */
  protected void defaultDoCallback(ResultCallback resultCallback, CallbackBean monitorResult) {
    if (resultCallback != null) {
      resultCallback.callback(monitorResult);
    }
  }

  private class MonitorProcess implements Runnable {
    private final Logger LOGGER = LoggerFactory.getLogger(MonitorProcess.class);

    private ResultCallback resultCallback;

    public MonitorProcess(ResultCallback resultCallback) {
      this.resultCallback = resultCallback;
    }

    @Override
    public void run() {
      try {
        AbstractMonitorHandler.this.doAction(this.resultCallback);
      } catch (Exception e) {
        this.LOGGER.error("MonitorProcess run error: ", e);
      }
    }
  }
}
