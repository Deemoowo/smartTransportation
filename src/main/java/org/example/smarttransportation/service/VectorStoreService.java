package org.example.smarttransportation.service;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.*;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.grpc.DataType;
import io.milvus.grpc.SearchResults;
import io.milvus.grpc.MutationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 向量存储服务
 * 负责管理Milvus向量数据库的操作
 * 
 * @author pojin
 * @date 2025/11/22
 */
@Service
public class VectorStoreService {
    
    @Autowired(required = false)
    private MilvusServiceClient milvusClient;
    
    @Autowired
    private EmbeddingService embeddingService;
    
    // 修改集合名称以匹配Python脚本中创建的名称
    private static final String COLLECTION_NAME = "knowledge_base";
    private static final String ID_FIELD = "id";
    private static final String VECTOR_FIELD = "embedding";
    private static final String CONTENT_FIELD = "content";
    // 修改字段名以匹配Python脚本中创建的字段
    private static final String METADATA_FIELD = "category";
    private static final String SOURCE_FIELD = "title";

    /**
     * 初始化向量集合
     */
    public boolean initializeCollection() {
        if (milvusClient == null) {
            throw new IllegalStateException("Milvus客户端未配置，请检查Milvus连接配置");
        }

        try {
            // 检查集合是否存在
            R<Boolean> hasCollection = milvusClient.hasCollection(
                HasCollectionParam.newBuilder()
                    .withCollectionName(COLLECTION_NAME)
                    .build()
            );

            if (hasCollection.getData()) {
                return true; // 集合已存在
            }

            // 创建字段定义
            List<FieldType> fields = Arrays.asList(
                FieldType.newBuilder()
                    .withName(ID_FIELD)
                    .withDataType(DataType.Int64)
                    .withPrimaryKey(true)
                    .withAutoID(true)
                    .build(),
                FieldType.newBuilder()
                    .withName(VECTOR_FIELD)
                    .withDataType(DataType.FloatVector)
                    .withDimension(embeddingService.getEmbeddingDimension())
                    .build(),
                FieldType.newBuilder()
                    .withName(CONTENT_FIELD)
                    .withDataType(DataType.VarChar)
                    .withMaxLength(2000)
                    .build(),
                FieldType.newBuilder()
                    .withName(METADATA_FIELD)
                    .withDataType(DataType.VarChar)
                    .withMaxLength(1000)
                    .build(),
                FieldType.newBuilder()
                    .withName(SOURCE_FIELD)
                    .withDataType(DataType.VarChar)
                    .withMaxLength(100)
                    .build()
            );

            // 创建集合
            CreateCollectionParam createParam = CreateCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withDescription("智慧交通知识库向量集合")
                .withShardsNum(2)
                .withFieldTypes(fields)
                .build();

            R<RpcStatus> createResult = milvusClient.createCollection(createParam);

            if (createResult.getStatus() != 0) {
                throw new RuntimeException("创建集合失败: " + createResult.getMessage());
            }

            // 创建向量索引
            createVectorIndex();

            // 加载集合
            loadCollection();

            return true;

        } catch (Exception e) {
            throw new RuntimeException("初始化向量集合失败: " + e.getMessage(), e);
        }
    }

    /**
     * 创建向量索引
     */
    private void createVectorIndex() {
        if (milvusClient == null) {
            throw new IllegalStateException("Milvus客户端未配置");
        }

        CreateIndexParam indexParam = CreateIndexParam.newBuilder()
            .withCollectionName(COLLECTION_NAME)
            .withFieldName(VECTOR_FIELD)
            .withIndexType(io.milvus.param.IndexType.IVF_FLAT)
            .withMetricType(io.milvus.param.MetricType.COSINE)
            .withExtraParam("{\"nlist\":1024}")
            .build();

        R<RpcStatus> indexResult = milvusClient.createIndex(indexParam);

        if (indexResult.getStatus() != 0) {
            throw new RuntimeException("创建索引失败: " + indexResult.getMessage());
        }
    }

    /**
     * 加载集合到内存
     */
    private void loadCollection() {
        if (milvusClient == null) {
            throw new IllegalStateException("Milvus客户端未配置");
        }

        R<RpcStatus> loadResult = milvusClient.loadCollection(
            LoadCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build()
        );

        if (loadResult.getStatus() != 0) {
            throw new RuntimeException("加载集合失败: " + loadResult.getMessage());
        }
    }

    /**
     * 添加文档到向量库
     */
    public boolean addDocument(String content, String metadata, String source) {
        if (!StringUtils.hasText(content)) {
            throw new IllegalArgumentException("文档内容不能为空");
        }

        if (milvusClient == null) {
            throw new IllegalStateException("Milvus客户端未配置，请检查Milvus连接配置");
        }

        try {
            // 生成向量
            List<Float> embedding = embeddingService.embedText(content);

            // 准备数据
            List<List<Float>> vectors = Arrays.asList(embedding);
            List<String> contents = Arrays.asList(content);
            List<String> metadataList = Arrays.asList(metadata != null ? metadata : "");
            List<String> sources = Arrays.asList(source != null ? source : "unknown");

            List<InsertParam.Field> fields = Arrays.asList(
                new InsertParam.Field(VECTOR_FIELD, vectors),
                new InsertParam.Field(CONTENT_FIELD, contents),
                new InsertParam.Field(METADATA_FIELD, metadataList),
                new InsertParam.Field(SOURCE_FIELD, sources)
            );

            InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFields(fields)
                .build();

            R<MutationResult> insertResult = milvusClient.insert(insertParam);

            if (insertResult.getStatus() != 0) {
                throw new RuntimeException("插入文档失败: " + insertResult.getMessage());
            }

            return true;

        } catch (Exception e) {
            throw new RuntimeException("添加文档到向量库失败: " + e.getMessage(), e);
        }
    }

