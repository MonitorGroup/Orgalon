package com.github.monitorgroup.bean;

/**
 * 内存基础属性
 *
 * @author niuniu
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class MemoryCommonBean {
  /**
   * 已申请内存
   */
  private long committed;
  /**
   * 初始化内存
   */
  private long init;
  /**
   * 最大内存
   */
  private long max;
  /**
   * 已使用内存
   */
  private long used;

  public long getCommitted() {
    return this.committed;
  }

  public void setCommitted(long committed) {
    this.committed = committed;
  }

  public long getInit() {
    return this.init;
  }

  public void setInit(long init) {
    this.init = init;
  }

  public long getMax() {
    return this.max;
  }

  public void setMax(long max) {
    this.max = max;
  }

  public long getUsed() {
    return this.used;
  }

  public void setUsed(long used) {
    this.used = used;
  }

  @Override
  public String toString() {
    return "MemoryCommonBean [committed=" + this.committed + ", init=" + this.init + ", max="
        + this.max + ", used=" + this.used + "]";
  }
}
