package com.ds_algo.q_graph;

import com.ds_algo.l_heap.MinHeap;
import com.ds_algo.p_unionFind.UnionFindGeneric;

import java.util.*;

public class ListGraph<V,E> extends Graph<V, E> {
    public ListGraph() {}

    public ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
    }

    private final Map<V,Vertex<V,E>> vertices = new HashMap();  // 存放所有的顶点: 以V为key, Vertex为Value
    private final Set<Edge<V,E>> edges = new HashSet<>();  // 存放所有的边: Edge

    // 权重比较器
    private Comparator<Edge<V,E>> edgeComparator = new Comparator<Edge<V, E>>() {
        @Override
        public int compare(Edge<V, E> e1, Edge<V, E> e2) {
            return weightManager.compare(e1.weight,e2.weight);
        }
    };


    /* ---------------- 进阶功能函数之最短路径 ----------------*/



    /* ---------------- 进阶功能函数之最小生成树 ----------------*/

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        if (Math.random() < 0.5){
            return prim();
        } else {
            return kruskal();
        }
    }

    /* 最小生成树之prim算法
     * 假设G={V,E}是有权的连通图(无向-无向能覆盖有向的情况)，A是G中最小生成树的边集
     * 算法从S={u} {u属于V集合}, A={}开始，重复执行一下操作，直到S = V为止
     * --找到切分C = {S, V-S}的最小横切边(u,v)并入集合A，同时将v并入集合S
     *
     */
    private Set<EdgeInfo<V,E>> prim(){
        Iterator<Vertex<V,E>> iterator = vertices.values().iterator();
        if (iterator.next() == null) return null;
        Vertex<V,E> vertex = iterator.next();
        Set<Vertex<V,E>> addedVertices = new HashSet<>();
        addedVertices.add(vertex);
        Set<EdgeInfo<V,E>> edgeInfos = new HashSet<>();
        MinHeap<Edge<V,E>> heap = new MinHeap<>(vertex.outEdges, edgeComparator);
        while (!heap.isEmpty() && addedVertices.size() < vertices.size()){
            Edge<V,E> edge = heap.remove();
            if (addedVertices.contains(edge.toVertex)) continue;
            addedVertices.add(edge.toVertex);
            edgeInfos.add(edge.info());
            heap.addAll(edge.toVertex.outEdges);
        }
        return edgeInfos;
    }

    /* 最小生成树之kruskal算法
     * 按照边的权重从小到大到添加到生成树中，直到生成树中有V-1条边
     * 1.若加入的边的会和生成树边形成环，则不加入该边
     *   可利用并查集这种结构解决新加入的边会不会和生成树形成环的情况:
     *   -- 将所有的顶点独立成单独的集合
     *   -- 若新加入的边fromVertex和toVertex属于同一个集合,说明添加这条边会使生成树形成环
     * 2.从添加第3条边开始，可能使生成树形成环
     *
     */
    private Set<EdgeInfo<V,E>> kruskal(){
        if (vertices.size() <= 0) return null;
        Set<EdgeInfo<V,E>> edgeInfos = new HashSet<>();
        MinHeap<Edge<V,E>> heap = new MinHeap<>(edges, edgeComparator);
        UnionFindGeneric uf = new UnionFindGeneric();
        for (Vertex<V,E> vertex : vertices.values()) {
            uf.makeSet(vertex); // 将每个顶点都初始化为单独的集合
        }
        while (!heap.isEmpty() && edgeInfos.size() < vertices.size() - 1){
            Edge<V,E> edge = heap.remove();
            if (uf.isSame(edge.fromVertex,edge.toVertex)) continue;
            edgeInfos.add(edge.info());
            uf.union(edge.fromVertex,edge.toVertex);
        }
        return edgeInfos;
    }

    /* ---------------- 进阶功能函数之AOV网络的拓扑排序 ----------------*/
    @Override
    public List<V> topologySort() {
        /* 拓扑排序之kahn算法
         * 假设L是存放拓扑排序的结果的列表
         * 1.将所有入度为0的Vertex放入L中，然后将该Vertex从图中删除
         * 2.重复1的操作，直到找不到入度为0的Vertex
         * 注意： 如果L中的Vertex数量和AOV网中的Vertex数量相等，说明拓扑排序完成
         *       如果L中的Vertex数量少于AOV网中的Vertex数量，说明AOV中存在环，无法完成拓扑排序
         * 在实现拓扑排序时，为了不破坏AOV的结构，需要变通一下再处理
         */

        List<V> list = new ArrayList<>();
        Map<Vertex<V,E> , Integer> inDegree = new HashMap<>(); // Vertex和其入度映射
        Queue<Vertex<V,E>> queue = new LinkedList<>(); // 存放入度为0的Vertex
        vertices.forEach((V v ,Vertex<V,E> vertex) -> {
            int count = vertex.inEdges.size();
            if (count == 0){
                queue.add(vertex);
            } else {
                inDegree.put(vertex,count);
            }
        });

        while (!queue.isEmpty()){
            Vertex<V,E> vertex = queue.poll();
            list.add(vertex.value);
            vertex.outEdges.forEach((Edge<V,E> edge)->{
                int inCount = inDegree.get(edge.toVertex) - 1;
                if (inCount == 0){
                    queue.add(edge.toVertex);
                } else {
                    inDegree.put(edge.toVertex,inCount);
                }
            });
        }
        return list;
    }

    /* ---------------- 进阶功能函数之遍历 ---------------- */

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
        double random = Math.random();
        if (random < 0.25){
            dfsRecurse(begin,visitor,visitedVertex);
        } else if (random < 0.5){
            dfsIterate(begin,visitor,visitedVertex);
        } else if (random < 0.75){
            dfsIterate2(begin,visitor,visitedVertex);
        } else {
            dfsIterate3(begin,visitor,visitedVertex);
        }
    }
    // 深度优先搜索 : 递归
    private void dfsRecurse(V v, VertexVisitor<V> visitor, Set<V> visitedVertex){
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
    private void dfsIterate(V v, VertexVisitor<V> visitor,Set<V> visitedVertex){
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
    private void dfsIterate2(V v, VertexVisitor<V> visitor,Set<V> visitedVertex){
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
    private void dfsIterate3(V v, VertexVisitor<V> visitor,Set<V> visitedVertex){
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
                    stack.push(edge.fromVertex);
                    stack.push(edge.toVertex);
                    visitedVertex.add(edge.toVertex.value);
                    if (visitor.visit(edge.toVertex.value)) return;
                    break; // 跳出一次for循环,进入下一次循环
                }
            }
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
            edge.fromVertex.outEdges.remove(edge);
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
    private class Vertex<V,E>{
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

    private class Edge<V, E> {
        Vertex<V,E> fromVertex;
        Vertex<V,E> toVertex;
        E weight;

        public EdgeInfo<V,E> info(){
            return new EdgeInfo<>(fromVertex.value,toVertex.value,weight);
        }

        public Edge(Vertex<V,E> fromVertex, Vertex<V,E> toVertex, E weight) {
            this.fromVertex = fromVertex;
            this.toVertex = toVertex;
            this.weight = weight;
        }

        public Edge(Vertex<V,E> fromVertex, Vertex<V,E> toVertex) {
            this.fromVertex = fromVertex;
            this.toVertex = toVertex;
        }

        @Override
        public boolean equals(Object obj) {
            Edge<V,E> edge = (Edge<V,E>) obj;
            // weight不参与hash，只要from和to是相同的就认为是同一条边
            return Objects.equals(fromVertex,edge.fromVertex) && Objects.equals(toVertex,edge.toVertex);
        }

        @Override
        public int hashCode() {
            return fromVertex.hashCode() * 31 + toVertex.hashCode();
        }

        @Override
        public String toString() {
            return "Edge[" +
                    "fromVertex=" + fromVertex +
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
