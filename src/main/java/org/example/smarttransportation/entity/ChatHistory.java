package org.example.smarttransportation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 对话历史实体类
 * 
 * @author pojin
 * @date 2025/11/23
 */
@Entity
@Table(name = "chat_history")
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会话ID，用于关联同一次对话的多轮交互
     */
    @Column(name = "session_id", nullable = false, length = 64)
    private String sessionId;

    /**
     * 用户输入的问题
     */
    @Column(name = "user_message", columnDefinition = "TEXT")
    private String userMessage;

    /**
     * AI助手的回复
     */
    @Column(name = "assistant_message", columnDefinition = "TEXT")
    private String assistantMessage;

    /**
     * 消息类型：user/assistant/system
     */
    @Column(name = "message_type", length = 20)
    private String messageType;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 对话上下文信息（JSON格式）
     */
    @Column(name = "context_info", columnDefinition = "TEXT")
    private String contextInfo;

    /**
     * 是否涉及数据查询
     */
    @Column(name = "involves_data_query")
    private Boolean involvesDataQuery = false;

    /**
     * 查询的数据表名
     */
    @Column(name = "queried_tables", length = 500)
    private String queriedTables;

    public ChatHistory() {
        this.createdAt = LocalDateTime.now();
    }

    public ChatHistory(String sessionId, String userMessage, String assistantMessage) {
        this();
        this.sessionId = sessionId;
        this.userMessage = userMessage;
        this.assistantMessage = assistantMessage;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getAssistantMessage() {
        return assistantMessage;
    }

    public void setAssistantMessage(String assistantMessage) {
        this.assistantMessage = assistantMessage;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(String contextInfo) {
        this.contextInfo = contextInfo;
    }

    public Boolean getInvolvesDataQuery() {
        return involvesDataQuery;
    }

    public void setInvolvesDataQuery(Boolean involvesDataQuery) {
        this.involvesDataQuery = involvesDataQuery;
    }

    public String getQueriedTables() {
        return queriedTables;
    }

    public void setQueriedTables(String queriedTables) {
        this.queriedTables = queriedTables;
    }
}
