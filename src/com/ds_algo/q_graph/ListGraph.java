package com.ds_algo.q_graph;

import java.util.*;

public class ListGraph<V,E> implements Graph<V,E>{

    private final Map<V,Vertex<V,E>> vertices = new HashMap();  // 存放所有的顶点: 以V为key, Vertex为Value
    private final Set<Edge<V,E>> edges = new HashSet<>();  // 存放所有的边: Edge


    /* ---------------- 进阶功能函数 ----------------*/

    @Override
    public void bfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        // 广度优先遍历（类比二叉树的层次遍历）
        Vertex<V,E> vertex = vertices.get(begin);
        if (vertex == null) return;
        Set<V> visitedVertex = new HashSet<>(); // 记录被访问过的vertex.value
        Queue<Vertex<V,E>> queue = new LinkedList<>();

        queue.add(vertex);
        visitedVertex.add(begin);

        while (!queue.isEmpty()){
            Vertex<V,E> frontVertex = queue.poll();
            if (visitor.visit(frontVertex.value)) return;
            frontVertex.outEdges.forEach((Edge<V,E> edge) -> {
                if (!visitedVertex.contains(edge.toVertex.value)) {
                    queue.add(edge.toVertex);
                    visitedVertex.add(edge.toVertex.value);
                }
            });
        }
    }

    @Override
    public void dfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Set<V> visitedVertex = new HashSet<>(); // 记录被访问过的vertex.value
//        dfsRecurse(begin,visitor,visitedVertex);
//        dfsIterate(begin,visitor,visitedVertex);
//        dfsIterate2(begin,visitor,visitedVertex);
          dfsIterate3(begin,visitor,visitedVertex);

    }
    // 深度优先搜索 : 递归
    public void dfsRecurse(V v, VertexVisitor<V> visitor, Set<V> visitedVertex){
        Vertex<V,E> vertex = vertices.get(v);
        if (vertex == null) return;
        visitedVertex.add(vertex.value);
        if (visitor.visit(v)) return;

        vertex.outEdges.forEach((Edge<V,E> edge) -> {
            if (!visitedVertex.contains(edge.toVertex.value)){
                dfsRecurse(edge.toVertex.value, visitor, visitedVertex);
            }
        });
    }

    // TODO: 深入思考吧！
    // 深度优先搜索 : 迭代之一统江湖法
    public void dfsIterate(V v, VertexVisitor<V> visitor,Set<V> visitedVertex){
        Vertex<V,E> vertex = vertices.get(v);
        if (vertex == null) return;

        Stack<Vertex<V,E>> stack = new Stack<>();
        stack.push(vertex);

        while (!stack.isEmpty()){
            Vertex<V,E> top = stack.pop();
            if (top != null){
                top.outEdges.forEach((Edge<V,E> edge) -> {
                    if (!visitedVertex.contains(edge.toVertex.value)){
                        stack.push(edge.toVertex);
                    }
                });

                stack.push(top);
                stack.push(null);
            } else {
                top = stack.pop();
                if (!visitedVertex.contains(top.value)){
                    if (visitor.visit(top.value)) return;
                }
                visitedVertex.add(top.value);
            }
        }
    }

    // 一统江湖法的具体化
    public void dfsIterate2(V v, VertexVisitor<V> visitor,Set<V> visitedVertex){
        Vertex<V,E> vertex = vertices.get(v);
        if (vertex == null) return;

        Stack<Vertex<V,E>> stack = new Stack<>();
        stack.push(vertex);
        while (!stack.isEmpty()){
            Vertex<V,E> top = stack.pop();
            if (!visitedVertex.contains(top.value)){
                if (visitor.visit(top.value)) return;
            }
            visitedVertex.add(top.value);

            top.outEdges.forEach((Edge<V,E> edge) -> {
                if (!visitedVertex.contains(edge.toVertex.value)){
                    stack.push(edge.toVertex);
                }
            });
        }
    }

    // 非一统江湖法之MJ: 我没看懂
    public void dfsIterate3(V v, VertexVisitor<V> visitor,Set<V> visitedVertex){
        Vertex<V,E> vertex = vertices.get(v);
        if (vertex == null) return;
        Stack<Vertex<V,E>> stack = new Stack<>();

        // 先访问起点
        stack.push(vertex);
        visitedVertex.add(vertex.value);
        if (visitor.visit(vertex.value)) return;

        while (!stack.isEmpty()){
            Vertex<V,E> top = stack.pop();
            for (Edge<V, E> edge : top.outEdges) {

                if (!visitedVertex.contains(edge.toVertex.value)){
                    stack.push(edge.formVertex);
                    stack.push(edge.toVertex);
                    visitedVertex.add(edge.toVertex.value);
                    if (visitor.visit(edge.toVertex.value)) return;

                    break; // 跳出一次for循环,进入下一次循环
                }
            };
        }
    }


    /* ---------------- 基本功能函数 ----------------*/
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

        // 因为使用的是集合,有天然的去重功能,我不明白为什么MJ还做了一次删除操作
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



    /* ---------------- 内部类 ------------------*/

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

    /* ---------------- 调试函数 ------------------*/
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
