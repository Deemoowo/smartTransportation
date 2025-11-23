package org.example.smarttransportation.config;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Milvus 配置类
 * 
 * @author pojin
 * @date 2025/11/22
 */
@Configuration
public class MilvusConfig {

    @Bean
    @ConditionalOnProperty(name = "milvus.enabled", havingValue = "true", matchIfMissing = false)
    public MilvusServiceClient milvusServiceClient(MilvusProperties milvusProperties) {
        try {
            ConnectParam.Builder builder = ConnectParam.newBuilder()
                    .withHost(milvusProperties.getHost())
                    .withPort(milvusProperties.getPort())
                    .withDatabaseName(milvusProperties.getDatabase());

            // 如果有token则添加
            if (milvusProperties.getToken() != null && !milvusProperties.getToken().isEmpty()) {
                builder.withToken(milvusProperties.getToken());
            }

            ConnectParam connectParam = builder.build();
            return new MilvusServiceClient(connectParam);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to Milvus: " + e.getMessage(), e);
        }
    }

    /**
     * Milvus 配置属性类
     */
    @Component
    @ConfigurationProperties(prefix = "milvus")
    public static class MilvusProperties {
        private String host = "localhost";
        private Integer port = 19530;
        private String uri = "./milvus_data/milvus.db";
        private String token = "";
        private String database = "default";
        private Connection connection = new Connection();

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public Connection getConnection() {
            return connection;
        }

        public void setConnection(Connection connection) {
            this.connection = connection;
        }

        /**
         * 连接配置
         */
        public static class Connection {
            private Long timeout = 30000L;
            private Long keepAliveTime = 30000L;
            private Long keepAliveTimeout = 20000L;
            private Long maxIdleTimeout = 60000L;
            private Integer retryTimes = 3;

            public Long getTimeout() {
                return timeout;
            }

            public void setTimeout(Long timeout) {
                this.timeout = timeout;
            }

            public Long getKeepAliveTime() {
                return keepAliveTime;
            }

            public void setKeepAliveTime(Long keepAliveTime) {
                this.keepAliveTime = keepAliveTime;
            }

            public Long getKeepAliveTimeout() {
                return keepAliveTimeout;
            }

            public void setKeepAliveTimeout(Long keepAliveTimeout) {
                this.keepAliveTimeout = keepAliveTimeout;
            }

            public Long getMaxIdleTimeout() {
                return maxIdleTimeout;
            }

            public void setMaxIdleTimeout(Long maxIdleTimeout) {
                this.maxIdleTimeout = maxIdleTimeout;
            }

            public Integer getRetryTimes() {
                return retryTimes;
            }

            public void setRetryTimes(Integer retryTimes) {
                this.retryTimes = retryTimes;
            }
        }
    }
}
