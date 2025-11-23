package org.example.smarttransportation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 天气数据实体
 * 
 * @author pojin
 * @date 2025/11/23
 */
@Entity
@Table(name = "nyc_weather_data")
public class WeatherData extends TransportationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "datetime")
    private LocalDateTime datetime;
    
    @Column(name = "tempmax", precision = 5, scale = 2)
    private BigDecimal tempmax;
    
    @Column(name = "tempmin", precision = 5, scale = 2)
    private BigDecimal tempmin;
    
    @Column(name = "temp", precision = 5, scale = 2)
    private BigDecimal temp;
    
    @Column(name = "feelslikemax", precision = 5, scale = 2)
    private BigDecimal feelslikemax;
    
    @Column(name = "feelslikemin", precision = 5, scale = 2)
    private BigDecimal feelslikemin;
    
    @Column(name = "feelslike", precision = 5, scale = 2)
    private BigDecimal feelslike;
    
    @Column(name = "dew", precision = 5, scale = 2)
    private BigDecimal dew;
    
    @Column(name = "humidity", precision = 5, scale = 2)
    private BigDecimal humidity;
    
    @Column(name = "precip", precision = 5, scale = 2)
    private BigDecimal precip;
    
    @Column(name = "precipprob", precision = 5, scale = 2)
    private BigDecimal precipprob;
    
    @Column(name = "precipcover", precision = 5, scale = 2)
    private BigDecimal precipcover;
    
    @Column(name = "preciptype", length = 64)
    private String preciptype;
    
    @Column(name = "snow", precision = 5, scale = 2)
    private BigDecimal snow;
    
    @Column(name = "snowdepth", precision = 5, scale = 2)
    private BigDecimal snowdepth;
    
    @Column(name = "windgust", precision = 5, scale = 2)
    private BigDecimal windgust;
    
    @Column(name = "windspeed", precision = 5, scale = 2)
    private BigDecimal windspeed;
    
    @Column(name = "winddir", precision = 5, scale = 2)
    private BigDecimal winddir;
    
    @Column(name = "sealevelpressure", precision = 7, scale = 2)
    private BigDecimal sealevelpressure;
    
    @Column(name = "cloudcover", precision = 5, scale = 2)
    private BigDecimal cloudcover;
    
    @Column(name = "visibility", precision = 5, scale = 2)
    private BigDecimal visibility;
    
    @Column(name = "solarradiation", precision = 5, scale = 2)
    private BigDecimal solarradiation;
    
    @Column(name = "solarenergy", precision = 5, scale = 2)
    private BigDecimal solarenergy;
    
    @Column(name = "uvindex", precision = 3, scale = 1)
    private BigDecimal uvindex;
    
    @Column(name = "severerisk", precision = 5, scale = 2)
    private BigDecimal severerisk;
    
    @Column(name = "sunrise")
    private String sunrise;
    
    @Column(name = "sunset")
    private String sunset;
    
    @Column(name = "moonphase", precision = 3, scale = 2)
    private BigDecimal moonphase;
    
    @Column(name = "conditions", length = 128)
    private String conditions;
    
    @Column(name = "description", length = 256)
    private String description;
    
    @Column(name = "icon", length = 32)
    private String icon;
    
    @Column(name = "stations", length = 128)
    private String stations;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getTempmax() {
        return tempmax;
    }

    public void setTempmax(BigDecimal tempmax) {
        this.tempmax = tempmax;
    }

    public BigDecimal getTempmin() {
        return tempmin;
    }

    public void setTempmin(BigDecimal tempmin) {
        this.tempmin = tempmin;
    }

    public BigDecimal getTemp() {
        return temp;
    }

    public void setTemp(BigDecimal temp) {
        this.temp = temp;
    }

    public BigDecimal getFeelslikemax() {
        return feelslikemax;
    }

    public void setFeelslikemax(BigDecimal feelslikemax) {
        this.feelslikemax = feelslikemax;
    }

    public BigDecimal getFeelslikemin() {
        return feelslikemin;
    }

    public void setFeelslikemin(BigDecimal feelslikemin) {
        this.feelslikemin = feelslikemin;
    }

    public BigDecimal getFeelslike() {
        return feelslike;
    }

    public void setFeelslike(BigDecimal feelslike) {
        this.feelslike = feelslike;
    }

    public BigDecimal getDew() {
        return dew;
    }

    public void setDew(BigDecimal dew) {
        this.dew = dew;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    public BigDecimal getPrecip() {
        return precip;
    }

    public void setPrecip(BigDecimal precip) {
        this.precip = precip;
    }

    public BigDecimal getPrecipprob() {
        return precipprob;
    }

    public void setPrecipprob(BigDecimal precipprob) {
        this.precipprob = precipprob;
    }

    public BigDecimal getPrecipcover() {
        return precipcover;
    }

    public void setPrecipcover(BigDecimal precipcover) {
        this.precipcover = precipcover;
    }

    public String getPreciptype() {
        return preciptype;
    }

    public void setPreciptype(String preciptype) {
        this.preciptype = preciptype;
    }

    public BigDecimal getSnow() {
        return snow;
    }

    public void setSnow(BigDecimal snow) {
        this.snow = snow;
    }

    public BigDecimal getSnowdepth() {
        return snowdepth;
    }

    public void setSnowdepth(BigDecimal snowdepth) {
        this.snowdepth = snowdepth;
    }

    public BigDecimal getWindgust() {
        return windgust;
    }

    public void setWindgust(BigDecimal windgust) {
        this.windgust = windgust;
    }

    public BigDecimal getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(BigDecimal windspeed) {
        this.windspeed = windspeed;
    }

    public BigDecimal getWinddir() {
        return winddir;
    }

    public void setWinddir(BigDecimal winddir) {
        this.winddir = winddir;
    }

    public BigDecimal getSealevelpressure() {
        return sealevelpressure;
    }

    public void setSealevelpressure(BigDecimal sealevelpressure) {
        this.sealevelpressure = sealevelpressure;
    }

    public BigDecimal getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(BigDecimal cloudcover) {
        this.cloudcover = cloudcover;
    }

    public BigDecimal getVisibility() {
        return visibility;
    }

    public void setVisibility(BigDecimal visibility) {
        this.visibility = visibility;
    }

    public BigDecimal getSolarradiation() {
        return solarradiation;
    }

    public void setSolarradiation(BigDecimal solarradiation) {
        this.solarradiation = solarradiation;
    }

    public BigDecimal getSolarenergy() {
        return solarenergy;
    }

    public void setSolarenergy(BigDecimal solarenergy) {
        this.solarenergy = solarenergy;
    }

    public BigDecimal getUvindex() {
        return uvindex;
    }

    public void setUvindex(BigDecimal uvindex) {
        this.uvindex = uvindex;
    }

    public BigDecimal getSevererisk() {
        return severerisk;
    }

    public void setSevererisk(BigDecimal severerisk) {
        this.severerisk = severerisk;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public BigDecimal getMoonphase() {
        return moonphase;
    }

    public void setMoonphase(BigDecimal moonphase) {
        this.moonphase = moonphase;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStations() {
        return stations;
    }

    public void setStations(String stations) {
        this.stations = stations;
    }

    /**
     * 判断是否为恶劣天气
     */
    public boolean isSevereWeather() {
        // 暴雪条件：降雪量 > 5cm 或 降雪深度 > 10cm
        boolean isBlizzard = (snow != null && snow.compareTo(BigDecimal.valueOf(5)) > 0) ||
                           (snowdepth != null && snowdepth.compareTo(BigDecimal.valueOf(10)) > 0);
        
        // 强风条件：风速 > 15 m/s
        boolean isStrongWind = windspeed != null && windspeed.compareTo(BigDecimal.valueOf(15)) > 0;
        
        // 低能见度条件：能见度 < 1km
        boolean isLowVisibility = visibility != null && visibility.compareTo(BigDecimal.valueOf(1)) < 0;
        
        // 严重天气风险
        boolean isHighRisk = severerisk != null && severerisk.compareTo(BigDecimal.valueOf(50)) > 0;
        
        return isBlizzard || isStrongWind || isLowVisibility || isHighRisk;
    }

    /**
     * 判断是否有结冰风险
     */
    public boolean hasIcingRisk() {
        // 温度在冰点附近且有降水
        boolean nearFreezing = temp != null && temp.compareTo(BigDecimal.valueOf(2)) <= 0 && 
                              temp.compareTo(BigDecimal.valueOf(-5)) >= 0;
        boolean hasPrecipitation = precip != null && precip.compareTo(BigDecimal.ZERO) > 0;
        
        return nearFreezing && hasPrecipitation;
    }

    /**
     * 获取天气风险等级
     */
    public String getWeatherRiskLevel() {
        if (isSevereWeather()) {
            return "高风险";
        } else if (hasIcingRisk()) {
            return "中风险";
        } else if (precipprob != null && precipprob.compareTo(BigDecimal.valueOf(70)) > 0) {
            return "低风险";
        } else {
            return "无风险";
        }
    }

    /**
     * 获取天气描述
     */
    public String getWeatherDescription() {
        if (conditions != null) {
            return conditions;
        } else if (description != null) {
            return description;
        } else {
            return "天气状况未知";
        }
    }

    /**
     * 获取温度值（摄氏度）
     */
    public double getTemperature() {
        return temp != null ? temp.doubleValue() : 0.0;
    }
}