    /**
     * 批量添加文档
     */
    public boolean addDocuments(List<DocumentInfo> documents) {
        if (documents == null || documents.isEmpty()) {
            return true;
        }

        if (milvusClient == null) {
            throw new IllegalStateException("Milvus客户端未配置，请检查Milvus连接配置");
        }

        try {
            List<String> contents = new ArrayList<>();
            List<String> metadataList = new ArrayList<>();
            List<String> sources = new ArrayList<>();

            for (DocumentInfo doc : documents) {
                contents.add(doc.getContent());
                metadataList.add(doc.getMetadata() != null ? doc.getMetadata() : "");
                sources.add(doc.getSource() != null ? doc.getSource() : "unknown");
            }

            // 批量生成向量
            List<List<Float>> vectors = embeddingService.embedTexts(contents);

            List<InsertParam.Field> fields = Arrays.asList(
                new InsertParam.Field(VECTOR_FIELD, vectors),
                new InsertParam.Field(CONTENT_FIELD, contents),
                new InsertParam.Field(METADATA_FIELD, metadataList),
                new InsertParam.Field(SOURCE_FIELD, sources)
            );

            InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFields(fields)
                .build();

            R<MutationResult> insertResult = milvusClient.insert(insertParam);

            return insertResult.getStatus() == 0;

        } catch (Exception e) {
            throw new RuntimeException("批量添加文档失败: " + e.getMessage(), e);
        }
    }

    /**
     * 语义搜索
     */
    public List<SearchResult> semanticSearch(String query, int topK) {
        if (!StringUtils.hasText(query)) {
            return new ArrayList<>();
        }

        if (milvusClient == null) {
            throw new IllegalStateException("Milvus客户端未配置，请检查Milvus连接配置");
        }

        try {
            // 生成查询向量
            List<Float> queryVector = embeddingService.embedText(query);

            SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withMetricType(io.milvus.param.MetricType.COSINE)
                .withOutFields(Arrays.asList(CONTENT_FIELD, METADATA_FIELD, SOURCE_FIELD))
                .withTopK(topK)
                .withVectors(Arrays.asList(queryVector))
                .withVectorFieldName(VECTOR_FIELD)
                .withParams("{\"nprobe\":10}")
                .build();

            R<SearchResults> searchResult = milvusClient.search(searchParam);

            if (searchResult.getStatus() != 0) {
                throw new RuntimeException("搜索失败: " + searchResult.getMessage());
            }

            return parseSearchResults(searchResult.getData());

        } catch (Exception e) {
            throw new RuntimeException("语义搜索失败: " + e.getMessage(), e);
        }
    }

    /**
     * 解析搜索结果
     */
    private List<SearchResult> parseSearchResults(SearchResults searchResults) {
        List<SearchResult> results = new ArrayList<>();

        if (searchResults.getResults().getFieldsDataCount() == 0) {
            return results;
        }

        // 获取字段数据
        var fieldsData = searchResults.getResults().getFieldsDataList();
        var ids = searchResults.getResults().getIds().getIntId().getDataList();
        var scores = searchResults.getResults().getScoresList();

        // 解析每个字段的数据
        Map<String, List<String>> fieldValues = new HashMap<>();
        for (var fieldData : fieldsData) {
            String fieldName = fieldData.getFieldName();
            List<String> values = new ArrayList<>();

            if (fieldData.getType() == DataType.VarChar) {
                var varcharData = fieldData.getScalars().getStringData();
                values.addAll(varcharData.getDataList());
            }

            fieldValues.put(fieldName, values);
        }

        // 构建搜索结果对象
        int resultCount = ids.size();
        for (int i = 0; i < resultCount; i++) {
            SearchResult result = new SearchResult();
            result.setScore(scores.size() > i ? (float) scores.get(i) : 0.0f);

            // 设置内容
            List<String> contents = fieldValues.get(CONTENT_FIELD);
            if (contents != null && contents.size() > i) {
                result.setContent(contents.get(i));
            }

            // 设置元数据
            List<String> metadataList = fieldValues.get(METADATA_FIELD);
            if (metadataList != null && metadataList.size() > i) {
                result.setMetadata(metadataList.get(i));
            }

            // 设置数据源
            List<String> sources = fieldValues.get(SOURCE_FIELD);
            if (sources != null && sources.size() > i) {
                result.setSource(sources.get(i));
            }

            results.add(result);
        }

        return results;
    }



    /**
     * 文档信息类
     */
    public static class DocumentInfo {
        private String content;
        private String metadata;
        private String source;

        public DocumentInfo(String content, String metadata, String source) {
            this.content = content;
            this.metadata = metadata;
            this.source = source;
        }

        // Getters and Setters
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public String getMetadata() { return metadata; }
        public void setMetadata(String metadata) { this.metadata = metadata; }
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
    }

    /**
     * 搜索结果类
     */
    public static class SearchResult {
        private float score;
        private String content;
        private String metadata;
        private String source;

        // Getters and Setters
        public float getScore() { return score; }
        public void setScore(float score) { this.score = score; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public String getMetadata() { return metadata; }
        public void setMetadata(String metadata) { this.metadata = metadata; }
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
    }
}