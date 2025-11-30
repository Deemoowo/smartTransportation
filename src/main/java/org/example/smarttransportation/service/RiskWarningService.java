package org.example.smarttransportation.service;

import org.example.smarttransportation.dto.RiskWarningReport;
import org.example.smarttransportation.entity.*;
import org.example.smarttransportation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 风险预警服务
 * 实现场景一：事前·主动风险预警 (Proactive Risk Warning)
 * 
 * @author pojin
 * @date 2025/11/23
 */
@Service
public class RiskWarningService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;
    
    @Autowired
    private TrafficAccidentRepository trafficAccidentRepository;
    
    @Autowired
    private PermittedEventRepository permittedEventRepository;
    
    @Autowired
    private SubwayRidershipRepository subwayRidershipRepository;

    /**
     * 生成风险预警通报
     * 核心算法：识别"暴雪+晚高峰+道路结冰隐患"的二级风险
     */
    public RiskWarningReport generateRiskWarning(LocalDateTime targetDateTime) {
        RiskWarningReport report = new RiskWarningReport();
        
        // 设置基本信息
        report.setTimeWindow(formatTimeWindow(targetDateTime));
        report.setAffectedArea("纽约市曼哈顿区");
        
        // 分析各类风险
        RiskWarningReport.RiskAnalysis riskAnalysis = analyzeRisks(targetDateTime);
        report.setRiskAnalysis(riskAnalysis);
        
        // 确定整体风险等级
        String riskLevel = determineRiskLevel(riskAnalysis);
        report.setRiskLevel(riskLevel);
        report.setRiskType(determineRiskType(riskAnalysis));
        
        // 识别高风险区域
        List<RiskWarningReport.HighRiskZone> highRiskZones = identifyHighRiskZones(targetDateTime, riskAnalysis);
        report.setHighRiskZones(highRiskZones);
        
        // 生成建议和SOP引用
        report.setRecommendations(generateRecommendations(riskLevel, riskAnalysis));
        report.setSopReference(getSopReference(riskLevel));
        
        return report;
    }

    /**
     * 分析各类风险
     */
    private RiskWarningReport.RiskAnalysis analyzeRisks(LocalDateTime targetDateTime) {
        RiskWarningReport.RiskAnalysis analysis = new RiskWarningReport.RiskAnalysis();
        
        // 分析天气风险
        RiskWarningReport.WeatherRisk weatherRisk = analyzeWeatherRisk(targetDateTime);
        analysis.setWeatherRisk(weatherRisk);
        
        // 分析交通风险
        RiskWarningReport.TrafficRisk trafficRisk = analyzeTrafficRisk(targetDateTime);
        analysis.setTrafficRisk(trafficRisk);
        
        // 分析事件风险
        RiskWarningReport.EventRisk eventRisk = analyzeEventRisk(targetDateTime);
        analysis.setEventRisk(eventRisk);
        
        // 计算综合风险评分
        int overallScore = weatherRisk.getRiskScore() + trafficRisk.getRiskScore() + eventRisk.getRiskScore();
        analysis.setOverallRiskScore(overallScore);
        
        // 生成风险因子描述
        analysis.setRiskFactors(generateRiskFactors(weatherRisk, trafficRisk, eventRisk));
        
        return analysis;
    }

    /**
     * 分析天气风险
     */
    private RiskWarningReport.WeatherRisk analyzeWeatherRisk(LocalDateTime targetDateTime) {
        RiskWarningReport.WeatherRisk weatherRisk = new RiskWarningReport.WeatherRisk();
        
        // 查询目标时间的天气数据
        Optional<WeatherData> weatherOpt = weatherDataRepository.findByDate(targetDateTime);
        
        if (weatherOpt.isPresent()) {
            WeatherData weather = weatherOpt.get();
            
            // 检查是否有降雪
            boolean hasSnow = weather.getSnow() != null && weather.getSnow().doubleValue() > 0;
            weatherRisk.setHasSnow(hasSnow);
            
            // 检查结冰风险
            boolean hasIcingRisk = weather.hasIcingRisk();
            weatherRisk.setHasIcingRisk(hasIcingRisk);
            
            // 检查恶劣天气
            boolean isSevereWeather = weather.isSevereWeather();
            weatherRisk.setSevereWeather(isSevereWeather);
            
            // 设置天气描述
            weatherRisk.setWeatherDescription(weather.getWeatherDescription());
            
            // 计算天气风险评分
            int score = 0;
            if (hasSnow) {
                score += 30;
            }
            if (hasIcingRisk) {
                score += 25;
            }
            if (isSevereWeather) {
                score += 20;
            }
            
            weatherRisk.setRiskScore(score);
        } else {
            weatherRisk.setWeatherDescription("天气数据不可用");
            weatherRisk.setRiskScore(0);
        }
        
        return weatherRisk;
    }

    /**
     * 分析交通风险
     */
    private RiskWarningReport.TrafficRisk analyzeTrafficRisk(LocalDateTime targetDateTime) {
        RiskWarningReport.TrafficRisk trafficRisk = new RiskWarningReport.TrafficRisk();
        
        // 判断是否为高峰时段
        int hour = targetDateTime.getHour();
        boolean isRushHour = (hour >= 7 && hour <= 9) || (hour >= 17 && hour <= 19);
        trafficRisk.setRushHour(isRushHour);
        
        // 查询历史事故数据（同一时段）
        LocalDateTime startDate = targetDateTime.minusDays(30);
        LocalDateTime endDate = targetDateTime.plusDays(1);
        
        List<TrafficAccident> accidents = trafficAccidentRepository.findByDateRange(startDate, endDate);
        int accidentCount = accidents.size();
        trafficRisk.setAccidentCount(accidentCount);
        
        // 查询地铁高密度站点
        List<SubwayRidership> highDensityStations = subwayRidershipRepository
            .findHighDensityStations(startDate, endDate, 500);
        trafficRisk.setHighDensityStations(highDensityStations.size());
        
        // 设置交通模式描述
        if (isRushHour) {
            trafficRisk.setTrafficPattern("高峰时段 - 交通密度极高");
        } else {
            trafficRisk.setTrafficPattern("平峰时段 - 交通密度正常");
        }
        
        // 计算交通风险评分
        int score = 0;
        if (isRushHour) {
            score += 25;
        }
        if (accidentCount > 10) {
            score += 20;
        }
        if (highDensityStations.size() > 5) {
            score += 15;
        }
        
        trafficRisk.setRiskScore(score);
        
        return trafficRisk;
    }

    /**
     * 分析事件风险
     */
    private RiskWarningReport.EventRisk analyzeEventRisk(LocalDateTime targetDateTime) {
        RiskWarningReport.EventRisk eventRisk = new RiskWarningReport.EventRisk();
        
        // 查询活跃事件
        LocalDateTime startTime = targetDateTime.minusHours(2);
        LocalDateTime endTime = targetDateTime.plusHours(2);
        
        List<PermittedEvent> activeEvents = permittedEventRepository
            .findByBoroughAndDateRange("Manhattan", startTime, endTime);
        eventRisk.setActiveEvents(activeEvents.size());
        
        // 统计高影响事件
        long highImpactCount = activeEvents.stream()
            .filter(event -> "高影响".equals(event.getImpactLevel()))
            .count();
        eventRisk.setHighImpactEvents((int) highImpactCount);
        
        // 统计事件类型
        Map<String, Long> eventTypeCount = activeEvents.stream()
            .collect(Collectors.groupingBy(
                PermittedEvent::getEventType,
                Collectors.counting()
            ));
        
        String eventTypes = eventTypeCount.entrySet().stream()
            .map(entry -> entry.getKey() + "(" + entry.getValue() + ")")
            .collect(Collectors.joining(", "));
        eventRisk.setEventTypes(eventTypes.isEmpty() ? "无活跃事件" : eventTypes);
        
        // 计算事件风险评分
        int score = 0;
        if (activeEvents.size() > 3) {
            score += 15;
        }
        if (highImpactCount > 0) {
            score += 20;
        }
        
        eventRisk.setRiskScore(score);
        
        return eventRisk;
    }

    /**
     * 确定风险等级
     */
    private String determineRiskLevel(RiskWarningReport.RiskAnalysis analysis) {
        int totalScore = analysis.getOverallRiskScore();
        
        if (totalScore >= 70) {
            return "一级风险"; // 高风险
        } else if (totalScore >= 50) {
            return "二级风险"; // 中高风险
        } else if (totalScore >= 30) {
            return "三级风险"; // 中等风险
        } else {
            return "四级风险"; // 低风险
        }
    }

    /**
     * 确定风险类型
     */
    private String determineRiskType(RiskWarningReport.RiskAnalysis analysis) {
        List<String> riskTypes = new ArrayList<>();
        
        RiskWarningReport.WeatherRisk weather = analysis.getWeatherRisk();
        if (weather.isHasSnow()) {
            riskTypes.add("暴雪");
        }
        if (weather.isHasIcingRisk()) {
            riskTypes.add("道路结冰");
        }
        if (weather.isSevereWeather()) {
            riskTypes.add("恶劣天气");
        }
        
        if (analysis.getTrafficRisk().isRushHour()) {
            riskTypes.add("高峰拥堵");
        }
        
        if (analysis.getEventRisk().getHighImpactEvents() > 0) {
            riskTypes.add("重大事件");
        }
        
        return riskTypes.isEmpty() ? "综合风险" : String.join("+", riskTypes);
    }

    /**
     * 识别高风险区域
     */
    private List<RiskWarningReport.HighRiskZone> identifyHighRiskZones(
            LocalDateTime targetDateTime, RiskWarningReport.RiskAnalysis analysis) {
        
        List<RiskWarningReport.HighRiskZone> zones = new ArrayList<>();
        
        // 基于历史事故数据识别事故多发区域
        LocalDateTime startDate = targetDateTime.minusDays(30);
        LocalDateTime endDate = targetDateTime.plusDays(1);
        
        List<Object[]> accidentsByStreet = trafficAccidentRepository
            .countAccidentsByStreet(startDate, endDate);
        
        // 取前5个事故多发街道
        for (int i = 0; i < Math.min(5, accidentsByStreet.size()); i++) {
            Object[] result = accidentsByStreet.get(i);
            String streetName = (String) result[0];
            Long accidentCount = (Long) result[1];
            
            if (accidentCount > 3) { // 只考虑事故数量较多的街道
                RiskWarningReport.HighRiskZone zone = new RiskWarningReport.HighRiskZone();
                zone.setZoneName("事故多发区域");
                zone.setLocation(streetName);
                zone.setRiskLevel(accidentCount > 10 ? "极高风险" : "高风险");
                zone.setRiskFactors("历史事故频发，天气条件恶化");
                
                List<String> suggestions = Arrays.asList(
                    "增派交警巡逻",
                    "设置临时警示标志",
                    "加强路面除雪除冰",
                    "限制车辆通行速度"
                );
                zone.setDeploymentSuggestions(suggestions);
                
                zones.add(zone);
            }
        }
        
        // 基于地铁高密度站点识别人流密集区域
        List<SubwayRidership> highDensityStations = subwayRidershipRepository
            .findHighDensityStations(startDate, endDate, 800);
        
        for (SubwayRidership station : highDensityStations.subList(0, Math.min(3, highDensityStations.size()))) {
            RiskWarningReport.HighRiskZone zone = new RiskWarningReport.HighRiskZone();
            zone.setZoneName("人流密集区域");
            zone.setLocation(station.getStationComplex() + "地铁站周边");
            zone.setRiskLevel("中高风险");
            zone.setRiskFactors("人流密集，恶劣天气下疏散困难");
            zone.setLatitude(station.getLatitude() != null ? station.getLatitude().doubleValue() : null);
            zone.setLongitude(station.getLongitude() != null ? station.getLongitude().doubleValue() : null);
            
            List<String> suggestions = Arrays.asList(
                "增加地面引导人员",
                "开放临时避难场所",
                "加强地铁站周边除雪",
                "准备应急疏散预案"
            );
            zone.setDeploymentSuggestions(suggestions);
            
            zones.add(zone);
        }
        
        return zones;
    }

    /**
     * 生成建议措施
     */
    private List<String> generateRecommendations(String riskLevel, RiskWarningReport.RiskAnalysis analysis) {
        List<String> recommendations = new ArrayList<>();
        
        // 基于风险等级的通用建议
        switch (riskLevel) {
            case "一级风险":
                recommendations.add("立即启动应急预案，全面部署应急资源");
                recommendations.add("发布交通管制通告，限制非必要车辆出行");
                recommendations.add("开放所有应急避难场所");
                break;
            case "二级风险":
                recommendations.add("启动二级应急响应，重点区域部署警力");
                recommendations.add("发布交通安全提醒，建议市民谨慎出行");
                recommendations.add("加强重点路段巡逻和监控");
                break;
            case "三级风险":
                recommendations.add("加强交通监控，做好应急准备");
                recommendations.add("向市民发布出行提醒");
                break;
            default:
                recommendations.add("保持常规监控，关注天气变化");
        }
        
        // 基于具体风险因子的专项建议
        RiskWarningReport.WeatherRisk weather = analysis.getWeatherRisk();
        if (weather.isHasSnow()) {
            recommendations.add("启动除雪作业，优先保障主干道通行");
            recommendations.add("在坡道和桥梁设置防滑设施");
        }
        
        if (weather.isHasIcingRisk()) {
            recommendations.add("重点关注桥梁、高架路段结冰情况");
            recommendations.add("准备融雪剂和防滑材料");
        }
        
        if (analysis.getTrafficRisk().isRushHour()) {
            recommendations.add("在高峰时段增派交通疏导人员");
            recommendations.add("优化信号灯配时，提高通行效率");
        }
        
        if (analysis.getEventRisk().getHighImpactEvents() > 0) {
            recommendations.add("协调活动主办方，做好人流疏导");
            recommendations.add("制定活动期间应急疏散方案");
        }
        
        return recommendations;
    }

    /**
     * 获取SOP引用
     */
    private String getSopReference(String riskLevel) {
        switch (riskLevel) {
            case "一级风险":
                return "SOP-PW-L1: 一级风险应急处置标准作业程序";
            case "二级风险":
                return "SOP-PW-L2: 二级风险预警处置标准作业程序";
            case "三级风险":
                return "SOP-PW-L3: 三级风险监控标准作业程序";
            default:
                return "SOP-PW-L4: 常规监控标准作业程序";
        }
    }

    /**
     * 生成风险因子描述
     */
    private String generateRiskFactors(RiskWarningReport.WeatherRisk weather, 
                                     RiskWarningReport.TrafficRisk traffic, 
                                     RiskWarningReport.EventRisk event) {
        List<String> factors = new ArrayList<>();
        
        if (weather.isHasSnow()) {
            factors.add("降雪天气");
        }
        if (weather.isHasIcingRisk()) {
            factors.add("道路结冰风险");
        }
        if (weather.isSevereWeather()) {
            factors.add("恶劣天气条件");
        }
        
        if (traffic.isRushHour()) {
            factors.add("交通高峰时段");
        }
        if (traffic.getAccidentCount() > 10) {
            factors.add("历史事故频发");
        }
        if (traffic.getHighDensityStations() > 5) {
            factors.add("人流密集");
        }
        
        if (event.getActiveEvents() > 3) {
            factors.add("多个活动同时进行");
        }
        if (event.getHighImpactEvents() > 0) {
            factors.add("高影响事件");
        }
        
        return factors.isEmpty() ? "暂无明显风险因子" : String.join("、", factors);
    }

    /**
     * 格式化时间窗口
     */
    private String formatTimeWindow(LocalDateTime targetDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
        LocalDateTime endTime = targetDateTime.plusHours(2);
        return targetDateTime.format(formatter) + " - " + endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * 扫描风险并生成结构化通报对象
     * 实现场景一：事前·主动风险预警 (Proactive Risk Warning)
     *
     * @param targetDateTime 目标时间
     * @return 风险预警报告
     */
    public RiskWarningReport scanForRisk(LocalDateTime targetDateTime) {
        // 生成风险预警报告
        RiskWarningReport report = generateRiskWarning(targetDateTime);

        // 可以在这里添加额外的处理逻辑，例如：
        // 1. 将报告保存到数据库
        // 2. 发送通知给相关人员
        // 3. 触发其他服务

        return report;
    }

    /**
     * 检查是否存在需要预警的风险条件
     *
     * @param targetDateTime 目标时间
     * @return 是否存在风险
     */
    public boolean hasRiskConditions(LocalDateTime targetDateTime) {
        // 分析各类风险
        RiskWarningReport.RiskAnalysis riskAnalysis = analyzeRisks(targetDateTime);

        // 确定整体风险等级
        String riskLevel = determineRiskLevel(riskAnalysis);

        // 如果风险等级为二级及以上，则认为存在风险
        return "一级风险".equals(riskLevel) || "二级风险".equals(riskLevel);
    }
}
