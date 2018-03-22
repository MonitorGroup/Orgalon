package com.github.monitorgroup.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.monitorgroup.bean.MonitorBean;
import com.github.monitorgroup.service.AbstractMonitorHandler;
import com.github.monitorgroup.service.OrgalonService;

/**
 * 监控服务实现类 spring/spring boot 工程中,需要将此实现类声明为bean
 *
 * @author niujunlong
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrgalonServiceImpl implements OrgalonService {
  private static final Logger LOGGER = LoggerFactory.getLogger(OrgalonServiceImpl.class);

  private volatile ScheduledExecutorService scheduledExecutorService;
  private final List<AbstractMonitorHandler<?>> handlerList =
      new ArrayList<AbstractMonitorHandler<?>>();

  public OrgalonServiceImpl() {}

  public OrgalonServiceImpl(ScheduledExecutorService scheduledExecutorService) {
    this.scheduledExecutorService = scheduledExecutorService;
  }

  @Override
  public void start(List<MonitorBean> monitorBeanList) {
    LOGGER.info("OrgalonServiceImpl start begin");
    if (monitorBeanList != null && monitorBeanList.size() > 0) {
      this.initScheduledExecutorService(monitorBeanList);
      for (MonitorBean monitorBean : monitorBeanList) {
        if (monitorBean == null || monitorBean.getResultCallback() == null) {
          LOGGER.error("OrgalonServiceImpl start config monitorBean illegal: {}", monitorBean);
          continue;
        }
        AbstractMonitorHandler<?> handler = null;
        switch (monitorBean.getMonitor()) {
          case THREAD:
            handler = new ThreadMonitorHandler();
            break;
          case GC:
            handler = new GCMonitorHandler();
            break;
          case MEMORY:
            handler = new MemoryMonitorHandler();
            break;
          default:
            LOGGER.error("OrgalonServiceImpl start config illegal enum: {}", monitorBean);
            break;
        }
        if (handler != null) {
          handler.startMonitor(this.scheduledExecutorService, monitorBean);
          this.handlerList.add(handler);
        }
      }
    }
    LOGGER.info("OrgalonServiceImpl start end");
  }

  private void initScheduledExecutorService(List<MonitorBean> monitorBeanList) {
    if (monitorBeanList == null || monitorBeanList.size() == 0
        || this.scheduledExecutorService != null) {
      return;
    }
    synchronized (this) {
      if (this.scheduledExecutorService != null) {
        return;
      }
      int count = 0;
      for (MonitorBean monitorBean : monitorBeanList) {
        if (monitorBean == null || monitorBean.getMonitor() == null
            || monitorBean.getMonitor().asyn) {
          continue;
        }
        count++;
      }
      ScheduledExecutorService scheduledExecutorService = null;
      if (count > 0) {
        scheduledExecutorService = Executors.newScheduledThreadPool(count);
        this.scheduledExecutorService = scheduledExecutorService;
      }
    }
  }


  @Override
  public void stop() {
    LOGGER.info("OrgalonServiceImpl stop begin");
    if (this.scheduledExecutorService != null) {
      this.scheduledExecutorService.shutdown();
      for (AbstractMonitorHandler<?> handler : this.handlerList) {
        if (handler != null) {
          handler.stopMonitor();
        }
      }
    }
    LOGGER.info("OrgalonServiceImpl stop end");
  }
}
