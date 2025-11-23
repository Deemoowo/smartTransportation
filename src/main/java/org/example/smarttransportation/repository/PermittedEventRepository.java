package org.example.smarttransportation.repository;

import org.example.smarttransportation.entity.PermittedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 许可事件数据访问层
 * 
 * @author pojin
 * @date 2025/11/23
 */
@Repository
public interface PermittedEventRepository extends JpaRepository<PermittedEvent, Long> {

    /**
     * 根据时间范围查询事件
     */
    @Query("SELECT p FROM PermittedEvent p WHERE p.startAt <= :endDate AND p.endAt >= :startDate")
    List<PermittedEvent> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);

    /**
     * 查询指定区域的事件
     */
    @Query("SELECT p FROM PermittedEvent p WHERE p.eventBorough = :borough " +
           "AND p.startAt <= :endDate AND p.endAt >= :startDate")
    List<PermittedEvent> findByBoroughAndDateRange(@Param("borough") String borough,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    /**
     * 查询高影响事件
     */
    @Query("SELECT p FROM PermittedEvent p WHERE p.startAt <= :endDate AND p.endAt >= :startDate " +
           "AND LOWER(p.streetClosureType) LIKE '%full street closure%'")
    List<PermittedEvent> findHighImpactEvents(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    /**
     * 查询高峰时段事件
     */
    @Query("SELECT p FROM PermittedEvent p WHERE p.startAt <= :endDate AND p.endAt >= :startDate " +
           "AND ((HOUR(p.startAt) BETWEEN 7 AND 9) OR (HOUR(p.endAt) BETWEEN 7 AND 9) " +
           "OR (HOUR(p.startAt) BETWEEN 17 AND 19) OR (HOUR(p.endAt) BETWEEN 17 AND 19))")
    List<PermittedEvent> findRushHourEvents(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    /**
     * 查询长期事件（超过24小时）
     */
    @Query("SELECT p FROM PermittedEvent p WHERE p.startAt <= :endDate AND p.endAt >= :startDate " +
           "AND FUNCTION('DATEDIFF', p.endAt, p.startAt) >= 1")
    List<PermittedEvent> findLongTermEvents(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    /**
     * 按事件类型统计
     */
    @Query("SELECT " +
           "CASE " +
           "  WHEN LOWER(p.eventName) LIKE '%parade%' THEN '游行活动' " +
           "  WHEN LOWER(p.eventName) LIKE '%festival%' THEN '节庆活动' " +
           "  WHEN LOWER(p.eventName) LIKE '%construction%' THEN '施工作业' " +
           "  WHEN LOWER(p.eventName) LIKE '%filming%' THEN '影视拍摄' " +
           "  ELSE '其他活动' " +
           "END, COUNT(p) " +
           "FROM PermittedEvent p " +
           "WHERE p.startAt <= :endDate AND p.endAt >= :startDate " +
           "GROUP BY " +
           "CASE " +
           "  WHEN LOWER(p.eventName) LIKE '%parade%' THEN '游行活动' " +
           "  WHEN LOWER(p.eventName) LIKE '%festival%' THEN '节庆活动' " +
           "  WHEN LOWER(p.eventName) LIKE '%construction%' THEN '施工作业' " +
           "  WHEN LOWER(p.eventName) LIKE '%filming%' THEN '影视拍摄' " +
           "  ELSE '其他活动' " +
           "END")
    List<Object[]> countEventsByType(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);

    /**
     * 按街道统计事件数量
     */
    @Query("SELECT p.eventLocation, COUNT(p) FROM PermittedEvent p " +
           "WHERE p.startAt <= :endDate AND p.endAt >= :startDate " +
           "AND p.eventLocation IS NOT NULL " +
           "GROUP BY p.eventLocation ORDER BY COUNT(p) DESC")
    List<Object[]> countEventsByLocation(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);
}
