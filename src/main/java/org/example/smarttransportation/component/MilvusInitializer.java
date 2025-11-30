package org.example.smarttransportation.component;

import org.example.smarttransportation.service.VectorStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Milvus 初始化组件
 * 在应用启动完成后自动初始化 Milvus 集合
 */
@Component
public class MilvusInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(MilvusInitializer.class);
    
    @Autowired
    private VectorStoreService vectorStoreService;
    
    /**
     * 应用启动完成后自动初始化 Milvus 集合
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initializeMilvusCollections() {
        try {
            logger.info("开始初始化 Milvus 集合...");
            boolean success = vectorStoreService.initializeCollection();
            if (success) {
                logger.info("Milvus 集合初始化成功");
            } else {
                logger.warn("Milvus 集合初始化失败");
            }
        } catch (Exception e) {
            logger.error("初始化 Milvus 集合时发生错误: {}", e.getMessage(), e);
        }
    }
}
