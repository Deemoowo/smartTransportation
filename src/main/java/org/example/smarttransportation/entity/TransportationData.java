package org.example.smarttransportation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 交通数据基础实体类
 * 
 * @author pojin
 * @date 2025/11/22
 */
@MappedSuperclass
public abstract class TransportationData {
    
    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;
    
    @Column(name = "longitude", precision = 11, scale = 7)
    private BigDecimal longitude;
    
    @Column(name = "borough", length = 64)
    private String borough;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Getters and Setters
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    public String getBorough() {
        return borough;
    }
    
    public void setBorough(String borough) {
        this.borough = borough;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * 获取地理位置描述
     */
    public String getLocationDescription() {
        if (latitude != null && longitude != null) {
            return String.format("纬度: %s, 经度: %s", latitude, longitude);
        }
        return "位置信息不可用";
    }
    
    /**
     * 计算与指定坐标的距离（简化版本）
     */
    public double calculateDistance(BigDecimal targetLat, BigDecimal targetLng) {
        if (latitude == null || longitude == null || targetLat == null || targetLng == null) {
            return Double.MAX_VALUE;
        }
        
        double lat1 = latitude.doubleValue();
        double lon1 = longitude.doubleValue();
        double lat2 = targetLat.doubleValue();
        double lon2 = targetLng.doubleValue();
        
        // 使用简化的欧几里得距离计算
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;
        
        return Math.sqrt(deltaLat * deltaLat + deltaLon * deltaLon);
    }
}
