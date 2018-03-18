package com.monitor.orgalon.spring.bean;

import com.monitor.orgalon.core.service.OrgalonService;
import com.monitor.orgalon.core.service.impl.OrgalonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
public class MonitorBeanFactory {

    @Bean
    public OrgalonService createOrgalonServiceBean(){
        return new OrgalonServiceImpl();
    }

}
