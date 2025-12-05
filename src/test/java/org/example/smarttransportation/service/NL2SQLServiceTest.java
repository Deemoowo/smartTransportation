package org.example.smarttransportation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * NL2SQL服务测试类
 * 用于验证生成的SQL语句是否能在MySQL中正确执行
 */
@SpringBootTest
@ActiveProfiles("test")
public class NL2SQLServiceTest {

    @Autowired
    private NL2SQLService nl2SQLService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 实际数据库表字段映射
    private Map<String, Set<String>> tableFields;

    @BeforeEach
    public void setUp() {
        // 初始化实际数据库表字段映射
        tableFields = Map.of(
            "nyc_traffic_accidents", Set.of(
                "CRASH DATE", "CRASH TIME", "borough", "ZIP CODE", "latitude", "longitude",
                "LOCATION", "ON STREET NAME", "CROSS STREET NAME", "OFF STREET NAME",
                "NUMBER OF PERSONS INJURED", "NUMBER OF PERSONS KILLED",
                "NUMBER OF PEDESTRIANS INJURED", "NUMBER OF PEDESTRIANS KILLED",
                "NUMBER OF CYCLIST INJURED", "NUMBER OF CYCLIST KILLED",
                "NUMBER OF MOTORIST INJURED", "NUMBER OF MOTORIST KILLED",
                "CONTRIBUTING FACTOR VEHICLE 1", "CONTRIBUTING FACTOR VEHICLE 2",
                "collision_id", "VEHICLE TYPE CODE 1", "VEHICLE TYPE CODE 2",
                "CRASH_DATETIME", "created_at", "crash_date", "crash_time",
                "cross_street_name", "number_of_cyclist_injured", "number_of_cyclist_killed",
                "number_of_motorist_injured", "number_of_motorist_killed",
                "number_of_pedestrians_injured", "number_of_pedestrians_killed",
                "number_of_persons_injured", "number_of_persons_killed",
                "off_street_name", "on_street_name", "unique_key"
            ),
            "nyc_permitted_events", Set.of(
                "Event ID", "Event Name", "Start Date/Time", "End Date/Time",
                "Event Borough", "Event Location", "Event Street Side",
                "Street Closure Type", "Processed_Location", "Location_Type",
                "latitude", "longitude", "geocode_query", "event_id", "borough",
                "created_at", "end_at", "event_borough", "event_location",
                "event_name", "event_street_side", "geocode_status", "start_at",
                "street_closure_type"
            ),
            "citibike_trips_202402", Set.of(
                "start_station_name", "started_at", "end_lat", "end_lng",
                "end_station_name", "ended_at", "start_lat", "start_lng"
            ),
            "subway_ridership", Set.of(
                "station_complex_id", "transit_timestamp", "borough", "created_at",
                "latitude", "longitude", "ridership", "station_complex", "stratum"
            ),
            "complaints", Set.of(
                "unique_key", "borough", "created_at", "latitude", "longitude",
                "agency", "closed_at", "complaint_type", "descriptor",
                "resolution_description", "status"
            )
        );
    }

    /**
     * 测试各种自然语言查询生成的SQL
     */
    @Test
    public void testSQLGeneration() {
        // 测试查询列表
        List<String> testQueries = Arrays.asList(
            "交通事故有多少起？",
            "最严重的交通事故在哪里？",
            "各区域的事故数量统计",
            "受伤人数最多的事故",
            "2024年2月的交通事故统计",
            "共享单车最繁忙的站点",
            "地铁客流量最高的站点",
            "本月有哪些许可事件？",
            "道路封闭活动统计",
            "投诉类型统计"
        );

        System.out.println("=== NL2SQL测试报告 ===");
        System.out.println();

        int totalQueries = testQueries.size();
        int successfulQueries = 0;
        int failedQueries = 0;

        for (int i = 0; i < testQueries.size(); i++) {
            String query = testQueries.get(i);
            System.out.printf("%d. 测试查询: %s%n", i + 1, query);
            
            try {
                // 生成SQL
                String generatedSQL = nl2SQLService.generateSQL(query);
                System.out.printf("   生成的SQL: %s%n", generatedSQL);
                
                if (generatedSQL == null || generatedSQL.trim().isEmpty()) {
                    System.out.println("   ❌ 错误: 生成的SQL为空");
                    failedQueries++;
                    continue;
                }
                
                // 验证SQL语法和安全性
                if (!isValidAndSafeSQL(generatedSQL)) {
                    System.out.println("   ❌ 错误: SQL验证失败（语法或安全性问题）");
                    failedQueries++;
                    continue;
                }
                
                // 尝试执行SQL
                try {
                    List<java.util.Map<String, Object>> results = jdbcTemplate.queryForList(generatedSQL);
                    System.out.printf("   ✅ 成功: 查询返回 %d 条记录%n", results.size());
                    
                    // 显示前3条结果作为示例
                    if (!results.isEmpty()) {
                        System.out.println("   示例结果:");
                        for (int j = 0; j < Math.min(3, results.size()); j++) {
                            System.out.printf("     %s%n", results.get(j));
                        }
                    }
                    successfulQueries++;
                    
                } catch (Exception e) {
                    System.out.printf("   ❌ 执行错误: %s%n", e.getMessage());
                    failedQueries++;
                }
                
            } catch (Exception e) {
                System.out.printf("   ❌ 生成错误: %s%n", e.getMessage());
                failedQueries++;
            }
            
            System.out.println();
        }

        // 输出测试总结
        System.out.println("=== 测试总结 ===");
        System.out.printf("总查询数: %d%n", totalQueries);
        System.out.printf("成功: %d (%.1f%%)%n", successfulQueries, (double) successfulQueries / totalQueries * 100);
        System.out.printf("失败: %d (%.1f%%)%n", failedQueries, (double) failedQueries / totalQueries * 100);
        
        if (failedQueries > 0) {
            System.out.println();
            System.out.println("⚠️  发现SQL生成问题，需要修复NL2SQLService");
        } else {
            System.out.println();
            System.out.println("🎉 所有SQL查询都能正确执行！");
        }
    }

