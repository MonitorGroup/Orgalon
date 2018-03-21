package com.github.monitorgroup.bean;

/**
 * 线程信息监控类
 *
 * @author niuniu
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class ThreadInfoCallbackBean extends CallbackBean {
  /**
   * 当前活动线程数
   */
  private int activityCount;
  /**
   * 峰值线程数
   */
  private int peakCount;
  /**
   * 线程总数
   */
  private long totalStartedThreadCount;
  /**
   * 守护线程数
   */
  private int daemonCount;


  public int getActivityCount() {
    return this.activityCount;
  }

  public void setActivityCount(int activityCount) {
    this.activityCount = activityCount;
  }

  public int getPeakCount() {
    return this.peakCount;
  }

  public void setPeakCount(int peakCount) {
    this.peakCount = peakCount;
  }

  public long getTotalStartedThreadCount() {
    return this.totalStartedThreadCount;
  }

  public void setTotalStartedThreadCount(long totalStartedThreadCount) {
    this.totalStartedThreadCount = totalStartedThreadCount;
  }

  public int getDaemonCount() {
    return this.daemonCount;
  }

  public void setDaemonCount(int daemonCount) {
    this.daemonCount = daemonCount;
  }

  @Override
  public String toString() {
    return "ThreadInfoCallbackBean [activityCount=" + this.activityCount + ", peakCount="
        + this.peakCount + ", totalStartedThreadCount=" + this.totalStartedThreadCount
        + ", daemonCount=" + this.daemonCount + "]";
  }
}
