package com.monitor.orgalon.core.service.impl;

import com.monitor.orgalon.core.entity.MemoryCommonEntity;
import com.monitor.orgalon.core.entity.MemoryInfoEntity;
import com.monitor.orgalon.core.entity.MemoryPoolInfoEntity;
import com.monitor.orgalon.core.service.ResultCallback;
import org.apache.commons.collections.CollectionUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 内存监控Handler
 * @author niujunlong
 * @version 1.0.0
 * @since 1.0.0
 */
public class MemoryMonitorHandler extends AbstractDefaultMonitorHandler<MemoryInfoEntity> {
    /**
     * 当前监控时间的内存对象。每个监控周期会更新
     */
    private static volatile MemoryInfoEntity memoryInfoEntity = new MemoryInfoEntity();


    @Override
    protected void doAction(List<ResultCallback> callbackList) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        List<MemoryPoolMXBean> memoryPoolMXBeanList = ManagementFactory.getMemoryPoolMXBeans();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        MemoryCommonEntity heapEntity = new MemoryCommonEntity();
        heapEntity.setCommitted(heapMemoryUsage.getCommitted());
        heapEntity.setInit(heapMemoryUsage.getInit());
        heapEntity.setMax(heapMemoryUsage.getMax());
        heapEntity.setUsed(heapMemoryUsage.getUsed());
        memoryInfoEntity.setHeadMomory(heapEntity);

        MemoryCommonEntity noHeapEntity = new MemoryCommonEntity();
        noHeapEntity.setCommitted(nonHeapMemoryUsage.getCommitted());
        noHeapEntity.setInit(nonHeapMemoryUsage.getInit());
        noHeapEntity.setMax(nonHeapMemoryUsage.getMax());
        noHeapEntity.setUsed(nonHeapMemoryUsage.getUsed());
        memoryInfoEntity.setNoHeadMomory(noHeapEntity);

        if(!CollectionUtils.isEmpty(memoryPoolMXBeanList)){
            List<MemoryPoolInfoEntity> memoryPoolInfoEntitieList = new ArrayList<MemoryPoolInfoEntity>(memoryPoolMXBeanList.size());
            for(MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeanList){
                MemoryPoolInfoEntity memoryPoolInfoEntity = new MemoryPoolInfoEntity();
                memoryPoolInfoEntity.setName(memoryPoolMXBean.getName());
                memoryPoolInfoEntity.setManagerName(Arrays.deepToString(memoryPoolMXBean.getMemoryManagerNames()));
                memoryPoolInfoEntity.setObjectName(memoryPoolMXBean.getObjectName().toString());

                MemoryUsage usage = memoryPoolMXBean.getUsage();
                MemoryUsage peakUsage = memoryPoolMXBean.getPeakUsage();

                MemoryCommonEntity currentMemory = new MemoryCommonEntity();
                currentMemory.setCommitted(usage.getCommitted());
                currentMemory.setInit(usage.getInit());
                currentMemory.setMax(usage.getMax());
                currentMemory.setUsed(usage.getUsed());
                memoryPoolInfoEntity.setCurrentMemory(currentMemory);

                MemoryCommonEntity peakMemory = new MemoryCommonEntity();
                peakMemory.setCommitted(peakUsage.getCommitted());
                peakMemory.setInit(peakUsage.getInit());
                peakMemory.setMax(peakUsage.getMax());
                peakMemory.setUsed(peakUsage.getUsed());
                memoryPoolInfoEntity.setPeakMemroy(peakMemory);
                memoryPoolInfoEntitieList.add(memoryPoolInfoEntity);
            }
            memoryInfoEntity.setMemoryPoolInfoEntityList(memoryPoolInfoEntitieList);
        }
        defaultDoCallback(callbackList, memoryInfoEntity);
    }
}
