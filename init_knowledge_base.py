#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
知识库初始化脚本
用于初始化Milvus向量数据库和MySQL关系数据库
"""

import pymysql
import json
from pymilvus import (
    connections,
    FieldSchema,
    CollectionSchema,
    DataType,
    Collection,
    utility
)

# 数据库配置
MYSQL_CONFIG = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': 'Qwert_1245',
    'database': 'smart_transportation'
}

MILVUS_CONFIG = {
    'host': 'localhost',
    'port': 19530
}

# Milvus集合配置
COLLECTION_NAME = "knowledge_base"
VECTOR_DIM = 1536  # 修改为1536维向量，与Java代码中的嵌入模型匹配

def connect_mysql():
    """连接MySQL数据库"""
    try:
        connection = pymysql.connect(
            host=MYSQL_CONFIG['host'],
            port=MYSQL_CONFIG['port'],
            user=MYSQL_CONFIG['user'],
            password=MYSQL_CONFIG['password'],
            database=MYSQL_CONFIG['database'],
            charset='utf8mb4',
            cursorclass=pymysql.cursors.DictCursor
        )
        print("✅ 成功连接到MySQL数据库")
        return connection
    except Exception as e:
        print(f"❌ 连接MySQL数据库失败: {e}")
        return None

def connect_milvus():
    """连接Milvus向量数据库"""
    try:
        connections.connect(
            alias="default",
            host=MILVUS_CONFIG['host'],
            port=MILVUS_CONFIG['port']
        )
        print("✅ 成功连接到Milvus向量数据库")
        return True
    except Exception as e:
        print(f"❌ 连接Milvus数据库失败: {e}")
        return False

def create_milvus_collection():
    """创建Milvus集合"""
    try:
        # 检查集合是否已存在
        if utility.has_collection(COLLECTION_NAME):
            print(f"⚠️  集合 {COLLECTION_NAME} 已存在，正在删除...")
            existing_collection = Collection(COLLECTION_NAME)
            existing_collection.drop()
            print(f"✅ 已删除现有集合 {COLLECTION_NAME}")

        # 定义字段
        fields = [
            FieldSchema(name="id", dtype=DataType.INT64, is_primary=True, auto_id=True),
            FieldSchema(name="embedding", dtype=DataType.FLOAT_VECTOR, dim=VECTOR_DIM),  # 使用1536维向量
            FieldSchema(name="content", dtype=DataType.VARCHAR, max_length=65535),
            FieldSchema(name="category", dtype=DataType.VARCHAR, max_length=100),
            FieldSchema(name="title", dtype=DataType.VARCHAR, max_length=500)
        ]

        # 创建集合模式
        schema = CollectionSchema(fields, description="交通知识库")

        # 创建集合
        collection = Collection(
            name=COLLECTION_NAME,
            schema=schema,
            using='default',
            shards_num=2
        )

        # 创建索引，使用COSINE度量类型以匹配Java代码中的查询
        index_params = {
            "index_type": "IVF_FLAT",
            "metric_type": "COSINE",  # 修改为COSINE度量类型
            "params": {"nlist": 128}
        }

        collection.create_index(
            field_name="embedding",
            index_params=index_params
        )

        # 加载集合
        collection.load()

        print(f"✅ 成功创建Milvus集合 {COLLECTION_NAME}，向量维度: {VECTOR_DIM}，度量类型: COSINE")
        return collection

    except Exception as e:
        print(f"❌ 创建Milvus集合失败: {e}")
        return None

def load_sample_data(collection):
    """加载示例数据到Milvus集合"""
    try:
        # 示例数据
        sample_data = [
            {
                "content": "2024年2月15日，曼哈顿第五大道与42街交叉口发生一起严重交通事故，一辆出租车与公交车相撞，造成3人受伤。",
                "category": "交通事故",
                "title": "2024年2月曼哈顿严重交通事故"
            },
            {
                "content": "2024年2月18日，由于暴雪天气影响，纽约市交通部门发布黄色预警，建议市民减少不必要的出行。",
                "category": "天气影响",
                "title": "2024年2月暴雪天气交通预警"
            },
            {
                "content": "2024年2月20日，时代广场周边因大型活动实施临时交通管制，部分公交线路调整。",
                "category": "许可事件",
                "title": "2024年2月时代广场活动交通管制"
            }
        ]

        # 插入数据到Milvus
        entities = [
            [data["content"] for data in sample_data],
            [data["category"] for data in sample_data],
            [data["title"] for data in sample_data]
        ]

        # 注意：这里需要实际的向量数据，暂时用占位符
        # 在实际应用中，应该使用真实的嵌入模型生成向量
        import random
        embeddings = [[random.random() for _ in range(VECTOR_DIM)] for _ in range(len(sample_data))]
        entities.insert(0, embeddings)

        # 插入数据
        collection.insert(entities)
        collection.flush()

        print(f"✅ 成功加载 {len(sample_data)} 条示例数据到Milvus集合")

    except Exception as e:
        print(f"❌ 加载示例数据失败: {e}")

def main():
    """主函数"""
    print("🚀 开始初始化知识库...")

    # 连接数据库
    mysql_conn = connect_mysql()
    if not mysql_conn:
        return

    if not connect_milvus():
        return

    # 创建Milvus集合
    collection = create_milvus_collection()
    if not collection:
        return

    # 加载示例数据
    load_sample_data(collection)

    print("🎉 知识库初始化完成!")

if __name__ == "__main__":
    main()