package com.monitor.orgalon.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring环境下orgalon监控入口
 * 负责:监控的初始化,启动等
 *
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class OrgalonContext implements ApplicationContextAware{
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
