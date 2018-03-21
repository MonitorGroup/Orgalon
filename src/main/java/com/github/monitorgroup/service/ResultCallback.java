package com.github.monitorgroup.service;

import com.github.monitorgroup.bean.CallbackBean;

/**
 * 监控结果回调
 *
 * @author niujunlong
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ResultCallback<T extends CallbackBean> {

  void callback(T monitorResult);
}
