package com.github.monitorgroup.service.impl;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.monitorgroup.bean.MemoryCommonBean;
import com.github.monitorgroup.bean.MemoryInfoCallbackBean;
import com.github.monitorgroup.bean.MemoryInfoCallbackBean.MemoryPoolInfoEntity;
import com.github.monitorgroup.service.AbstractMonitorHandler;
import com.github.monitorgroup.service.ResultCallback;

/**
 * 内存监控Handler
 *
 * @author niujunlong
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class MemoryMonitorHandler extends AbstractMonitorHandler<MemoryInfoCallbackBean> {

  @Override
  protected void doAction(ResultCallback resultCallback) {
    // 当前监控时间的内存对象。每个监控周期会更新
    MemoryInfoCallbackBean memoryInfoEntity = new MemoryInfoCallbackBean();

    MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    List<MemoryPoolMXBean> memoryPoolMXBeanList = ManagementFactory.getMemoryPoolMXBeans();
    MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
    MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

    MemoryCommonBean heapEntity = new MemoryCommonBean();
    heapEntity.setCommitted(heapMemoryUsage.getCommitted());
    heapEntity.setInit(heapMemoryUsage.getInit());
    heapEntity.setMax(heapMemoryUsage.getMax());
    heapEntity.setUsed(heapMemoryUsage.getUsed());
    memoryInfoEntity.setHeadMomory(heapEntity);

    MemoryCommonBean noHeapEntity = new MemoryCommonBean();
    noHeapEntity.setCommitted(nonHeapMemoryUsage.getCommitted());
    noHeapEntity.setInit(nonHeapMemoryUsage.getInit());
    noHeapEntity.setMax(nonHeapMemoryUsage.getMax());
    noHeapEntity.setUsed(nonHeapMemoryUsage.getUsed());
    memoryInfoEntity.setNoHeadMomory(noHeapEntity);

    if (memoryPoolMXBeanList != null && !memoryPoolMXBeanList.isEmpty()) {
      List<MemoryPoolInfoEntity> memoryPoolInfoEntitieList =
          new ArrayList<MemoryPoolInfoEntity>(memoryPoolMXBeanList.size());
      for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeanList) {
        MemoryPoolInfoEntity memoryPoolInfoEntity = new MemoryPoolInfoEntity();
        memoryPoolInfoEntity.setName(memoryPoolMXBean.getName());
        memoryPoolInfoEntity
            .setManagerName(Arrays.deepToString(memoryPoolMXBean.getMemoryManagerNames()));
        memoryPoolInfoEntity.setObjectName(memoryPoolMXBean.getName());

        MemoryUsage usage = memoryPoolMXBean.getUsage();
        MemoryUsage peakUsage = memoryPoolMXBean.getPeakUsage();

        MemoryCommonBean currentMemory = new MemoryCommonBean();
        currentMemory.setCommitted(usage.getCommitted());
        currentMemory.setInit(usage.getInit());
        currentMemory.setMax(usage.getMax());
        currentMemory.setUsed(usage.getUsed());
        memoryPoolInfoEntity.setCurrentMemory(currentMemory);

        MemoryCommonBean peakMemory = new MemoryCommonBean();
        peakMemory.setCommitted(peakUsage.getCommitted());
        peakMemory.setInit(peakUsage.getInit());
        peakMemory.setMax(peakUsage.getMax());
        peakMemory.setUsed(peakUsage.getUsed());
        memoryPoolInfoEntity.setPeakMemroy(peakMemory);
        memoryPoolInfoEntitieList.add(memoryPoolInfoEntity);
      }
      memoryInfoEntity.setMemoryPoolInfoEntityList(memoryPoolInfoEntitieList);
    }
    this.defaultDoCallback(resultCallback, memoryInfoEntity);
  }
}