    /**
     * 测试特定的问题场景
     */
    @Test
    public void testProblematicQueries() {
        System.out.println("=== 测试问题场景 ===");
        
        // 测试可能导致字段名错误的查询
        List<String> problematicQueries = Arrays.asList(
            "查询事故日期和受伤人数",  // 可能会生成错误的字段名
            "按事故时间统计",         // 测试时间字段
            "事件名称和开始时间",     // 测试许可事件字段
            "地铁站点复合体统计"      // 测试地铁数据字段
        );

        for (String query : problematicQueries) {
            System.out.printf("测试: %s%n", query);
            
            try {
                String sql = nl2SQLService.generateSQL(query);
                System.out.printf("生成的SQL: %s%n", sql);
                
                // 检查是否包含不存在的字段名
                if (containsInvalidFields(sql)) {
                    System.out.println("❌ 发现无效字段名");
                } else {
                    System.out.println("✅ 字段名验证通过");
                }
                
                // 尝试执行
                jdbcTemplate.queryForList(sql);
                System.out.println("✅ SQL执行成功");
                
            } catch (Exception e) {
                System.out.printf("❌ 错误: %s%n", e.getMessage());
            }
            
            System.out.println();
        }
    }

    /**
     * 验证SQL是否有效且安全
     */
    private boolean isValidAndSafeSQL(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            return false;
        }
        
        String upperSQL = sql.toUpperCase().trim();
        
        // 必须以SELECT开头
        if (!upperSQL.startsWith("SELECT")) {
            return false;
        }
        
        // 不能包含危险关键词
        String[] forbiddenKeywords = {
            "DROP", "DELETE", "UPDATE", "INSERT", "ALTER", "CREATE", "TRUNCATE"
        };
        
        for (String keyword : forbiddenKeywords) {
            if (upperSQL.contains(keyword)) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * 检查SQL是否包含无效的字段名
     */
    private boolean containsInvalidFields(String sql) {
        String lowerSQL = sql.toLowerCase();
        
        // 提取SQL中涉及的表名
        Set<String> tablesInSQL = new HashSet<>();
        for (String tableName : tableFields.keySet()) {
            if (lowerSQL.contains(tableName.toLowerCase())) {
                tablesInSQL.add(tableName);
            }
        }

        if (tablesInSQL.isEmpty()) {
            return false; // 没有识别到表名，跳过验证
        }

        // 检查每个表的字段是否存在
        for (String tableName : tablesInSQL) {
            Set<String> validFields = tableFields.get(tableName);
            if (validFields == null) continue;

            // 检查SQL中是否包含不存在的字段
            // 这里使用简单的字符串匹配，实际应该使用SQL解析器
            for (String field : validFields) {
                String fieldLower = field.toLowerCase();
                // 检查是否使用了错误的字段名格式
                if (field.contains(" ")) {
                    // 带空格的字段名，检查是否正确使用了反引号
                    String withoutBackticks = fieldLower.replace(" ", "_");
                    if (lowerSQL.contains(withoutBackticks) && !lowerSQL.contains("`" + field.toLowerCase() + "`")) {
                        System.out.printf("警告：字段 '%s' 应该使用反引号格式 `%s` 而不是 %s%n",
                                        field, field, withoutBackticks);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 测试规则匹配生成的SQL
     */
    @Test
    public void testRuleBasedSQL() {
        System.out.println("=== 测试规则匹配SQL生成 ===");
        
        List<String> ruleQueries = Arrays.asList(
            "事故统计",
            "单车站点",
            "地铁客流",
            "许可事件",
            "投诉类型"
        );

        for (String query : ruleQueries) {
            try {
                // 使用注入的NL2SQLService实例，它会自动回退到规则匹配
                String sql = nl2SQLService.generateSQL(query);
                System.out.printf("查询: %s%n", query);
                System.out.printf("SQL: %s%n", sql);

                // 验证字段名
                if (containsInvalidFields(sql)) {
                    System.out.println("⚠️  发现字段名问题");
                }

                // 验证执行
                List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
                System.out.printf("✅ 执行成功，返回 %d 条记录%n", results.size());
                
            } catch (Exception e) {
                System.out.printf("❌ 错误: %s%n", e.getMessage());
            }
            System.out.println();
        }
    }
}
