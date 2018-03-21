package com.github.monitorgroup.bean;

/**
 * @author niuniu
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class GCInfoCallbackBean extends CallbackBean {
  /**
   * 标识是哪个gc动作，一般为：end of major GC，Young Gen GC等，分别表示老年代和新生代的gc结束
   */
  private String name;
  /**
   * 标识这个收集器进行了几次gc
   */
  private long count;
  /**
   * 引起gc的原因,如：System.gc()，Allocation Failure，G1 Humongous Allocation等
   */
  private String gcCause;
  /**
   * gc的开始时间
   */
  private String startTime;
  /**
   * gc的结束时间
   */
  private String endTime;
  /**
   * gc前内存情况
   */
  private MemoryCommonBean beforeMemory;
  /**
   * gc后内存情况
   */
  private MemoryCommonBean afterMemory;
  /**
   * GC耗时，单位：毫秒
   */
  private long GCTime;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getCount() {
    return this.count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public String getGcCause() {
    return this.gcCause;
  }

  public void setGcCause(String gcCause) {
    this.gcCause = gcCause;
  }

  public String getStartTime() {
    return this.startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return this.endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public MemoryCommonBean getBeforeMemory() {
    return this.beforeMemory;
  }

  public void setBeforeMemory(MemoryCommonBean beforeMemory) {
    this.beforeMemory = beforeMemory;
  }

  public MemoryCommonBean getAfterMemory() {
    return this.afterMemory;
  }

  public void setAfterMemory(MemoryCommonBean afterMemory) {
    this.afterMemory = afterMemory;
  }

  public long getGCTime() {
    return this.GCTime;
  }

  public void setGCTime(long GCTime) {
    this.GCTime = GCTime;
  }

  @Override
  public String toString() {
    return "GCInfoCallbackBean [name=" + this.name + ", count=" + this.count + ", gcCause="
        + this.gcCause + ", startTime=" + this.startTime + ", endTime=" + this.endTime
        + ", beforeMemory=" + this.beforeMemory + ", afterMemory=" + this.afterMemory + ", GCTime="
        + this.GCTime + "]";
  }
}
