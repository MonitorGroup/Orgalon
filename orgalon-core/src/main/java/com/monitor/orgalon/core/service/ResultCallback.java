package com.monitor.orgalon.core.service;

/**
 * 监控结果回调
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ResultCallback<T> {
    void doCallback(T monitorResult);
}
