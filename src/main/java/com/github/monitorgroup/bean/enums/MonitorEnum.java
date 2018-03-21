package com.github.monitorgroup.bean.enums;

/**
 * 监控维度枚举
 *
 * @author niuniu
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public enum MonitorEnum {
  THREAD(true), // 线程监控
  MEMORY(true), // 内存监控
  GC(false), // GC监控
  ;

  // 是否异步执行
  public final boolean asyn;

  private MonitorEnum(boolean asyn) {
    this.asyn = asyn;
  }
}
