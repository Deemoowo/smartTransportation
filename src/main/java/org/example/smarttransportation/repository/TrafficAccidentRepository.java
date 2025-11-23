package org.example.smarttransportation.repository;

import org.example.smarttransportation.entity.TrafficAccident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 交通事故数据访问层
 * 
 * @author pojin
 * @date 2025/11/23
 */
@Repository
public interface TrafficAccidentRepository extends JpaRepository<TrafficAccident, Long> {

    /**
     * 根据时间范围查询事故
     */
    @Query("SELECT t FROM TrafficAccident t WHERE t.crashDate BETWEEN :startDate AND :endDate")
    List<TrafficAccident> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate);

    /**
     * 查询指定时间范围内的高峰时段事故
     */
    @Query("SELECT t FROM TrafficAccident t WHERE t.crashDate BETWEEN :startDate AND :endDate " +
           "AND (HOUR(t.crashDate) BETWEEN 7 AND 9 OR HOUR(t.crashDate) BETWEEN 17 AND 19)")
    List<TrafficAccident> findRushHourAccidents(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

    /**
     * 查询天气相关事故
     */
    @Query("SELECT t FROM TrafficAccident t WHERE t.crashDate BETWEEN :startDate AND :endDate " +
           "AND (LOWER(t.contributingFactorVehicle1) LIKE %:weatherFactor% " +
           "OR LOWER(t.contributingFactorVehicle2) LIKE %:weatherFactor%)")
    List<TrafficAccident> findWeatherRelatedAccidents(@Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate,
                                                      @Param("weatherFactor") String weatherFactor);

    /**
     * 按街道统计事故数量
     */
    @Query("SELECT t.onStreetName, COUNT(t) FROM TrafficAccident t " +
           "WHERE t.crashDate BETWEEN :startDate AND :endDate " +
           "AND t.onStreetName IS NOT NULL " +
           "GROUP BY t.onStreetName ORDER BY COUNT(t) DESC")
    List<Object[]> countAccidentsByStreet(@Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);

    /**
     * 查询严重事故（有伤亡）
     */
    @Query("SELECT t FROM TrafficAccident t WHERE t.crashDate BETWEEN :startDate AND :endDate " +
           "AND (t.numberOfPersonsInjured > 0 OR t.numberOfPersonsKilled > 0)")
    List<TrafficAccident> findSevereAccidents(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    /**
     * 按时段统计事故数量
     */
    @Query("SELECT HOUR(t.crashDate), COUNT(t) FROM TrafficAccident t " +
           "WHERE t.crashDate BETWEEN :startDate AND :endDate " +
           "GROUP BY HOUR(t.crashDate) ORDER BY HOUR(t.crashDate)")
    List<Object[]> countAccidentsByHour(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);
}
