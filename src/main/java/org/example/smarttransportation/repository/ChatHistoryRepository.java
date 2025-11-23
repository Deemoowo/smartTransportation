package org.example.smarttransportation.repository;

import org.example.smarttransportation.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 对话历史数据访问层
 * 
 * @author pojin
 * @date 2025/11/23
 */
@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

    /**
     * 根据会话ID查询对话历史
     */
    List<ChatHistory> findBySessionIdOrderByCreatedAtAsc(String sessionId);

    /**
     * 查询最近的对话历史
     */
    @Query("SELECT c FROM ChatHistory c WHERE c.sessionId = :sessionId " +
           "ORDER BY c.createdAt DESC")
    List<ChatHistory> findRecentChatsBySessionId(@Param("sessionId") String sessionId);

    /**
     * 查询涉及数据查询的对话
     */
    List<ChatHistory> findByInvolvesDataQueryTrueOrderByCreatedAtDesc();

    /**
     * 根据时间范围查询对话
     */
    @Query("SELECT c FROM ChatHistory c WHERE c.createdAt BETWEEN :startTime AND :endTime " +
           "ORDER BY c.createdAt DESC")
    List<ChatHistory> findByTimeRange(@Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);

    /**
     * 查询包含特定关键词的对话
     */
    @Query("SELECT c FROM ChatHistory c WHERE c.userMessage LIKE %:keyword% " +
           "OR c.assistantMessage LIKE %:keyword% ORDER BY c.createdAt DESC")
    List<ChatHistory> findByKeyword(@Param("keyword") String keyword);

    /**
     * 统计会话数量
     */
    @Query("SELECT COUNT(DISTINCT c.sessionId) FROM ChatHistory c")
    Long countDistinctSessions();

    /**
     * 删除指定时间之前的对话历史
     */
    void deleteByCreatedAtBefore(LocalDateTime cutoffTime);
}
