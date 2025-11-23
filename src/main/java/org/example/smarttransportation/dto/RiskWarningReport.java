package org.example.smarttransportation.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 交通风险预警通报DTO
 * 
 * @author pojin
 * @date 2025/11/23
 */
public class RiskWarningReport {
    
    private String reportId;
    private LocalDateTime generatedAt;
    private String riskLevel;
    private String riskType;
    private String timeWindow;
    private String affectedArea;
    private RiskAnalysis riskAnalysis;
    private List<HighRiskZone> highRiskZones;
    private List<String> recommendations;
    private String sopReference;
    
    // 构造函数
    public RiskWarningReport() {
        this.generatedAt = LocalDateTime.now();
        this.reportId = "RWR-" + System.currentTimeMillis();
    }
    
    // Getters and Setters
    public String getReportId() {
        return reportId;
    }
    
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
    
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
    
    public String getRiskLevel() {
        return riskLevel;
    }
    
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
    
    public String getRiskType() {
        return riskType;
    }
    
    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }
    
    public String getTimeWindow() {
        return timeWindow;
    }
    
    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow;
    }
    
    public String getAffectedArea() {
        return affectedArea;
    }
    
    public void setAffectedArea(String affectedArea) {
        this.affectedArea = affectedArea;
    }
    
    public RiskAnalysis getRiskAnalysis() {
        return riskAnalysis;
    }
    
    public void setRiskAnalysis(RiskAnalysis riskAnalysis) {
        this.riskAnalysis = riskAnalysis;
    }
    
    public List<HighRiskZone> getHighRiskZones() {
        return highRiskZones;
    }
    
    public void setHighRiskZones(List<HighRiskZone> highRiskZones) {
        this.highRiskZones = highRiskZones;
    }
    
    public List<String> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
    
    public String getSopReference() {
        return sopReference;
    }
    
    public void setSopReference(String sopReference) {
        this.sopReference = sopReference;
    }
    
    /**
     * 风险分析详情
     */
    public static class RiskAnalysis {
        private WeatherRisk weatherRisk;
        private TrafficRisk trafficRisk;
        private EventRisk eventRisk;
        private int overallRiskScore;
        private String riskFactors;
        
        // Getters and Setters
        public WeatherRisk getWeatherRisk() {
            return weatherRisk;
        }
        
        public void setWeatherRisk(WeatherRisk weatherRisk) {
            this.weatherRisk = weatherRisk;
        }
        
        public TrafficRisk getTrafficRisk() {
            return trafficRisk;
        }
        
        public void setTrafficRisk(TrafficRisk trafficRisk) {
            this.trafficRisk = trafficRisk;
        }
        
        public EventRisk getEventRisk() {
            return eventRisk;
        }
        
        public void setEventRisk(EventRisk eventRisk) {
            this.eventRisk = eventRisk;
        }
        
        public int getOverallRiskScore() {
            return overallRiskScore;
        }
        
        public void setOverallRiskScore(int overallRiskScore) {
            this.overallRiskScore = overallRiskScore;
        }
        
        public String getRiskFactors() {
            return riskFactors;
        }
        
        public void setRiskFactors(String riskFactors) {
            this.riskFactors = riskFactors;
        }
    }
    
    /**
     * 天气风险
     */
    public static class WeatherRisk {
        private boolean hasSnow;
        private boolean hasIcingRisk;
        private boolean isSevereWeather;
        private String weatherDescription;
        private int riskScore;
        
        // Getters and Setters
        public boolean isHasSnow() {
            return hasSnow;
        }
        
        public void setHasSnow(boolean hasSnow) {
            this.hasSnow = hasSnow;
        }
        
        public boolean isHasIcingRisk() {
            return hasIcingRisk;
        }
        
        public void setHasIcingRisk(boolean hasIcingRisk) {
            this.hasIcingRisk = hasIcingRisk;
        }
        
        public boolean isSevereWeather() {
            return isSevereWeather;
        }
        
        public void setSevereWeather(boolean severeWeather) {
            isSevereWeather = severeWeather;
        }
        
        public String getWeatherDescription() {
            return weatherDescription;
        }
        
        public void setWeatherDescription(String weatherDescription) {
            this.weatherDescription = weatherDescription;
        }
        
        public int getRiskScore() {
            return riskScore;
        }
        
        public void setRiskScore(int riskScore) {
            this.riskScore = riskScore;
        }
    }
    
    /**
     * 交通风险
     */
    public static class TrafficRisk {
        private boolean isRushHour;
        private int accidentCount;
        private int highDensityStations;
        private String trafficPattern;
        private int riskScore;
        
        // Getters and Setters
        public boolean isRushHour() {
            return isRushHour;
        }
        
        public void setRushHour(boolean rushHour) {
            isRushHour = rushHour;
        }
        
        public int getAccidentCount() {
            return accidentCount;
        }
        
        public void setAccidentCount(int accidentCount) {
            this.accidentCount = accidentCount;
        }
        
        public int getHighDensityStations() {
            return highDensityStations;
        }
        
        public void setHighDensityStations(int highDensityStations) {
            this.highDensityStations = highDensityStations;
        }
        
        public String getTrafficPattern() {
            return trafficPattern;
        }
        
        public void setTrafficPattern(String trafficPattern) {
            this.trafficPattern = trafficPattern;
        }
        
        public int getRiskScore() {
            return riskScore;
        }
        
        public void setRiskScore(int riskScore) {
            this.riskScore = riskScore;
        }
    }
    
    /**
     * 事件风险
     */
    public static class EventRisk {
        private int activeEvents;
        private int highImpactEvents;
        private String eventTypes;
        private int riskScore;
        
        // Getters and Setters
        public int getActiveEvents() {
            return activeEvents;
        }
        
        public void setActiveEvents(int activeEvents) {
            this.activeEvents = activeEvents;
        }
        
        public int getHighImpactEvents() {
            return highImpactEvents;
        }
        
        public void setHighImpactEvents(int highImpactEvents) {
            this.highImpactEvents = highImpactEvents;
        }
        
        public String getEventTypes() {
            return eventTypes;
        }
        
        public void setEventTypes(String eventTypes) {
            this.eventTypes = eventTypes;
        }
        
        public int getRiskScore() {
            return riskScore;
        }
        
        public void setRiskScore(int riskScore) {
            this.riskScore = riskScore;
        }
    }
    
    /**
     * 高风险区域
     */
    public static class HighRiskZone {
        private String zoneName;
        private String location;
        private String riskLevel;
        private String riskFactors;
        private List<String> deploymentSuggestions;
        private Double latitude;
        private Double longitude;
        
        // Getters and Setters
        public String getZoneName() {
            return zoneName;
        }
        
        public void setZoneName(String zoneName) {
            this.zoneName = zoneName;
        }
        
        public String getLocation() {
            return location;
        }
        
        public void setLocation(String location) {
            this.location = location;
        }
        
        public String getRiskLevel() {
            return riskLevel;
        }
        
        public void setRiskLevel(String riskLevel) {
            this.riskLevel = riskLevel;
        }
        
        public String getRiskFactors() {
            return riskFactors;
        }
        
        public void setRiskFactors(String riskFactors) {
            this.riskFactors = riskFactors;
        }
        
        public List<String> getDeploymentSuggestions() {
            return deploymentSuggestions;
        }
        
        public void setDeploymentSuggestions(List<String> deploymentSuggestions) {
            this.deploymentSuggestions = deploymentSuggestions;
        }
        
        public Double getLatitude() {
            return latitude;
        }
        
        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
        
        public Double getLongitude() {
            return longitude;
        }
        
        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }
}
