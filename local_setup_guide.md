# T-Agent 智能交通治理系统本地启动与测试指南

## 系统要求

- Java 17 或更高版本
- Maven 3.8.x 或更高版本
- MySQL 8.0 或更高版本
- Milvus 2.4.x 或更高版本
- Python 3.8 或更高版本（用于知识库初始化脚本）

## 环境准备

### 1. 安装 Java 和 Maven

确保系统已安装 Java 17+ 和 Maven：

```bash
java -version
mvn -version
```

### 2. 安装和配置 MySQL

1. 安装 MySQL 8.0+
2. 创建数据库：
   ```sql
   CREATE DATABASE smart_transportation;
   ```
3. 执行数据库初始化脚本（如果有的话）

### 3. 安装和配置 Milvus

1. 使用 Docker 安装 Milvus：
   ```bash
   # 拉取 Milvus 镜像
   docker pull milvusdb/milvus:v2.4.9
   
   # 启动 Milvus
   docker run -d --name milvus-standalone -p 19530:19530 -p 9091:9091 milvusdb/milvus:v2.4.9
   ```

2. 验证 Milvus 是否正常运行：
   ```bash
   docker logs milvus-standalone
   ```

### 4. 配置应用程序

1. 修改 `src/main/resources/application.properties` 文件中的数据库连接配置：
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/smart_transportation?useSSL=false&serverTimezone=UTC
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

2. 修改 Milvus 连接配置（如果需要）：
   ```properties
   milvus.host=localhost
   milvus.port=19530
   ```

## 启动应用程序

### 1. 构建项目

```bash
mvn clean package
```

### 2. 初始化知识库

运行知识库初始化脚本：

```bash
python init_knowledge_base.py
```

### 3. 启动 Spring Boot 应用

```bash
mvn spring-boot:run
```

或者使用打包后的 jar 文件：

```bash
java -jar target/smart-transportation-0.0.1-SNAPSHOT.jar
```

## 测试系统功能

### 1. API 测试

系统启动后，可以通过以下端点测试功能：

1. **风险预警接口**：
   ```
   POST http://localhost:8080/api/risk/warning
   Content-Type: application/json
   
   {
       "location": "曼哈顿",
       "time": "2024-02-15T08:30:00"
   }
   ```

2. **应急响应接口**：
   ```
   POST http://localhost:8080/api/emergency/response
   Content-Type: application/json
   
   {
       "incidentType": "交通事故",
       "location": "曼哈顿大道与第5街交叉口",
       "severity": "高"
   }
   ```

3. **数据治理接口**：
   ```
   POST http://localhost:8080/api/governance/proposal
   Content-Type: application/json
   
   {
       "location": "曼哈顿中城",
       "startDate": "2024-02-01",
       "endDate": "2024-02-29"
   }
   ```

### 2. 单元测试

运行单元测试：

```bash
mvn test
```

### 3. 集成测试

运行集成测试：

```bash
mvn verify
```

## 常见问题排查

### 1. 数据库连接失败

- 检查 MySQL 服务是否启动
- 验证数据库连接配置是否正确
- 确认数据库用户权限设置

### 2. Milvus 连接失败

- 检查 Milvus 服务是否启动
- 验证 Milvus 连接配置是否正确
- 确认防火墙设置

### 3. 知识库初始化失败

- 检查 Python 环境和依赖包
- 验证 Milvus 连接
- 查看错误日志获取详细信息

## 系统监控

### 1. 日志查看

应用程序日志位于：
```
logs/smart-transportation.log
```

### 2. 性能监控

可以通过 Spring Boot Actuator 端点监控系统性能：
```
http://localhost:8080/actuator
```

## 数据导入

如果需要导入测试数据，可以使用以下方法：

1. 使用 SQL 脚本导入基础数据
2. 通过 API 接口批量导入数据
3. 使用数据迁移工具导入历史数据

## 系统扩展

### 1. 增加新的数据源

- 在 `application.properties` 中配置新的数据源
- 创建相应的 Repository 和 Entity 类
- 更新 Service 层逻辑

### 2. 扩展知识库

- 使用 `init_knowledge_base.py` 脚本添加新的 SOP 和专家知识
- 调整向量化模型以提高检索准确性

## 安全考虑

1. 生产环境中应启用 HTTPS
2. 数据库密码不应明文存储在配置文件中
3. API 接口应添加身份验证和授权机制
4. 定期更新系统依赖包以修复安全漏洞

## 备份与恢复

1. 定期备份 MySQL 数据库
2. 备份 Milvus 向量数据库
3. 保留应用程序配置文件的备份
4. 制定灾难恢复计划