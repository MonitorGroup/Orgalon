package com.github.monitorgroup.bean;

import com.github.monitorgroup.bean.enums.MonitorEnum;
import com.github.monitorgroup.service.ResultCallback;

/**
 * 监控配置bean
 *
 * @author niuniu
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class MonitorBean {
  /**
   * 监控项
   */
  private MonitorEnum monitorEnum;

  /**
   * 初始延迟时间，单位：毫秒 默认初始延迟时间：0秒
   */
  private long initialDelay;

  /**
   * 监控间隔时间，单位：毫秒 默认间隔时间：1秒
   */
  private long delay = 1000;

  /**
   * 回调函数
   */
  private ResultCallback<CallbackBean> callback;

  public MonitorEnum getMonitorEnum() {
    return this.monitorEnum;
  }

  public void setMonitorEnum(MonitorEnum monitorEnum) {
    this.monitorEnum = monitorEnum;
  }

  public long getInitialDelay() {
    return this.initialDelay;
  }

  public void setInitialDelay(long initialDelay) {
    this.initialDelay = initialDelay;
  }

  public long getDelay() {
    return this.delay;
  }

  public void setDelay(long delay) {
    this.delay = delay;
  }

  public ResultCallback<CallbackBean> getCallback() {
    return this.callback;
  }

  public void setCallback(ResultCallback<CallbackBean> callback) {
    this.callback = callback;
  }

  @Override
  public String toString() {
    return "MonitorBean [monitorEnum=" + this.monitorEnum + ", initialDelay=" + this.initialDelay
        + ", delay=" + this.delay + ", callback=" + this.callback + "]";
  }
}
