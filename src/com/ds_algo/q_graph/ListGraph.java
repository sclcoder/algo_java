package com.ds_algo.q_graph;

import com.ds_algo.l_heap.MinHeap;
import com.ds_algo.p_unionFind.UnionFindGeneric;

import java.util.*;
import java.util.Map.Entry;

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

    /**
     * Floyd算法: 属于多源最短路径算法，可以求出任意两个顶点的最短路径，支持负权边
     * 时间复杂度O(V^3),效率比执行V的Dijkstra算法好
     * 算法原理:
     *  从任意顶点i到顶点j的最短路径无外乎两种可能
     *      1.直接从i到j
     *      2.从i经过若干个顶点到j
     *  假设dist(i,j)为顶点i到顶点j的最短路径的距离
     *  对于每个顶点k，检查dist(i,k) + dist(k,j) < dist(i,j)是否成立
     *      1.如果成立，证明从i经过k到达j的路径比从i直接到j短，设置dist(i,j) = dist(i,k) + dist(k,j)
     *      2.当遍历完所有的节点k,dist(i,j)记录就是i到j的最短路径的距离
     *    Map<V, Map<V, PathInfo<V, E>>>
     *        V:起点
     *        Map<V : 终点
     */
    public Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
        Map<V, Map<V, PathInfo<V, E>>> paths = new HashMap<>();
        // 初始化
        for (Edge<V, E> edge : edges) {
            Map<V, PathInfo<V, E>> map = paths.get(edge.fromVertex.value);
            if (map == null) {
                map = new HashMap<>();
                paths.put(edge.fromVertex.value, map);
            }

            PathInfo<V, E> pathInfo = new PathInfo<>(edge.weight);
            pathInfo.edgeInfos.add(edge.info());
            map.put(edge.toVertex.value, pathInfo);
        }

        vertices.forEach((V v2, Vertex<V, E> vertex2) -> {
            vertices.forEach((V v1, Vertex<V, E> vertex1) -> {
                vertices.forEach((V v3, Vertex<V, E> vertex3) -> {
                    if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;

                    // v1 -> v2 : v1起点 v2终点
                    PathInfo<V, E> path12 = getPathInfo(v1, v2, paths);
                    if (path12 == null) return;

                    // v2 -> v3
                    PathInfo<V, E> path23 = getPathInfo(v2, v3, paths);
                    if (path23 == null) return;

                    // v1 -> v3
                    PathInfo<V, E> path13 = getPathInfo(v1, v3, paths);

                    E newWeight = weightManager.add(path12.weight, path23.weight);
                    if (path13 != null && weightManager.compare(newWeight, path13.weight) >= 0) return;

                    if (path13 == null) {
                        path13 = new PathInfo<V, E>();
                        paths.get(v1).put(v3, path13);
                    } else {
                        path13.edgeInfos.clear();
                    }

                    path13.weight = newWeight;
                    path13.edgeInfos.addAll(path12.edgeInfos);
                    path13.edgeInfos.addAll(path23.edgeInfos);
                });
            });
        });

        return paths;
    }


    private PathInfo<V, E> getPathInfo(V from, V to, Map<V, Map<V, PathInfo<V, E>>> paths) {
        Map<V, PathInfo<V, E>> map = paths.get(from);
        return map == null ? null : map.get(to);
    }

    /**
     * 获取源点到其他顶点的最短路径权重信息
     * @param begin 源点
     * @return 包含源点到其他顶点的最短路径的权重映射
     */
    @Override
    public Map<V, PathInfo<V,E>> shortestPath(V begin) {
//        return Dijkstra(begin);
        return bellmanFord(begin);
    }

    /**
     *
     * 携带最短路径的权重和路径版本
     * @param begin 源点
     * @return 包含到V顶点的最短路径信息: 路径 + 权重
     *
     * BellmanFord算法 : 单源最短路径算法，支持负权边，能检测出负权环
     * 原理: 对所有的边进行V-1次松弛操作(V是节点数量)，得到所有可能的最短路径
     * 时间复杂度：O(EV)， E是边的数量，V是顶点数量
     */
    private Map<V, PathInfo<V, E>> bellmanFord(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;

        Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
        selectedPaths.put(begin, new PathInfo<>(weightManager.zero()));

        int count = vertices.size() - 1;
        for (int i = 0; i < count; i++) { // v - 1 次
            for (Edge<V, E> edge : edges) {
                PathInfo<V, E> fromPath = selectedPaths.get(edge.fromVertex.value);
                if (fromPath == null) continue;
                relax(edge, fromPath, selectedPaths);
            }
        }

        for (Edge<V, E> edge : edges) {
            PathInfo<V, E> fromPath = selectedPaths.get(edge.fromVertex.value);
            if (fromPath == null) continue;
            if (relax(edge, fromPath, selectedPaths)) {
                System.out.println("有负权环");
                return null;
            }
        }

        selectedPaths.remove(begin);
        return selectedPaths;
    }


    /**
     * 松弛
     * @param edge 需要进行松弛的边
     * @param fromPath edge的from的最短路径信息
     * @param paths 存放着其他点（对于dijkstra来说，就是还没有离开桌面的点）的最短路径信息
     */
    private boolean relax(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<V, PathInfo<V, E>> paths) {
        // 新的可选择的最短路径：beginVertex到edge.from的最短路径 + edge.weight
        E newWeight = weightManager.add(fromPath.weight, edge.weight);
        // 以前的最短路径：beginVertex到edge.to的最短路径
        PathInfo<V, E> oldPath = paths.get(edge.toVertex.value);
        if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return false;

        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.toVertex.value, oldPath);
        } else {
            oldPath.edgeInfos.clear();
        }

        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());

        return true;
    }



    /**
     * Dijkstra算法
     * 携带最短路径的权重和路径版本
     * @param begin 源点
     * @return 包含到V顶点的最短路径信息: 路径 + 权重
     */
    private Map<V,PathInfo<V,E>> Dijkstra(V begin){

        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;

        Map<V,PathInfo<V,E>> shortestPath = new HashMap<>();
        Map<Vertex<V,E>,PathInfo<V,E>> paths = new HashMap<>();
        // 松弛
        paths.put(beginVertex, new PathInfo<>(weightManager.zero()));

        // 初始化paths
//        for (Edge<V,E> edge : beginVertex.outEdges) {
//            PathInfo<V,E> pathInfo  = new PathInfo<>(edge.weight);
//            pathInfo.getEdgeInfos().add(edge.info());
//            paths.put(edge.toVertex, pathInfo);
//        }

        while (!paths.isEmpty()){
            Entry<Vertex<V,E>, PathInfo<V,E>> minEntry = getMinPath(paths);
            Vertex<V,E> minVertex = minEntry.getKey();
            PathInfo<V,E> minPath = minEntry.getValue();
            // vertex离开桌面
            shortestPath.put(minVertex.value, minPath);
            // 不需要再找该顶点的最短路径了
            paths.remove(minVertex);

            // 对它的minVertex的outEdges进行松弛操作
            for (Edge<V,E> edge : minVertex.outEdges) {
                // 如果edge.to已经离开桌面，就没必要进行松弛操作
                if (shortestPath.containsKey(edge.toVertex.value)) continue;
                // 松弛edge
                relaxForDijkstra(edge, minPath, paths);
            }
        }

        shortestPath.remove(begin);
        return shortestPath;
    }

    /**
     * 松弛
     * @param edge 需要进行松弛的边
     * @param minPath edge的from的最短路径信息
     * @param paths 存放着其他点（对于dijkstra来说，就是还没有离开桌面的点）的最短路径信息
     */
    private void relaxForDijkstra(Edge<V,E> edge, PathInfo<V,E> minPath, Map<Vertex<V,E>, PathInfo<V,E>> paths){

        // 新的可选择的最短路径：beginVertex到edge.from的最短路径 + edge.weight
        E newWeight = weightManager.add(minPath.getWeight(), edge.weight);
        // 以前的最短路径：beginVertex到edge.to的最短路径
        PathInfo<V,E> oldPath = paths.get(edge.toVertex);
        if (oldPath != null && weightManager.compare(newWeight,oldPath.getWeight()) >= 0) return;

        if (oldPath == null){
            oldPath = new PathInfo<>();
            paths.put(edge.toVertex, oldPath);
        } else {
            // oldPath != null 且 newWeight更小
            oldPath.edgeInfos.clear(); // 之前的路径作废
        }

        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(minPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
    }


    /*
     *  查找权重最小的PathInfo
     */
    private Entry<Vertex<V,E>, PathInfo<V,E>> getMinPath(Map<Vertex<V,E>, PathInfo<V,E>> paths){
         Iterator<Entry<Vertex<V,E>, PathInfo<V,E>>> iterator = paths.entrySet().iterator();
         Entry<Vertex<V,E>, PathInfo<V,E>> minEntry = iterator.next();
         if (iterator.hasNext()){
             Entry<Vertex<V,E>, PathInfo<V,E>> entry = iterator.next();
             if (weightManager.compare(entry.getValue().getWeight(), minEntry.getValue().getWeight()) < 0){
                 minEntry = entry;
             }
         }
        return minEntry;
    }

    /*
     *  迪杰斯特拉算法: 使用前提为不能有负权边
     *  松弛操作: 更新两个顶点之间的最短路径
     *  --此处指更新源点到另外一个顶点的最短路径
     *  --松弛的意义: 尝试找出更短的路径
     *
     *  算法本身很简单，妈的为什么我写了那么久。。。
     *  仅仅携带权重信息版本
     */
    private Map<V,E> Dijkstra1(V begin){
        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;
        Map<V,E> shortestPath = new HashMap<>();
        // 记录源点到所有的顶点的路径信息
        Map<Vertex<V,E>, E> paths = new HashMap<>();
        for (Edge<V,E> edge : beginVertex.outEdges) {
            paths.put(edge.toVertex, edge.weight);
        }

        while (!paths.isEmpty()) {
            Entry<Vertex<V, E>, E> minEntry = getMinEdge1(paths);
            // vertex离开桌面
            Vertex<V,E> vertex = minEntry.getKey();
            // 记录源点到该顶点的最短路径值
            shortestPath.put(vertex.value,minEntry.getValue());
            // 找到了到该顶点的最短路径, 将该条信息从paths中删除
            paths.remove(vertex);

            for (Edge<V,E> edge : vertex.outEdges) {
                // 如果edge.to已经离开桌面，就没必要进行松弛操作
                if (shortestPath.containsKey(edge.toVertex.value)) continue;
                // 新的可选择的最短路径：beginVertex到edge.from的最短路径 + edge.weight
                E newWeight = weightManager.add(minEntry.getValue(), edge.weight);
				// 以前的最短路径：beginVertex到edge.to的最短路径
				E oldWeight = paths.get(edge.toVertex);
				if (oldWeight == null || weightManager.compare(newWeight,oldWeight) < 0){
                    paths.put(edge.toVertex, newWeight);
                }
            }
        }
        // 因为一开始shortestPath中并没有源点到源点的最短路径,在循环时,就可能通过其他顶点回到源点，导致添加到shortestPath中出错，因为源点到源点的最短路径应该是0(泛型)
        shortestPath.remove(begin);
        return shortestPath;
    }

    /*
     *  查找权重最短的边:配合Dijkstra1版本
     */
    private Entry<Vertex<V,E>, E> getMinEdge1(Map<Vertex<V,E>, E> paths){
        Iterator<Entry<Vertex<V,E> ,E>> iterator = paths.entrySet().iterator();
        Entry<Vertex<V,E>, E> minEntry = iterator.next();
        while (iterator.hasNext()){
            Entry<Vertex<V,E>, E> nextEntry = iterator.next();
            if (weightManager.compare(nextEntry.getValue(),minEntry.getValue()) < 0){
                minEntry = nextEntry;
            }
        }
        return minEntry;
    }



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
