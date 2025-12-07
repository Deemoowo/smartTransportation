package org.example.smarttransportation.config;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.example.smarttransportation.dto.WeatherAnswer;
import org.example.smarttransportation.service.MetadataCacheService;
import org.example.smarttransportation.service.TavilySearchService;
import org.example.smarttransportation.service.TrafficDataAnalysisService;
import org.example.smarttransportation.service.WeatherApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class ToolsConfig {

    public record WebSearchRequest(String query) {}

    @Bean
    @Description("Search the web for information using Tavily API. Use this tool when you need up-to-date information or facts not in your knowledge base.")
    public Function<WebSearchRequest, TavilySearchService.TavilyResponse> webSearch(TavilySearchService tavilySearchService) {
        return request -> tavilySearchService.search(request.query());
    }

    @Bean
    @Description("查询纽约市曼哈顿区的交通状况。当用户询问'交通怎么样'、'拥堵情况'、'事故'、'客流'、'出行建议'或具体日期的交通分析时，必须调用此工具。")
    public Function<TrafficQueryRequest, String> trafficQuery(TrafficDataAnalysisService trafficDataAnalysisService, MetadataCacheService metadataCacheService) {
        return request -> {
            if (request.sessionId() != null) {
                metadataCacheService.addThought(request.sessionId(), "🤖 决定调用工具: trafficQuery");
                metadataCacheService.addThought(request.sessionId(), "⚙️ 参数: " + request.query());
            }
            return trafficDataAnalysisService.analyzeUserQuery(request.query(), request.sessionId());
        };
    }

    @Bean
    @Description("查询纽约曼哈顿区的天气情况。当用户询问天气、气温、降雨或天气对交通的影响时调用。")
    public Function<WeatherQueryRequest, WeatherAnswer> weatherQuery(WeatherApiService weatherApiService, MetadataCacheService metadataCacheService) {
        return request -> {
            if (request.sessionId() != null) {
                metadataCacheService.addThought(request.sessionId(), "🤖 决定调用工具: weatherQuery");
            }
            return weatherApiService.fetchManhattanFeb2024Weather(request.sessionId());
        };
    }

    @JsonClassDescription("交通数据查询请求")
    public record TrafficQueryRequest(
            @JsonProperty(required = true)
            @JsonPropertyDescription("用户的自然语言查询，例如'2月有多少事故'或'地铁客流趋势'")
            String query,
            
            @JsonProperty(required = true)
            @JsonPropertyDescription("当前会话ID (Session ID)，必须从上下文或系统提示中获取并原样传递")
            String sessionId
    ) {}

    @JsonClassDescription("天气查询请求")
    public record WeatherQueryRequest(
            @JsonProperty(required = false)
            @JsonPropertyDescription("查询日期，格式YYYY-MM-DD，默认为2024-02-01")
            String date,
            
            @JsonProperty(required = false)
            @JsonPropertyDescription("查询地点，默认为Manhattan,NY")
            String location,

            @JsonProperty(required = true)
            @JsonPropertyDescription("当前会话ID (Session ID)，必须从上下文或系统提示中获取并原样传递")
            String sessionId
    ) {}
}
