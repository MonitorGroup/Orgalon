package com.github.monitorgroup.bean;

import java.util.List;

/**
 * @author niuniu
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class MemoryInfoCallbackBean extends CallbackBean {
  /**
   * 堆内存
   */
  private MemoryCommonBean headMomory;
  /**
   * 非堆内存
   */
  private MemoryCommonBean noHeadMomory;
  /**
   * 各个区域内存数据
   */
  private List<MemoryPoolInfoEntity> memoryPoolInfoEntityList;

  public MemoryCommonBean getHeadMomory() {
    return this.headMomory;
  }

  public void setHeadMomory(MemoryCommonBean headMomory) {
    this.headMomory = headMomory;
  }

  public MemoryCommonBean getNoHeadMomory() {
    return this.noHeadMomory;
  }

  public void setNoHeadMomory(MemoryCommonBean noHeadMomory) {
    this.noHeadMomory = noHeadMomory;
  }

  public List<MemoryPoolInfoEntity> getMemoryPoolInfoEntityList() {
    return this.memoryPoolInfoEntityList;
  }

  public void setMemoryPoolInfoEntityList(List<MemoryPoolInfoEntity> memoryPoolInfoEntityList) {
    this.memoryPoolInfoEntityList = memoryPoolInfoEntityList;
  }

  @Override
  public String toString() {
    return "MemoryInfoCallbackBean [headMomory=" + this.headMomory + ", noHeadMomory="
        + this.noHeadMomory + ", memoryPoolInfoEntityList=" + this.memoryPoolInfoEntityList + "]";
  }

  public static class MemoryPoolInfoEntity {
    /**
     * 峰值内存
     */
    private MemoryCommonBean peakMemroy;
    /**
     * 当前内存区域
     */
    private MemoryCommonBean currentMemory;

    /**
     * 名称
     */
    private String name;
    /**
     * 所属管理者名称
     */
    private String managerName;
    /**
     * objectName
     */
    private String objectName;

    public MemoryCommonBean getPeakMemroy() {
      return this.peakMemroy;
    }

    public void setPeakMemroy(MemoryCommonBean peakMemroy) {
      this.peakMemroy = peakMemroy;
    }

    public MemoryCommonBean getCurrentMemory() {
      return this.currentMemory;
    }

    public void setCurrentMemory(MemoryCommonBean currentMemory) {
      this.currentMemory = currentMemory;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getManagerName() {
      return this.managerName;
    }

    public void setManagerName(String managerName) {
      this.managerName = managerName;
    }

    public String getObjectName() {
      return this.objectName;
    }

    public void setObjectName(String objectName) {
      this.objectName = objectName;
    }

    @Override
    public String toString() {
      return "MemoryPoolInfoEntity [peakMemroy=" + this.peakMemroy + ", currentMemory="
          + this.currentMemory + ", name=" + this.name + ", managerName=" + this.managerName
          + ", objectName=" + this.objectName + "]";
    }
  }
}
