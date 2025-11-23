package org.example.smarttransportation.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * CitibikeTrip复合主键类
 * 
 * @author pojin
 * @date 2025/11/23
 */
public class CitibikeTripId implements Serializable {
    
    private LocalDateTime startedAt;
    private String startStationName;
    
    public CitibikeTripId() {}
    
    public CitibikeTripId(LocalDateTime startedAt, String startStationName) {
        this.startedAt = startedAt;
        this.startStationName = startStationName;
    }
    
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    
    public String getStartStationName() {
        return startStationName;
    }
    
    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CitibikeTripId that = (CitibikeTripId) o;
        return Objects.equals(startedAt, that.startedAt) && 
               Objects.equals(startStationName, that.startStationName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(startedAt, startStationName);
    }
}
