package org.example.smarttransportation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 共享单车出行数据实体
 * 
 * @author pojin
 * @date 2025/11/22
 */
@Entity
@Table(name = "citibike_trips_202402")
@IdClass(CitibikeTripId.class)
public class CitibikeTrip {

    @Id
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Id
    @Column(name = "start_station_name")
    private String startStationName;
    
    @Column(name = "ended_at")
    private LocalDateTime endedAt;
    
    @Column(name = "end_station_name")
    private String endStationName;
    
    @Column(name = "start_lat", precision = 10, scale = 8)
    private BigDecimal startLat;
    
    @Column(name = "start_lng", precision = 11, scale = 8)
    private BigDecimal startLng;
    
    @Column(name = "end_lat", precision = 10, scale = 8)
    private BigDecimal endLat;
    
    @Column(name = "end_lng", precision = 11, scale = 8)
    private BigDecimal endLng;
    
    // Getters and Setters

    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    
    public LocalDateTime getEndedAt() {
        return endedAt;
    }
    
    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }
    
    public String getStartStationName() {
        return startStationName;
    }
    
    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }
    
    public String getEndStationName() {
        return endStationName;
    }
    
    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }
    
    public BigDecimal getStartLat() {
        return startLat;
    }
    
    public void setStartLat(BigDecimal startLat) {
        this.startLat = startLat;
    }
    
    public BigDecimal getStartLng() {
        return startLng;
    }
    
    public void setStartLng(BigDecimal startLng) {
        this.startLng = startLng;
    }
    
    public BigDecimal getEndLat() {
        return endLat;
    }
    
    public void setEndLat(BigDecimal endLat) {
        this.endLat = endLat;
    }
    
    public BigDecimal getEndLng() {
        return endLng;
    }
    
    public void setEndLng(BigDecimal endLng) {
        this.endLng = endLng;
    }
    
    /**
     * 计算行程时长（分钟）
     */
    public long getTripDurationMinutes() {
        if (startedAt != null && endedAt != null) {
            return java.time.Duration.between(startedAt, endedAt).toMinutes();
        }
        return 0;
    }
    
    /**
     * 计算行程距离（简化版本，使用欧几里得距离）
     */
    public double getTripDistance() {
        if (startLat != null && startLng != null && endLat != null && endLng != null) {
            double deltaLat = endLat.doubleValue() - startLat.doubleValue();
            double deltaLng = endLng.doubleValue() - startLng.doubleValue();
            return Math.sqrt(deltaLat * deltaLat + deltaLng * deltaLng) * 111.32; // 转换为公里
        }
        return 0.0;
    }
    
    /**
     * 获取出行时段描述
     */
    public String getTripTimeSlot() {
        if (startedAt == null) {
            return "未知时段";
        }
        
        int hour = startedAt.getHour();
        if (hour >= 7 && hour <= 9) {
            return "早高峰";
        } else if (hour >= 17 && hour <= 19) {
            return "晚高峰";
        } else if (hour >= 22 || hour <= 5) {
            return "深夜时段";
        } else {
            return "平峰时段";
        }
    }
}
