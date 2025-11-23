package org.example.smarttransportation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 城市投诉数据实体
 * 
 * @author pojin
 * @date 2025/11/22
 */
@Entity
@Table(name = "complaints")
public class Complaint extends TransportationData {
    
    @Id
    @Column(name = "unique_key")
    private Long uniqueKey;
    
    @Column(name = "closed_at")
    private LocalDateTime closedAt;
    
    @Column(name = "agency", length = 64)
    private String agency;
    
    @Column(name = "complaint_type", length = 128)
    private String complaintType;
    
    @Column(name = "descriptor")
    private String descriptor;
    
    @Column(name = "status", length = 64)
    private String status;
    
    @Column(name = "resolution_description", columnDefinition = "TEXT")
    private String resolutionDescription;
    
    // Getters and Setters
    public Long getUniqueKey() {
        return uniqueKey;
    }
    
    public void setUniqueKey(Long uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
    
    public LocalDateTime getClosedAt() {
        return closedAt;
    }
    
    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }
    
    public String getAgency() {
        return agency;
    }
    
    public void setAgency(String agency) {
        this.agency = agency;
    }
    
    public String getComplaintType() {
        return complaintType;
    }
    
    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }
    
    public String getDescriptor() {
        return descriptor;
    }
    
    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getResolutionDescription() {
        return resolutionDescription;
    }
    
    public void setResolutionDescription(String resolutionDescription) {
        this.resolutionDescription = resolutionDescription;
    }
    
    /**
     * 获取处理时长（小时）
     */
    public long getProcessingTimeHours() {
        if (getCreatedAt() != null && closedAt != null) {
            return java.time.Duration.between(getCreatedAt(), closedAt).toHours();
        }
        return 0;
    }
    
    /**
     * 判断是否为交通相关投诉
     */
    public boolean isTrafficRelated() {
        if (complaintType == null) {
            return false;
        }
        
        String type = complaintType.toLowerCase();
        return type.contains("traffic") || type.contains("vehicle") || 
               type.contains("parking") || type.contains("noise") ||
               type.contains("street") || type.contains("sidewalk");
    }
    
    /**
     * 获取投诉严重程度
     */
    public String getSeverityLevel() {
        if (descriptor == null) {
            return "未知";
        }
        
        String desc = descriptor.toLowerCase();
        if (desc.contains("loud") || desc.contains("dangerous") || desc.contains("blocking")) {
            return "高";
        } else if (desc.contains("minor") || desc.contains("occasional")) {
            return "低";
        } else {
            return "中";
        }
    }
}
