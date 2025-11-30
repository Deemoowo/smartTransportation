package org.example.smarttransportation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 交通事故数据实体
 * 
 * @author pojin
 * @date 2025/11/23
 */
@Entity
@Table(name = "nyc_traffic_accidents")
public class TrafficAccident extends TransportationData {

    @Id
    @Column(name = "collision_id")
    private Long collisionId;
    
    @Column(name = "crash_date")
    private LocalDateTime crashDate;
    
    @Column(name = "crash_time")
    private String crashTime;
    
    @Column(name = "on_street_name", length = 128)
    private String onStreetName;
    
    @Column(name = "cross_street_name", length = 128)
    private String crossStreetName;
    
    @Column(name = "off_street_name", length = 128)
    private String offStreetName;
    
    @Column(name = "number_of_persons_injured")
    private Integer numberOfPersonsInjured;
    
    @Column(name = "number_of_persons_killed")
    private Integer numberOfPersonsKilled;
    
    @Column(name = "number_of_pedestrians_injured")
    private Integer numberOfPedestriansInjured;
    
    @Column(name = "number_of_pedestrians_killed")
    private Integer numberOfPedestriansKilled;
    
    @Column(name = "number_of_cyclist_injured")
    private Integer numberOfCyclistInjured;
    
    @Column(name = "number_of_cyclist_killed")
    private Integer numberOfCyclistKilled;
    
    @Column(name = "number_of_motorist_injured")
    private Integer numberOfMotoristInjured;
    
    @Column(name = "number_of_motorist_killed")
    private Integer numberOfMotoristKilled;
    
    @Column(name = "contributing_factor_vehicle_1", length = 128)
    private String contributingFactorVehicle1;
    
    @Column(name = "contributing_factor_vehicle_2", length = 128)
    private String contributingFactorVehicle2;
    
    @Column(name = "contributing_factor_vehicle_3", length = 128)
    private String contributingFactorVehicle3;
    
    @Column(name = "contributing_factor_vehicle_4", length = 128)
    private String contributingFactorVehicle4;
    
    @Column(name = "contributing_factor_vehicle_5", length = 128)
    private String contributingFactorVehicle5;
    
    @Column(name = "unique_key")
    private String uniqueKey;
    
    @Column(name = "vehicle_type_code1", length = 64)
    private String vehicleTypeCode1;
    
    @Column(name = "vehicle_type_code2", length = 64)
    private String vehicleTypeCode2;
    
    @Column(name = "vehicle_type_code_3", length = 64)
    private String vehicleTypeCode3;
    
    @Column(name = "vehicle_type_code_4", length = 64)
    private String vehicleTypeCode4;
    
    @Column(name = "vehicle_type_code_5", length = 64)
    private String vehicleTypeCode5;

    // Getters and Setters
    public Long getCollisionId() {
        return collisionId;
    }

    public void setCollisionId(Long collisionId) {
        this.collisionId = collisionId;
    }

    public LocalDateTime getCrashDate() {
        return crashDate;
    }

    public void setCrashDate(LocalDateTime crashDate) {
        this.crashDate = crashDate;
    }

    public String getCrashTime() {
        return crashTime;
    }

    public void setCrashTime(String crashTime) {
        this.crashTime = crashTime;
    }

    public String getOnStreetName() {
        return onStreetName;
    }

    public void setOnStreetName(String onStreetName) {
        this.onStreetName = onStreetName;
    }

    public String getCrossStreetName() {
        return crossStreetName;
    }

    public void setCrossStreetName(String crossStreetName) {
        this.crossStreetName = crossStreetName;
    }

    public String getOffStreetName() {
        return offStreetName;
    }

    public void setOffStreetName(String offStreetName) {
        this.offStreetName = offStreetName;
    }

    public Integer getNumberOfPersonsInjured() {
        return numberOfPersonsInjured;
    }

    public void setNumberOfPersonsInjured(Integer numberOfPersonsInjured) {
        this.numberOfPersonsInjured = numberOfPersonsInjured;
    }

    public Integer getNumberOfPersonsKilled() {
        return numberOfPersonsKilled;
    }

    public void setNumberOfPersonsKilled(Integer numberOfPersonsKilled) {
        this.numberOfPersonsKilled = numberOfPersonsKilled;
    }

    public Integer getNumberOfPedestriansInjured() {
        return numberOfPedestriansInjured;
    }

    public void setNumberOfPedestriansInjured(Integer numberOfPedestriansInjured) {
        this.numberOfPedestriansInjured = numberOfPedestriansInjured;
    }

    public Integer getNumberOfPedestriansKilled() {
        return numberOfPedestriansKilled;
    }

    public void setNumberOfPedestriansKilled(Integer numberOfPedestriansKilled) {
        this.numberOfPedestriansKilled = numberOfPedestriansKilled;
    }

    public Integer getNumberOfCyclistInjured() {
        return numberOfCyclistInjured;
    }

    public void setNumberOfCyclistInjured(Integer numberOfCyclistInjured) {
        this.numberOfCyclistInjured = numberOfCyclistInjured;
    }

