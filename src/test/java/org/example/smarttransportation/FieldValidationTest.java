package org.example.smarttransportation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * 字段验证测试 - 验证所有表的字段是否可以正常访问
 */
@SpringBootTest
public class FieldValidationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testAllTableFields() {
        System.out.println("=== 开始验证所有表的字段 ===\n");

        // 1. 测试 citibike_trips_202402
        testTable("citibike_trips_202402", new String[]{
            "start_station_name", "started_at", "end_lat", "end_lng", 
            "end_station_name", "ended_at", "start_lat", "start_lng"
        });

        // 2. 测试 complaints
        testTable("complaints", new String[]{
            "unique_key", "borough", "created_at", "latitude", "longitude", 
            "agency", "closed_at", "complaint_type", "descriptor", 
            "resolution_description", "status"
        });

        // 3. 测试 nyc_traffic_accidents - 使用反引号的字段
        testTableWithBackticks("nyc_traffic_accidents", new String[]{
            "`CRASH DATE`", "`CRASH TIME`", "`CRASH_DATETIME`", "borough", 
            "`ZIP CODE`", "latitude", "longitude", "`LOCATION`", 
            "`ON STREET NAME`", "`CROSS STREET NAME`", "`OFF STREET NAME`",
            "`NUMBER OF PERSONS INJURED`", "`NUMBER OF PERSONS KILLED`",
            "`NUMBER OF PEDESTRIANS INJURED`", "`NUMBER OF PEDESTRIANS KILLED`",
            "`NUMBER OF CYCLIST INJURED`", "`NUMBER OF CYCLIST KILLED`",
            "`NUMBER OF MOTORIST INJURED`", "`NUMBER OF MOTORIST KILLED`",
            "`CONTRIBUTING FACTOR VEHICLE 1`", "`CONTRIBUTING FACTOR VEHICLE 2`",
            "collision_id", "`VEHICLE TYPE CODE 1`", "`VEHICLE TYPE CODE 2`", "created_at"
        });

        // 4. 测试 nyc_traffic_accidents - 小写字段（如果存在）
        testTableLowercaseFields("nyc_traffic_accidents", new String[]{
            "crash_date", "crash_time", "crash_datetime", "cross_street_name",
            "number_of_cyclist_injured", "number_of_cyclist_killed",
            "number_of_motorist_injured", "number_of_motorist_killed",
            "number_of_pedestrians_injured", "number_of_pedestrians_killed",
            "number_of_persons_injured", "number_of_persons_killed",
            "off_street_name", "on_street_name", "unique_key"
        });

        // 5. 测试 nyc_permitted_events - 使用反引号的字段
        testTableWithBackticks("nyc_permitted_events", new String[]{
            "`Event ID`", "`Event Name`", "`Start Date/Time`", "`End Date/Time`",
            "`Event Borough`", "`Event Location`", "`Event Street Side`",
            "`Street Closure Type`", "`Processed_Location`", "`Location_Type`",
            "latitude", "longitude", "geocode_query"
        });

        // 6. 测试 nyc_permitted_events - 小写字段（如果存在）
        testTableLowercaseFields("nyc_permitted_events", new String[]{
            "event_id", "borough", "created_at", "end_at", "event_borough",
            "event_location", "event_name", "event_street_side",
            "geocode_status", "start_at", "street_closure_type"
        });

        // 7. 测试 subway_ridership
        testTable("subway_ridership", new String[]{
            "station_complex_id", "transit_timestamp", "borough", "created_at",
            "latitude", "longitude", "ridership", "station_complex", "stratum"
        });

        System.out.println("\n=== 字段验证完成 ===");
    }

    private void testTable(String tableName, String[] fields) {
        System.out.println("测试表: " + tableName);
        for (String field : fields) {
            try {
                String sql = String.format("SELECT %s FROM %s LIMIT 1", field, tableName);
                jdbcTemplate.queryForList(sql);
                System.out.println("  ✓ " + field + " - 可访问");
            } catch (Exception e) {
                System.err.println("  ✗ " + field + " - 错误: " + e.getMessage());
            }
        }
        System.out.println();
    }

    private void testTableWithBackticks(String tableName, String[] fields) {
        System.out.println("测试表（带反引号字段）: " + tableName);
        for (String field : fields) {
            try {
                String sql = String.format("SELECT %s FROM %s LIMIT 1", field, tableName);
                jdbcTemplate.queryForList(sql);
                System.out.println("  ✓ " + field + " - 可访问");
            } catch (Exception e) {
                System.err.println("  ✗ " + field + " - 错误: " + e.getMessage());
            }
        }
        System.out.println();
    }

    private void testTableLowercaseFields(String tableName, String[] fields) {
        System.out.println("测试表（小写字段）: " + tableName);
        for (String field : fields) {
            try {
                String sql = String.format("SELECT %s FROM %s LIMIT 1", field, tableName);
                jdbcTemplate.queryForList(sql);
                System.out.println("  ✓ " + field + " - 可访问");
            } catch (Exception e) {
                System.err.println("  ✗ " + field + " - 不存在或无法访问");
            }
        }
        System.out.println();
    }

    @Test
    public void testCommonQueries() {
        System.out.println("=== 测试常见查询 ===\n");

        // 测试1: 查询2024年2月10日的交通事故
        testQuery("查询2024年2月10日的交通事故",
            "SELECT * FROM nyc_traffic_accidents WHERE `CRASH DATE` = '2024-02-10' LIMIT 5");

        // 测试2: 使用小写字段名查询
        testQuery("使用小写crash_datetime查询",
            "SELECT crash_datetime FROM nyc_traffic_accidents WHERE `CRASH DATE` = '2024-02-10' LIMIT 5");

        // 测试3: 查询许可事件
        testQuery("查询2024年2月的许可事件",
            "SELECT `Event Name`, `Start Date/Time` FROM nyc_permitted_events WHERE `Start Date/Time` >= '2024-02-01' LIMIT 5");

        // 测试4: 使用小写字段名查询许可事件
        testQuery("使用小写event_name查询",
            "SELECT event_name, start_at FROM nyc_permitted_events WHERE start_at >= '2024-02-01' LIMIT 5");

        System.out.println("\n=== 常见查询测试完成 ===");
    }

    private void testQuery(String description, String sql) {
        System.out.println("测试: " + description);
        System.out.println("SQL: " + sql);
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
            System.out.println("  ✓ 查询成功，返回 " + results.size() + " 条记录");
            if (!results.isEmpty()) {
                System.out.println("  字段列表: " + results.get(0).keySet());
            }
        } catch (Exception e) {
            System.err.println("  ✗ 查询失败: " + e.getMessage());
        }
        System.out.println();
    }
}
