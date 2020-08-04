package com.ds_algo.q_graph;

import java.util.*;

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
public abstract class Graph<V, E> {
    protected WeightManager<E> weightManager;
    public Graph() {}
    public Graph(WeightManager<E> weightManager) {
        this.weightManager = weightManager;
    }
    /*
     * 遍历接口
     */
    interface VertexVisitor<V>{
        boolean visit(V v);
    }

    /*
     *  权重管理者接口: 因为权重是泛型E，具体的比较、相加、zero的处理交给外界
     */
    public interface WeightManager<E> {
        int compare(E w1, E w2);
        E add(E w1, E w2);
        E zero();
    }

    public abstract int verticesSize();
    public abstract int edgesSize();

    public abstract void addVertex(V vertex);
    public abstract void removeVertex(V vertex);

    public abstract  void addEdge(V fromV, V toV);
    public abstract void addEdge(V fromV, V toV ,E weight);
    public abstract void removeEdge(V fromV, V toV);

    /*
     * 广度优先搜索
     */
    public abstract void bfs(V begin, VertexVisitor<V> visitor);
    /*
     * 深度优先搜索
     */
    public abstract void dfs(V begin, VertexVisitor<V> visitor);


    /*
     * 拓扑排序
     * 一个大工程通常分为多个子工程，子工程之间可能存在一定的先后顺序，即一些子工程必须在某些子工程完成后才能开始
     * 在现在化的管理中，通常使用有向图描述和分析一项工程的计划和实施过程。子工程通常称为活动（Activity）
     * AOV:
     * -- 以顶点表示活动、有向边表示活动之间的先后关系，这样的图称为AOV网。（Activity on Vertex network）
     * -- 标准的AOV网，必须是有向无环图
     *
     * 拓扑排序: topologySort
     * -- 前驱活动: 有向边的起点活动称为终点的前驱活动--只有当一个活动的所有前驱活动都完成后，这个活动才能进行
     * -- 拓扑排序: 将一个AOV网的所有活动排成一个序列，是个每个活动的前驱活动都排在该活动的前面
     *
     * 思路： kahn算法
     */
    public abstract List<V> topologySort();

    /*
     * 生成树Spanning Tree:连通图的极小连通子图，它含有图的所有的N个顶点，恰好有N-1条边
     * 最小生成树Minimum Spanning Tree: 即最小权重树、最小支撑树
     * --是所有生成树中总权重最小的那棵树
     * --适用有权的连通图（无向）
     *
     * 算法: 切分定理
     * 切分: 将图中的节点切分为两部分称为一个切分
     * C={S,T}, S={A,B,D}, T= {C,E}  S,T属于整个图的顶点集合C,将其分为S、T两个集合，就叫做切分
     * 横切边: 如果一个边的两个顶点分别属于切分的两个部分，这个边叫做横切边
     * 切分定理: 给定任意切分，横切边中权重最小的边必定属于最小生成树
     *
     * Prim、Kruskal算法
     *
     */
    public abstract Set<EdgeInfo<V,E>> mst();

    /*
     * 最短路径:
     *   两个顶点之间权重最小的路径（有向、无向均适用，但不能有负权环，可以有负权边）
     *
     * 单源最短路径算法:
     * Dijkstra: 不能有负权边
     * Bellman-ford：可以有负权边
     *
     * 多源最短路径算法:可以有负权边
     * Floyd
     *
     */

    public abstract Map<V,PathInfo<V,E>> shortestPath(V begin);

    // 多源最短路径算法
    public abstract Map<V, Map<V, PathInfo<V, E>>> shortestPath();

    /* ---------------- 暴露给外界的信息类 ------------------*/

    /*
     * PathInfo<V,E> 为获取最短路径信息设置的类
     */
    public static class PathInfo<V,E>{
        protected  E weight;
        protected  List<EdgeInfo<V,E>> edgeInfos = new LinkedList<>();
        public PathInfo(){};
        public PathInfo(E weight) {
            this.weight = weight;
        }

        public E getWeight() {
            return weight;
        }

        public void setWeight(E weight) {
            this.weight = weight;
        }

        public List<EdgeInfo<V, E>> getEdgeInfos() {
            return edgeInfos;
        }

        public void setEdgeInfos(List<EdgeInfo<V, E>> edgeInfos) {
            edgeInfos = edgeInfos;
        }

        @Override
        public String toString() {
            return "PathInfo{" +
                    "weight=" + weight +
                    ", edgeInfos=" + edgeInfos +
                    '}';
        }
    }

    /*
     * 不想让外界知道内部使用的类 Edge<V, E> Vertex<V,E>等信息，因为这些结构是自己内部具体实现,而且实现方案可能会改变
     * 这样的具体信息就没必要暴露，反而麻烦，比如，有一天换种方式实现，外部就惨了。
     * 所以只将外部需要的必要信息封装一下
     *
     */
    public static class EdgeInfo<V,E>{
        private V form;
        private V to;
        private E weight;

        public EdgeInfo(V form, V to, E weight) {
            this.form = form;
            this.to = to;
            this.weight = weight;
        }

        public V getForm() {
            return form;
        }

        public void setForm(V form) {
            this.form = form;
        }

        public V getTo() {
            return to;
        }

        public void setTo(V to) {
            this.to = to;
        }

        public E getWeight() {
            return weight;
        }

        public void setWeight(E weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EdgeInfo{" +
                    "form=" + form +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }
}
