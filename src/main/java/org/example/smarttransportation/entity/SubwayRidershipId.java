package org.example.smarttransportation.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * SubwayRidership复合主键类
 * 
 * @author pojin
 * @date 2025/11/23
 */
public class SubwayRidershipId implements Serializable {
    
    private LocalDateTime transitTimestamp;
    private Integer stationComplexId;
    
    public SubwayRidershipId() {}
    
    public SubwayRidershipId(LocalDateTime transitTimestamp, Integer stationComplexId) {
        this.transitTimestamp = transitTimestamp;
        this.stationComplexId = stationComplexId;
    }
    
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubwayRidershipId that = (SubwayRidershipId) o;
        return Objects.equals(transitTimestamp, that.transitTimestamp) && 
               Objects.equals(stationComplexId, that.stationComplexId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(transitTimestamp, stationComplexId);
    }
}
