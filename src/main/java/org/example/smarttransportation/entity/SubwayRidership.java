package org.example.smarttransportation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 地铁客流数据实体
 * 
 * @author pojin
 * @date 2025/11/22
 */
@Entity
@Table(name = "subway_ridership")
@IdClass(SubwayRidershipId.class)
public class SubwayRidership extends TransportationData {

    @Id
    @Column(name = "transit_timestamp")
    private LocalDateTime transitTimestamp;

    @Id
    @Column(name = "station_complex_id")
    private Integer stationComplexId;


    
    @Column(name = "station_complex", length = 128)
    private String stationComplex;
    
    @Column(name = "ridership")
    private Integer ridership;
    
    @Column(name = "stratum", length = 128)
    private String stratum;
    
    // Getters and Setters

    public LocalDateTime getTransitTimestamp() {
        return transitTimestamp;
    }
    
    public void setTransitTimestamp(LocalDateTime transitTimestamp) {
        this.transitTimestamp = transitTimestamp;
    }
    
    public Integer getStationComplexId() {
        return stationComplexId;
    }
    
    public void setStationComplexId(Integer stationComplexId) {
        this.stationComplexId = stationComplexId;
    }
    
    public String getStationComplex() {
        return stationComplex;
    }
    
    public void setStationComplex(String stationComplex) {
        this.stationComplex = stationComplex;
    }
    
    public Integer getRidership() {
        return ridership;
    }
    
    public void setRidership(Integer ridership) {
        this.ridership = ridership;
    }
    
    public String getStratum() {
        return stratum;
    }
    
    public void setStratum(String stratum) {
        this.stratum = stratum;
    }
    
    /**
     * 获取客流密度等级
     */
    public String getRidershipLevel() {
        if (ridership == null) {
            return "未知";
        }
        
        if (ridership >= 1000) {
            return "高密度";
        } else if (ridership >= 500) {
            return "中密度";
        } else if (ridership >= 100) {
            return "低密度";
        } else {
            return "极低密度";
        }
    }
    
    /**
     * 获取时段描述
     */
    public String getTimeSlotDescription() {
        if (transitTimestamp == null) return "未知时段";
        
        int hour = transitTimestamp.getHour();
        if (hour >= 7 && hour <= 9) return "早高峰";
        else if (hour >= 17 && hour <= 19) return "晚高峰";
        else if (hour >= 22 || hour <= 5) return "深夜时段";
        else return "平峰时段";
    }
}
