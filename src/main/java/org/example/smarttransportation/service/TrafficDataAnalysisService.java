package org.example.smarttransportation.service;

import org.example.smarttransportation.entity.TrafficAccident;
import org.example.smarttransportation.entity.WeatherData;
import org.example.smarttransportation.entity.PermittedEvent;
import org.example.smarttransportation.entity.SubwayRidership;
import org.example.smarttransportation.repository.TrafficAccidentRepository;
import org.example.smarttransportation.repository.WeatherDataRepository;
import org.example.smarttransportation.repository.PermittedEventRepository;
import org.example.smarttransportation.repository.SubwayRidershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 交通数据分析服务
 * 为AI助手提供数据查询和分析能力
 *
 * @author pojin
 * @date 2025/11/23
 */
@Service
public class TrafficDataAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(TrafficDataAnalysisService.class);

    @Autowired
    private TrafficAccidentRepository trafficAccidentRepository;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private PermittedEventRepository permittedEventRepository;

    @Autowired
    private SubwayRidershipRepository subwayRidershipRepository;

    @Autowired
    private NL2SQLService nl2SQLService;

    /**
     * 分析用户查询并返回相关数据摘要
     */
    public String analyzeUserQuery(String userQuery) {
        try {
            // 首先尝试使用NL2SQL服务处理查询
            NL2SQLService.QueryResult queryResult = nl2SQLService.executeQuery(userQuery);

            if (queryResult.isSuccess() && queryResult.getData() != null && !queryResult.getData().isEmpty()) {
                StringBuilder analysis = new StringBuilder();
                analysis.append("【数据查询结果】\n");

                // 显示执行的SQL（用于调试）
                if (queryResult.getSql() != null) {
                    logger.info("执行的SQL: {}", queryResult.getSql());
                }

                // 格式化查询结果
                List<Map<String, Object>> data = queryResult.getData();
                analysis.append(String.format("查询到 %d 条记录：\n", data.size()));

                // 限制显示前10条记录，避免输出过长
                int displayCount = Math.min(data.size(), 10);
                for (int i = 0; i < displayCount; i++) {
                    Map<String, Object> row = data.get(i);
                    analysis.append(String.format("%d. ", i + 1));

                    // 格式化每行数据
                    for (Map.Entry<String, Object> entry : row.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();

                        // 简化字段名显示
                        String displayKey = simplifyFieldName(key);
                        analysis.append(String.format("%s: %s, ", displayKey, value));
                    }

                    // 移除最后的逗号和空格
                    if (analysis.length() > 2) {
                        analysis.setLength(analysis.length() - 2);
                    }
                    analysis.append("\n");
                }

                if (data.size() > displayCount) {
                    analysis.append(String.format("... 还有 %d 条记录\n", data.size() - displayCount));
                }

                return analysis.toString();
            } else {
                // NL2SQL查询失败，回退到传统分析方法
                logger.warn("NL2SQL查询失败: {}", queryResult.getMessage());
                return analyzeUserQueryTraditional(userQuery);
            }

        } catch (Exception e) {
            logger.error("NL2SQL查询异常: {}", e.getMessage());
            // 出现异常时回退到传统分析方法
            return analyzeUserQueryTraditional(userQuery);
        }
    }

    /**
     * 传统的数据分析方法（作为NL2SQL的备选方案）
     */
    private String analyzeUserQueryTraditional(String userQuery) {
        StringBuilder analysis = new StringBuilder();
        String query = userQuery.toLowerCase();

        try {
            // 分析事故相关查询
            if (containsKeywords(query, "事故", "accident", "碰撞", "crash")) {
                String accidentAnalysis = analyzeAccidents(query);
                if (!accidentAnalysis.isEmpty()) {
                    analysis.append("【交通事故分析】\n").append(accidentAnalysis).append("\n\n");
                }
            }

            // 分析天气相关查询
            if (containsKeywords(query, "天气", "weather", "雨", "雪", "风")) {
                String weatherAnalysis = analyzeWeather(query);
                if (!weatherAnalysis.isEmpty()) {
                    analysis.append("【天气数据分析】\n").append(weatherAnalysis).append("\n\n");
                }
            }

            // 分析地铁客流查询
            if (containsKeywords(query, "地铁", "subway", "客流", "ridership")) {
                String ridershipAnalysis = analyzeRidership(query);
                if (!ridershipAnalysis.isEmpty()) {
                    analysis.append("【地铁客流分析】\n").append(ridershipAnalysis).append("\n\n");
                }
            }

            // 分析许可事件查询
            if (containsKeywords(query, "事件", "event", "许可", "permit", "活动")) {
                String eventAnalysis = analyzeEvents(query);
                if (!eventAnalysis.isEmpty()) {
                    analysis.append("【许可事件分析】\n").append(eventAnalysis).append("\n\n");
                }
            }

            // 如果没有具体数据查询，提供总体概况
            if (analysis.length() == 0) {
                analysis.append(getGeneralOverview());
            }

        } catch (Exception e) {
            logger.error("传统数据分析失败: {}", e.getMessage());
            return "数据查询暂时不可用，请稍后再试。";
        }

        return analysis.toString().trim();
    }

    /**
     * 简化字段名显示
     */
    private String simplifyFieldName(String fieldName) {
        if (fieldName == null) return "未知";

        // 处理常见的字段名
        switch (fieldName.toLowerCase()) {
            case "crash date":
            case "`crash date`":
                return "事故日期";
            case "number of persons injured":
            case "`number of persons injured`":
                return "受伤人数";
            case "number of persons killed":
            case "`number of persons killed`":
                return "死亡人数";
            case "on street name":
            case "`on street name`":
                return "街道名称";
            case "borough":
                return "行政区";
            case "start_station_name":
                return "起始站点";
            case "trip_count":
                return "出行次数";
            case "event name":
            case "`event name`":
                return "事件名称";
            case "start date/time":
            case "`start date/time`":
                return "开始时间";
            default:
                return fieldName;
        }
    }

    /**
     * 分析交通事故数据
     */
    private String analyzeAccidents(String query) {
        try {
            // 获取2024年2月的事故数据
            LocalDate startTime = LocalDate.of(2024, 2, 1);
            LocalDate endTime = LocalDate.of(2024, 2, 28);

            List<TrafficAccident> recentAccidents = trafficAccidentRepository
                .findByDateRange(startTime, endTime);

            if (recentAccidents.isEmpty()) {
                return "2024年2月暂无交通事故记录。";
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("2024年2月共发生 %d 起交通事故\n", recentAccidents.size()));

            // 统计严重程度
            long severeAccidents = recentAccidents.stream()
                .filter(TrafficAccident::isSevere)
                .count();

            if (severeAccidents > 0) {
                result.append(String.format("其中严重事故 %d 起\n", severeAccidents));
            }

            // 统计伤亡情况
            int totalInjured = recentAccidents.stream()
                .mapToInt(TrafficAccident::getPersonsInjured)
                .sum();
            int totalKilled = recentAccidents.stream()
                .mapToInt(TrafficAccident::getPersonsKilled)
                .sum();

            if (totalInjured > 0 || totalKilled > 0) {
                result.append(String.format("造成伤亡：受伤 %d 人，死亡 %d 人\n", totalInjured, totalKilled));
            }

            // 分析高发区域
            String topLocation = recentAccidents.stream()
                .collect(Collectors.groupingBy(TrafficAccident::getBorough, Collectors.counting()))
                .entrySet().stream()
                .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .map(e -> e.getKey())
                .orElse("未知");

            result.append(String.format("事故高发区域：%s", topLocation));

            return result.toString();

        } catch (Exception e) {
            logger.error("事故数据分析失败", e);
            return "事故数据分析暂时不可用。";
        }
    }

    /**
     * 分析天气数据
     */
    private String analyzeWeather(String query) {
        try {
            // 获取2024年2月的天气数据
            LocalDate startTime = LocalDate.of(2024, 2, 1);
            LocalDate endTime = LocalDate.of(2024, 2, 28);

            List<WeatherData> recentWeather = weatherDataRepository
                .findByDateRange(startTime, endTime);

            if (recentWeather.isEmpty()) {
                return "2024年2月暂无天气数据记录。";
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("2024年2月天气数据（共 %d 条记录）\n", recentWeather.size()));

            // 统计恶劣天气
            long severeWeatherDays = recentWeather.stream()
                .filter(WeatherData::isSevereWeather)
                .count();

            if (severeWeatherDays > 0) {
                result.append(String.format("恶劣天气天数：%d 天\n", severeWeatherDays));
            }

            // 统计结冰风险
            long icingRiskDays = recentWeather.stream()
                .filter(WeatherData::hasIcingRisk)
                .count();

            if (icingRiskDays > 0) {
                result.append(String.format("道路结冰风险天数：%d 天\n", icingRiskDays));
            }

            // 平均温度
            double avgTemp = recentWeather.stream()
                .mapToDouble(WeatherData::getTemperature)
                .average()
                .orElse(0.0);

            result.append(String.format("平均温度：%.1f°F", avgTemp));

            return result.toString();

        } catch (Exception e) {
            logger.error("天气数据分析失败", e);
            return "天气数据分析暂时不可用。";
        }
    }

    /**
     * 分析地铁客流数据
     */
    private String analyzeRidership(String query) {
        try {
            // 获取2024年2月的地铁客流数据
            LocalDate startTime = LocalDate.of(2024, 2, 1);
            LocalDate endTime = LocalDate.of(2024, 2, 28);

            List<SubwayRidership> recentRidership = subwayRidershipRepository
                .findByDateRange(startTime, endTime);

            if (recentRidership.isEmpty()) {
                return "2024年2月暂无地铁客流数据。";
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("2024年2月地铁客流数据（共 %d 条记录）\n", recentRidership.size()));

            // 计算总客流量
            long totalRidership = recentRidership.stream()
                .mapToLong(SubwayRidership::getRidership)
                .sum();

            result.append(String.format("总客流量：%,d 人次\n", totalRidership));

            // 平均日客流量
            double avgDaily = (double) totalRidership / recentRidership.size();
            result.append(String.format("日均客流量：%,.0f 人次", avgDaily));

            return result.toString();

        } catch (Exception e) {
            logger.error("客流数据分析失败", e);
            return "客流数据分析暂时不可用。";
        }
    }

    /**
     * 分析许可事件数据
     */
    private String analyzeEvents(String query) {
        try {
            // 获取2024年2月的许可事件数据
            LocalDate startTime = LocalDate.of(2024, 2, 1);
            LocalDate endTime = LocalDate.of(2024, 2, 28);

            List<PermittedEvent> recentEvents = permittedEventRepository
                .findByDateRange(startTime, endTime);

            if (recentEvents.isEmpty()) {
                return "2024年2月暂无许可事件记录。";
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("2024年2月许可事件（共 %d 个）\n", recentEvents.size()));

            // 统计高影响事件
            long highImpactEvents = recentEvents.stream()
                .filter(PermittedEvent::isHighImpact)
                .count();

            if (highImpactEvents > 0) {
                result.append(String.format("高影响事件：%d 个\n", highImpactEvents));
            }

            // 统计高峰时段事件
            long rushHourEvents = recentEvents.stream()
                .filter(PermittedEvent::isDuringRushHour)
                .count();

            if (rushHourEvents > 0) {
                result.append(String.format("高峰时段事件：%d 个", rushHourEvents));
            }

            return result.toString();

        } catch (Exception e) {
            logger.error("事件数据分析失败", e);
            return "事件数据分析暂时不可用。";
        }
    }

    /**
     * 获取总体概况
     */
    private String getGeneralOverview() {
        StringBuilder overview = new StringBuilder();
        overview.append("【纽约曼哈顿交通数据概况】\n");
        overview.append("我可以为您分析以下数据：\n");
        overview.append("• 交通事故统计和趋势分析\n");
        overview.append("• 天气条件对交通的影响\n");
        overview.append("• 地铁客流量变化\n");
        overview.append("• 许可事件对交通的影响\n");
        overview.append("请告诉我您想了解哪方面的具体信息。");

        return overview.toString();
    }

    /**
     * 检查查询是否包含关键词
     */
    private boolean containsKeywords(String query, String... keywords) {
        for (String keyword : keywords) {
            if (query.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}