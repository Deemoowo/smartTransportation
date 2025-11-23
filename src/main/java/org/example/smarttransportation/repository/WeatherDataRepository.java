package org.example.smarttransportation.repository;

import org.example.smarttransportation.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 天气数据访问层
 * 
 * @author pojin
 * @date 2025/11/23
 */
@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    /**
     * 根据时间范围查询天气数据
     */
    @Query("SELECT w FROM WeatherData w WHERE w.datetime BETWEEN :startDate AND :endDate ORDER BY w.datetime")
    List<WeatherData> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);

    /**
     * 查询指定日期的天气数据
     */
    @Query("SELECT w FROM WeatherData w WHERE DATE(w.datetime) = DATE(:date)")
    Optional<WeatherData> findByDate(@Param("date") LocalDateTime date);

    /**
     * 查询恶劣天气条件
     */
    @Query("SELECT w FROM WeatherData w WHERE w.datetime BETWEEN :startDate AND :endDate " +
           "AND (w.snow > 5 OR w.snowdepth > 10 OR w.windspeed > 15 OR w.visibility < 1 OR w.severerisk > 50)")
    List<WeatherData> findSevereWeatherConditions(@Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    /**
     * 查询有结冰风险的天气
     */
    @Query("SELECT w FROM WeatherData w WHERE w.datetime BETWEEN :startDate AND :endDate " +
           "AND w.temp BETWEEN -5 AND 2 AND w.precip > 0")
    List<WeatherData> findIcingRiskWeather(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    /**
     * 查询降雪天气
     */
    @Query("SELECT w FROM WeatherData w WHERE w.datetime BETWEEN :startDate AND :endDate " +
           "AND (w.snow > 0 OR w.snowdepth > 0 OR LOWER(w.preciptype) LIKE '%snow%')")
    List<WeatherData> findSnowWeather(@Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);

    /**
     * 查询最近的天气数据
     */
    @Query("SELECT w FROM WeatherData w WHERE w.datetime <= :dateTime ORDER BY w.datetime DESC LIMIT 1")
    Optional<WeatherData> findLatestWeatherBefore(@Param("dateTime") LocalDateTime dateTime);

    /**
     * 按天统计恶劣天气天数
     */
    @Query("SELECT DATE(w.datetime), COUNT(w) FROM WeatherData w " +
           "WHERE w.datetime BETWEEN :startDate AND :endDate " +
           "AND (w.snow > 5 OR w.windspeed > 15 OR w.visibility < 1) " +
           "GROUP BY DATE(w.datetime) ORDER BY DATE(w.datetime)")
    List<Object[]> countSevereWeatherDays(@Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);
}
