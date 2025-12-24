package org.example.smarttransportation.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 模型配置类
 * 管理多个AI模型的Bean定义
 * 
 * @author pojin
 * @date 2025/12/24
 */
@Configuration
public class ModelConfig {

    /**
     * OpenAI 兼容模型（Qwen3-coder-plus）
     * 设置为默认模型
     */
    @Bean
    @Primary
    @Qualifier("openAiChatModel")
    public ChatModel openAiChatModel(OpenAiChatModel openAiChatModel) {
        return openAiChatModel;
    }

    /**
     * DashScope 模型（Qwen-plus）
     */
    @Bean
    @Qualifier("dashScopeChatModel")
    public ChatModel dashScopeChatModel(DashScopeChatModel dashScopeChatModel) {
        return dashScopeChatModel;
    }
}