    public Integer getNumberOfCyclistKilled() {
        return numberOfCyclistKilled;
    }

    public void setNumberOfCyclistKilled(Integer numberOfCyclistKilled) {
        this.numberOfCyclistKilled = numberOfCyclistKilled;
    }

    public Integer getNumberOfMotoristInjured() {
        return numberOfMotoristInjured;
    }

    public void setNumberOfMotoristInjured(Integer numberOfMotoristInjured) {
        this.numberOfMotoristInjured = numberOfMotoristInjured;
    }

    public Integer getNumberOfMotoristKilled() {
        return numberOfMotoristKilled;
    }

    public void setNumberOfMotoristKilled(Integer numberOfMotoristKilled) {
        this.numberOfMotoristKilled = numberOfMotoristKilled;
    }

    public String getContributingFactorVehicle1() {
        return contributingFactorVehicle1;
    }

    public void setContributingFactorVehicle1(String contributingFactorVehicle1) {
        this.contributingFactorVehicle1 = contributingFactorVehicle1;
    }

    public String getContributingFactorVehicle2() {
        return contributingFactorVehicle2;
    }

    public void setContributingFactorVehicle2(String contributingFactorVehicle2) {
        this.contributingFactorVehicle2 = contributingFactorVehicle2;
    }

    public String getContributingFactorVehicle3() {
        return contributingFactorVehicle3;
    }

    public void setContributingFactorVehicle3(String contributingFactorVehicle3) {
        this.contributingFactorVehicle3 = contributingFactorVehicle3;
    }

    public String getContributingFactorVehicle4() {
        return contributingFactorVehicle4;
    }

    public void setContributingFactorVehicle4(String contributingFactorVehicle4) {
        this.contributingFactorVehicle4 = contributingFactorVehicle4;
    }

    public String getContributingFactorVehicle5() {
        return contributingFactorVehicle5;
    }

    public void setContributingFactorVehicle5(String contributingFactorVehicle5) {
        this.contributingFactorVehicle5 = contributingFactorVehicle5;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getVehicleTypeCode1() {
        return vehicleTypeCode1;
    }

    public void setVehicleTypeCode1(String vehicleTypeCode1) {
        this.vehicleTypeCode1 = vehicleTypeCode1;
    }

    public String getVehicleTypeCode2() {
        return vehicleTypeCode2;
    }

    public void setVehicleTypeCode2(String vehicleTypeCode2) {
        this.vehicleTypeCode2 = vehicleTypeCode2;
    }

    public String getVehicleTypeCode3() {
        return vehicleTypeCode3;
    }

    public void setVehicleTypeCode3(String vehicleTypeCode3) {
        this.vehicleTypeCode3 = vehicleTypeCode3;
    }

    public String getVehicleTypeCode4() {
        return vehicleTypeCode4;
    }

    public void setVehicleTypeCode4(String vehicleTypeCode4) {
        this.vehicleTypeCode4 = vehicleTypeCode4;
    }

    public String getVehicleTypeCode5() {
        return vehicleTypeCode5;
    }

    public void setVehicleTypeCode5(String vehicleTypeCode5) {
        this.vehicleTypeCode5 = vehicleTypeCode5;
    }

    /**
     * 获取事故严重程度
     */
    public String getSeverityLevel() {
        int totalKilled = (numberOfPersonsKilled != null ? numberOfPersonsKilled : 0);
        int totalInjured = (numberOfPersonsInjured != null ? numberOfPersonsInjured : 0);
        
        if (totalKilled > 0) {
            return "致命";
        } else if (totalInjured >= 5) {
            return "严重";
        } else if (totalInjured > 0) {
            return "轻微";
        } else {
            return "财产损失";
        }
    }

    /**
     * 获取事故时段描述
     */
    public String getTimeSlotDescription() {
        if (crashDate == null) {
            return "未知时段";
        }
        
        int hour = crashDate.getHour();
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

    /**
     * 是否涉及恶劣天气因素
     */
    public boolean isWeatherRelated() {
        String factor1 = contributingFactorVehicle1 != null ? contributingFactorVehicle1.toLowerCase() : "";
        String factor2 = contributingFactorVehicle2 != null ? contributingFactorVehicle2.toLowerCase() : "";
        
        return factor1.contains("snow") || factor1.contains("ice") || factor1.contains("rain") ||
               factor2.contains("snow") || factor2.contains("ice") || factor2.contains("rain");
    }

    /**
     * 判断是否为严重事故
     */
    public boolean isSevere() {
        int totalKilled = (numberOfPersonsKilled != null ? numberOfPersonsKilled : 0);
        int totalInjured = (numberOfPersonsInjured != null ? numberOfPersonsInjured : 0);

        return totalKilled > 0 || totalInjured >= 3;
    }

    /**
     * 获取受伤人数
     */
    public int getPersonsInjured() {
        return numberOfPersonsInjured != null ? numberOfPersonsInjured : 0;
    }

    /**
     * 获取死亡人数
     */
    public int getPersonsKilled() {
        return numberOfPersonsKilled != null ? numberOfPersonsKilled : 0;
    }
}