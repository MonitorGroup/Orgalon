package com.github.monitorgroup.service.impl;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.management.ListenerNotFoundException;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.monitorgroup.bean.CallbackBean;
import com.github.monitorgroup.bean.GCInfoCallbackBean;
import com.github.monitorgroup.bean.MemoryCommonBean;
import com.github.monitorgroup.bean.OrgalonConstants;
import com.github.monitorgroup.service.AbstractMonitorHandler;
import com.github.monitorgroup.service.ResultCallback;
import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

/**
 * GC监控handler 对NotificationEmitter添加一个GC监听器，当GC结束时，执行监听器的handleNotification方法
 *
 * @author niujunlong
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("restriction")
public class GCMonitorHandler extends AbstractMonitorHandler<GCInfoCallbackBean> {
  private static final Logger LOGGER = LoggerFactory.getLogger(GCMonitorHandler.class);

  private static final long startRuntimeTime = ManagementFactory.getRuntimeMXBean().getStartTime();

  private Map<NotificationEmitter, NotificationListener> emitterMap =
      new IdentityHashMap<NotificationEmitter, NotificationListener>();

  /**
   * 因为gc的监听是通过listener,所以没有返回值,用占位符Void替代返回值
   */
  @Override
  protected void doAction(final ResultCallback<CallbackBean> resultCallback) {
    List<GarbageCollectorMXBean> garbageCollectorMXBeanList =
        ManagementFactory.getGarbageCollectorMXBeans();
    if (garbageCollectorMXBeanList == null || garbageCollectorMXBeanList.isEmpty()) {
      return;
    }
    for (GarbageCollectorMXBean gcBean : garbageCollectorMXBeanList) {
      NotificationEmitter emitter = (NotificationEmitter) gcBean;
      // 每个内存区域，new一个监听器
      NotificationListener listener = new NotificationListener() {

        @Override
        public void handleNotification(Notification notification, Object handback) {
          GCInfoCallbackBean gcInfoEntity = new GCInfoCallbackBean();
          GarbageCollectionNotificationInfo info =
              GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
          GcInfo gcInfo = info.getGcInfo();

          Map<String, MemoryUsage> memoryUsageBeforeGc = gcInfo.getMemoryUsageBeforeGc();
          Map<String, MemoryUsage> memoryUsageAfterGc = gcInfo.getMemoryUsageAfterGc();
          SimpleDateFormat sdf = new SimpleDateFormat(OrgalonConstants.DATE_FORMAT_Y_M_D_H_M_S_S);
          gcInfoEntity.setGcCause(info.getGcCause());
          gcInfoEntity.setName(info.getGcAction());
          gcInfoEntity.setCount(info.getGcInfo().getId());
          gcInfoEntity.setGCTime(info.getGcInfo().getDuration());
          gcInfoEntity.setStartTime(sdf.format(new Date(startRuntimeTime + gcInfo.getStartTime())));
          gcInfoEntity.setEndTime(sdf.format(new Date(startRuntimeTime + gcInfo.getEndTime())));
          if (memoryUsageAfterGc != null && !memoryUsageAfterGc.isEmpty()) {
            for (Map.Entry<String, MemoryUsage> entry : memoryUsageAfterGc.entrySet()) {
              String name = entry.getKey();

              MemoryUsage beforeUsage = memoryUsageBeforeGc.get(name);
              MemoryCommonBean beforeMemory = new MemoryCommonBean();
              beforeMemory.setCommitted(beforeUsage.getCommitted());
              beforeMemory.setUsed(beforeUsage.getUsed());
              beforeMemory.setMax(beforeUsage.getMax());
              beforeMemory.setInit(beforeUsage.getInit());

              MemoryUsage afterUsage = entry.getValue();
              MemoryCommonBean afterMemory = new MemoryCommonBean();
              afterMemory.setCommitted(afterUsage.getCommitted());
              afterMemory.setUsed(afterUsage.getUsed());
              afterMemory.setMax(afterUsage.getMax());
              afterMemory.setInit(afterUsage.getInit());
              gcInfoEntity.setAfterMemory(afterMemory);
              gcInfoEntity.setBeforeMemory(beforeMemory);
            }
          }
          GCMonitorHandler.this.defaultDoCallback(resultCallback, gcInfoEntity);
        }
      };
      // 添加监听器
      emitter.addNotificationListener(listener, new NotificationFilter() {
        private static final long serialVersionUID = -3919323140352547629L;

        @Override
        public boolean isNotificationEnabled(Notification notification) {
          return notification.getType()
              .equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION);
        }
      }, null);
      this.emitterMap.put(emitter, listener);
    }
  }

  @Override
  public void stopMonitor() {
    List<GarbageCollectorMXBean> garbageCollectorMXBeanList =
        ManagementFactory.getGarbageCollectorMXBeans();
    if (garbageCollectorMXBeanList == null || garbageCollectorMXBeanList.isEmpty()) {
      return;
    }
    for (GarbageCollectorMXBean gcBean : garbageCollectorMXBeanList) {
      NotificationEmitter emitter = (NotificationEmitter) gcBean;
      NotificationListener listener = this.emitterMap.remove(emitter);
      if (listener != null) {
        try {
          emitter.removeNotificationListener(listener);
        } catch (ListenerNotFoundException e) {
          LOGGER.error("GCMonitorHandler stopMonitor gcBean error: ", e);
        }
      }
    }
  }
}
