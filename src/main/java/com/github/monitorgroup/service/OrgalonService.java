package com.github.monitorgroup.service;

import java.util.List;

import com.github.monitorgroup.bean.MonitorBean;

/**
 * 监控接口
 *
 * @author niujunlong
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OrgalonService {

  /**
   * 开启监控
   *
   * @param monitorBeanList 监控项列表
   */
  void start(List<MonitorBean> monitorBeanList);

  /**
   * 停止监控
   */
  void stop();
}
