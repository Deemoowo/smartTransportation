package org.example.smarttransportation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 许可事件数据实体
 * 
 * @author pojin
 * @date 2025/11/23
 */
@Entity
@Table(name = "nyc_permitted_events")
public class PermittedEvent extends TransportationData {

    @Id
    @Column(name = "event_id")
    private Long eventId;
    
    @Column(name = "event_name", length = 256)
    private String eventName;
    
    @Column(name = "start_at")
    private LocalDateTime startAt;
    
    @Column(name = "end_at")
    private LocalDateTime endAt;
    
    @Column(name = "event_borough", length = 64)
    private String eventBorough;
    
    @Column(name = "event_location", length = 256)
    private String eventLocation;
    
    @Column(name = "event_street_side", length = 32)
    private String eventStreetSide;
    
    @Column(name = "street_closure_type", length = 64)
    private String streetClosureType;
    
    @Column(name = "geocode_query", length = 256)
    private String geocodeQuery;
    
    @Column(name = "geocode_status", length = 32)
    private String geocodeStatus;

    // Getters and Setters
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public String getEventBorough() {
        return eventBorough;
    }

    public void setEventBorough(String eventBorough) {
        this.eventBorough = eventBorough;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventStreetSide() {
        return eventStreetSide;
    }

    public void setEventStreetSide(String eventStreetSide) {
        this.eventStreetSide = eventStreetSide;
    }

    public String getStreetClosureType() {
        return streetClosureType;
    }

    public void setStreetClosureType(String streetClosureType) {
        this.streetClosureType = streetClosureType;
    }

    public String getGeocodeQuery() {
        return geocodeQuery;
    }

    public void setGeocodeQuery(String geocodeQuery) {
        this.geocodeQuery = geocodeQuery;
    }

    public String getGeocodeStatus() {
        return geocodeStatus;
    }

    public void setGeocodeStatus(String geocodeStatus) {
        this.geocodeStatus = geocodeStatus;
    }

    /**
     * 获取事件影响等级
     */
    public String getImpactLevel() {
        if (streetClosureType == null) {
            return "未知";
        }
        
        String closureType = streetClosureType.toLowerCase();
        if (closureType.contains("full street closure")) {
            return "高影响";
        } else if (closureType.contains("sidewalk and curb lane closure")) {
            return "中影响";
        } else if (closureType.contains("curb lane only") || closureType.contains("partial")) {
            return "低影响";
        } else {
            return "轻微影响";
        }
    }

    /**
     * 获取事件持续时间（小时）
     */
    public long getDurationHours() {
        if (startAt == null || endAt == null) {
            return 0;
        }
        
        return java.time.Duration.between(startAt, endAt).toHours();
    }

    /**
     * 判断是否为长期事件（超过24小时）
     */
    public boolean isLongTermEvent() {
        return getDurationHours() > 24;
    }

    /**
     * 判断是否在高峰时段
     */
    public boolean isDuringRushHour() {
        if (startAt == null) return false;
        
        int startHour = startAt.getHour();
        int endHour = endAt != null ? endAt.getHour() : startHour;
        
        // 早高峰 7-9点 或 晚高峰 17-19点
        boolean morningRush = (startHour >= 7 && startHour <= 9) || (endHour >= 7 && endHour <= 9);
        boolean eveningRush = (startHour >= 17 && startHour <= 19) || (endHour >= 17 && endHour <= 19);
        
        return morningRush || eveningRush;
    }

    /**
     * 判断是否为大型活动
     */
    public boolean isMajorEvent() {
        if (eventName == null) return false;
        
        String name = eventName.toLowerCase();
        return name.contains("parade") || name.contains("festival") || 
               name.contains("marathon") || name.contains("concert") ||
               name.contains("fair") || getDurationHours() > 8;
    }

    /**
     * 获取事件类型
     */
    public String getEventType() {
        if (eventName == null) return "未知事件";
        
        String name = eventName.toLowerCase();
        if (name.contains("parade")) return "游行活动";
        else if (name.contains("festival")) return "节庆活动";
        else if (name.contains("marathon") || name.contains("run")) return "体育赛事";
        else if (name.contains("concert") || name.contains("show")) return "演出活动";
        else if (name.contains("construction") || name.contains("work")) return "施工作业";
        else if (name.contains("filming") || name.contains("production")) return "影视拍摄";
        else if (name.contains("market") || name.contains("fair")) return "市集活动";
        else return "其他活动";
    }

    /**
     * 获取风险评估
     */
    public String getRiskAssessment() {
        int riskScore = 0;
        
        // 影响等级评分
        String impact = getImpactLevel();
        if ("高影响".equals(impact)) riskScore += 3;
        else if ("中影响".equals(impact)) riskScore += 2;
        else if ("低影响".equals(impact)) riskScore += 1;
        
        // 时段评分
        if (isDuringRushHour()) riskScore += 2;
        
        // 持续时间评分
        if (isLongTermEvent()) riskScore += 1;
        
        // 大型活动评分
        if (isMajorEvent()) riskScore += 2;
        
        if (riskScore >= 6) return "高风险";
        else if (riskScore >= 4) return "中风险";
        else if (riskScore >= 2) return "低风险";
        else return "无风险";
    }

    /**
     * 判断是否为高影响事件
     */
    public boolean isHighImpact() {
        String impact = getImpactLevel();
        return "高影响".equals(impact) || isMajorEvent() || isDuringRushHour();
    }
}