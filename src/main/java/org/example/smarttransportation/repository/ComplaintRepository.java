package org.example.smarttransportation.repository;

import org.example.smarttransportation.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 投诉数据仓库接口
 * 
 * @author T-Agent
 * @version 1.0
 */
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    
    /**
     * 根据位置和时间范围查询投诉数据
     * 
     * @param location 位置
     * @param start 开始时间
     * @param end 结束时间
     * @return 投诉数据列表
     */
    @Query("SELECT c FROM Complaint c WHERE (c.borough LIKE CONCAT('%', :location, '%') OR c.descriptor LIKE CONCAT('%', :location, '%')) AND c.createdAt BETWEEN :start AND :end")
    List<Complaint> findByLocationAndDateRange(@Param("location") String location, 
                                              @Param("start") LocalDateTime start, 
                                              @Param("end") LocalDateTime end);
    
    /**
     * 根据投诉类型查询投诉数据
     * 
     * @param complaintType 投诉类型
     * @return 投诉数据列表
     */
    List<Complaint> findByComplaintType(String complaintType);
    
    /**
     * 根据状态查询投诉数据
     * 
     * @param status 状态
     * @return 投诉数据列表
     */
    List<Complaint> findByStatus(String status);
    
    /**
     * 根据机构查询投诉数据
     * 
     * @param agency 机构
     * @return 投诉数据列表
     */
    List<Complaint> findByAgency(String agency);
    
    /**
     * 查询交通相关投诉
     * 
     * @return 交通相关投诉列表
     */
    @Query("SELECT c FROM Complaint c WHERE LOWER(c.complaintType) LIKE '%traffic%' OR LOWER(c.complaintType) LIKE '%vehicle%' OR LOWER(c.complaintType) LIKE '%parking%' OR LOWER(c.complaintType) LIKE '%noise%' OR LOWER(c.complaintType) LIKE '%street%' OR LOWER(c.complaintType) LIKE '%sidewalk%'")
    List<Complaint> findTrafficRelatedComplaints();
}
