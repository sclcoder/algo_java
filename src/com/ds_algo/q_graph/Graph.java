package com.ds_algo.q_graph;

/**
 * Vertex: 顶点
 * Edge: 边
 * Graph: 图, 顶点集和边集构成图
 *
 * 图的接口: 实现图的功能有两种实现方案
 * 1. 邻接矩阵
 * 2. 邻接表
 *
 * V : 泛型顶点
 * E : 泛型权重
 *
 */
public interface Graph<V, E> {

    int verticesSize();
    int edgesSize();

    void addVertex(V vertex);
    void removeVertex(V vertex);

    void addEdge(V fromV, V toV);
    void addEdge(V fromV, V toV ,E weight);
    void removeEdge(V fromV, V toV);


}
