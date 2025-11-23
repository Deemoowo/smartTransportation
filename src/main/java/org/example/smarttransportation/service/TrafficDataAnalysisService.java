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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    /**
     * 分析用户查询并返回相关数据摘要
     */
    public String analyzeUserQuery(String userQuery) {
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
            logger.error("数据分析失败: {}", e.getMessage());
            return "数据查询暂时不可用，请稍后再试。";
        }

        return analysis.toString().trim();
    }

    /**
     * 分析交通事故数据
     */
    private String analyzeAccidents(String query) {
        try {
            // 获取最近的事故数据
            LocalDateTime endTime = LocalDateTime.now();
            LocalDateTime startTime = endTime.minusDays(30);
            
            List<TrafficAccident> recentAccidents = trafficAccidentRepository
                .findByDateRange(startTime, endTime);

            if (recentAccidents.isEmpty()) {
                return "最近30天内暂无交通事故记录。";
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("最近30天共发生 %d 起交通事故\n", recentAccidents.size()));

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
            LocalDateTime endTime = LocalDateTime.now();
            LocalDateTime startTime = endTime.minusDays(7);
            
            List<WeatherData> recentWeather = weatherDataRepository
                .findByDateRange(startTime, endTime);

            if (recentWeather.isEmpty()) {
                return "最近7天暂无天气数据记录。";
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("最近7天天气数据（共 %d 条记录）\n", recentWeather.size()));

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
            LocalDateTime endTime = LocalDateTime.now();
            LocalDateTime startTime = endTime.minusDays(7);
            
            List<SubwayRidership> recentRidership = subwayRidershipRepository
                .findByDateRange(startTime, endTime);

            if (recentRidership.isEmpty()) {
                return "最近7天暂无地铁客流数据。";
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("最近7天地铁客流数据（共 %d 条记录）\n", recentRidership.size()));

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
            LocalDateTime endTime = LocalDateTime.now();
            LocalDateTime startTime = endTime.minusDays(30);
            
            List<PermittedEvent> recentEvents = permittedEventRepository
                .findByDateRange(startTime, endTime);

            if (recentEvents.isEmpty()) {
                return "最近30天暂无许可事件记录。";
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("最近30天许可事件（共 %d 个）\n", recentEvents.size()));

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
