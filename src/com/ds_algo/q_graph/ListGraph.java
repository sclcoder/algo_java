package com.ds_algo.q_graph;

import java.util.*;

public class ListGraph<V,E> implements Graph<V,E>{

    // 存放所有的顶点: 以V为key, Vertex为Value
    private final Map<V,Vertex<V,E>> vertices = new HashMap();
    // 存放所有的边: Edge
    private final Set<Edge<V,E>> edges = new HashSet<>();

    /*
     * 顶点类: 记录顶点的相关信息
     * 1. 顶点值
     * 2. 进入的边、出去的边: 边的数量不可控，且不需要顺序和重复，考虑使用Set保存
     * 外部调用使用的V, 内部保存的是Vertex
     */
    public class Vertex<V,E>{
        V value;
        Set<Edge<V,E>> inEdges = new HashSet<>();
        Set<Edge<V,E>> outEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            return Objects.equals(this.value, ((Vertex<V,E>)o).value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    public class Edge<V, E>{
        Vertex<V,E> formVertex;
        Vertex<V,E> toVertex;
        E weight;

        public Edge(Vertex<V,E> formVertex, Vertex<V,E> toVertex, E weight) {
            this.formVertex = formVertex;
            this.toVertex = toVertex;
            this.weight = weight;
        }

        public Edge(Vertex<V,E> formVertex, Vertex<V,E> toVertex) {
            this.formVertex = formVertex;
            this.toVertex = toVertex;
        }

        @Override
        public boolean equals(Object obj) {
            Edge<V,E> edge = (Edge<V,E>) obj;
            // weight不参与hash，只要from和to是相同的就认为是同一条边
            return Objects.equals(formVertex,edge.formVertex) && Objects.equals(toVertex,edge.toVertex);
        }

        @Override
        public int hashCode() {
            return formVertex.hashCode() * 31 + toVertex.hashCode();
        }

        @Override
        public String toString() {
            return "Edge[" +
                    "formVertex=" + formVertex +
                    ", toVertex=" + toVertex +
                    ", weight=" + (weight == null ? "null" : weight)+
                    ']';
        }
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public void addVertex(V v) {
        if (vertices.containsKey(v)) return;
        vertices.put(v,new Vertex<>(v));
    }

    @Override
    public void removeVertex(V v) {
        /*
         * 1.删除和vertex相关的edge
         * 2.删除vertex
         */
        Vertex<V,E> vertex = vertices.get(v);
        if (vertex == null) return;

        vertex.outEdges.forEach((Edge<V, E> edge) -> {
            edge.toVertex.inEdges.remove(edge);
            // 不需要在这里处理，况且还是在迭代中删除，不靠谱
            // vertex.outEdges.remove(edge);
            edges.remove(edge);
        });
        vertex.inEdges.forEach((Edge<V, E> edge) -> {
            edge.formVertex.outEdges.remove(edge);
            // vertex.inEdges.remove(edge);
            edges.remove(edge);
        });

        // 这里会删除vertex,其outEdges、inEdges都会被清空
        vertices.remove(v);
    }

    @Override
    public void addEdge(V fromV, V toV) {
        addEdge(fromV,toV,null);
    }

    @Override
    public void addEdge(V fromV, V toV, E weight) {

        Vertex<V,E> fromVertex = vertices.get(fromV);
        if (fromVertex == null){
            fromVertex = new Vertex<>(fromV);
            vertices.put(fromV,fromVertex);
        }

        Vertex<V,E> toVertex = vertices.get(toV);
        if (toVertex == null){
            toVertex = new Vertex<>(toV);
            vertices.put(toV,toVertex);
        }

        Edge<V,E> edge = new Edge<>(fromVertex,toVertex,weight);
        edge.weight = weight;

        /*
         * 因为使用的是集合,有天然的去重功能,我不明白为什么MJ还做了一次删除操作
         */
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void removeEdge(V fromV, V toV) {
        Vertex<V,E> fromVertex = vertices.get(fromV);
        Vertex<V,E> toVertex = vertices.get(toV);
        if (fromVertex == null || toVertex == null) return;

        Edge<V,E> edge = new Edge<>(fromVertex,toVertex);

        fromVertex.outEdges.remove(edge);
        toVertex.inEdges.remove(edge);
        edges.remove(edge);
    }


    public void print(){
        System.out.println("---------顶点信息----------");
        vertices.forEach((V v, Vertex<V,E> vertex) -> {
            System.out.println(v);
            System.out.println("in-----------");
            System.out.println(vertex.inEdges);
            System.out.println("out-----------");
            System.out.println(vertex.outEdges);
        });

        System.out.println("---------边信息----------");

        edges.forEach((Edge<V, E> edge) -> {
            System.out.println(edge);
        });


    };

}
