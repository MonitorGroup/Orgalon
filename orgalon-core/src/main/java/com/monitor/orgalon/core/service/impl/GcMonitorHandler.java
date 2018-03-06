package com.monitor.orgalon.core.service.impl;

import com.monitor.orgalon.core.constants.OrgalonConstants;
import com.monitor.orgalon.core.entity.GcInfoEntity;
import com.monitor.orgalon.core.entity.MemoryCommonEntity;
import com.monitor.orgalon.core.service.ResultCallback;
import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * GC监控handler
 * 对NotificationEmitter添加一个GC监听器，当GC结束时，执行监听器的handleNotification方法
 *
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
public class GcMonitorHandler extends AbstractDefaultMonitorHandler<GcInfoEntity> {
    private static final long startRuntimeTime = ManagementFactory.getRuntimeMXBean().getStartTime();

    /**
     * 因为gc的监听是通过listener,所以没有返回值,用占位符Void替代返回值.
     *
     * @param callbackList
     * @return
     */
    @Override
    public void doAction(final List<ResultCallback> callbackList) {
        List<GarbageCollectorMXBean> garbageCollectorMXBeanList = ManagementFactory.getGarbageCollectorMXBeans();
        if (CollectionUtils.isEmpty(garbageCollectorMXBeanList)) {
            return;
        }
        for (GarbageCollectorMXBean gcBean : garbageCollectorMXBeanList) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            //每个内存区域，new一个监听器
            NotificationListener listener = new NotificationListener() {
                @Override
                public void handleNotification(Notification notification, Object handback) {
                    GcInfoEntity gcInfoEntity = new GcInfoEntity();
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
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
                    if (!MapUtils.isEmpty(memoryUsageAfterGc)) {
                        for (Map.Entry<String, MemoryUsage> entry : memoryUsageAfterGc.entrySet()) {
                            String name = entry.getKey();

                            MemoryUsage beforeUsage = memoryUsageBeforeGc.get(name);
                            MemoryCommonEntity beforeMemory = new MemoryCommonEntity();
                            beforeMemory.setCommitted(beforeUsage.getCommitted());
                            beforeMemory.setUsed(beforeUsage.getUsed());
                            beforeMemory.setMax(beforeUsage.getMax());
                            beforeMemory.setInit(beforeUsage.getInit());

                            MemoryUsage afterUsage = entry.getValue();
                            MemoryCommonEntity afterMemory = new MemoryCommonEntity();
                            afterMemory.setCommitted(afterUsage.getCommitted());
                            afterMemory.setUsed(afterUsage.getUsed());
                            afterMemory.setMax(afterUsage.getMax());
                            afterMemory.setInit(afterUsage.getInit());
                            gcInfoEntity.setAfterMemory(afterMemory);
                            gcInfoEntity.setBeforeMemory(beforeMemory);
                        }
                    }
                    defaultDoCallback(callbackList, gcInfoEntity);
                }
            };
            //添加监听器
            emitter.addNotificationListener(listener, new NotificationFilter() {
                @Override
                public boolean isNotificationEnabled(Notification notification) {
                    return notification.getType()
                            .equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION);
                }
            }, null);
        }
    }
}
