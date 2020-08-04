package com.ds_algo.q_graph;

import com.ds_algo.q_graph.Graph.EdgeInfo;
import com.ds_algo.q_graph.Graph.WeightManager;

import java.util.List;
import java.util.Set;

public class TestGraphMain {
    static WeightManager<Double> weightManager = new WeightManager<Double>() {
        @Override
        public int compare(Double w1, Double w2) {
            return w1.compareTo(w2);
        }

        @Override
        public Double add(Double w1, Double w2) {
            return w1 + w2;
        }

        @Override
        public Double zero() {
            return 0.0;
        }
    };
    public static void main(String[] args) {
//        test();
//        testBfs();
//        testDfs();
//        testTopo();
        testMst();
    }
    static void testMst() {
        Graph<Object, Double> graph = undirectedGraph2(Data.MST_01);
        Set<EdgeInfo<Object, Double>> infos = graph.mst();
        for (EdgeInfo<Object, Double> info : infos) {
            System.out.println(info);
        }
    }
    static void testTopo() {
        Graph<Object, Double> graph = directedGraph(Data.TOPO);
        List<Object> list = graph.topologySort();
        System.out.println(list);
    }

    static void testDfs() {

        Graph<Object, Double> graph1 = undirectedGraph(Data.DFS_01);
        graph1.dfs(1, (Object v) -> {
            System.out.print(v + " ");
            return false;
        });
        System.out.println("无向图DFS");


        Graph<Object, Double> graph2 = directedGraph(Data.DFS_02);
        graph2.dfs("a", (Object v) -> {
            System.out.print(v + " ");
            return false;
        });
        System.out.println("有向图DFS");

    }

    static void testBfs() {
        Graph<Object, Double> graph1 = undirectedGraph(Data.BFS_01);
        graph1.bfs("A", (Object v) -> {
            System.out.print(v + " ");
            return false;
        });
        System.out.println("无向图BFS");


        Graph<Object, Double> graph2 = directedGraph(Data.BFS_02);
        graph2.bfs(0, (Object v) -> {
            System.out.print(v + " ");
            return false;
        });

        System.out.println("有向图BFS");

        Graph<Object, Double> graph3 = undirectedGraph(Data.BFS_03);
        graph3.bfs(0, (Object v) -> {
            System.out.print(v + " ");
            return false;
        });
        System.out.println("无向图BFS");

        Graph<Object, Double> graph4 = directedGraph(Data.BFS_04);
        graph4.bfs(5, (Object v) -> {
            System.out.print(v + " ");
            return false;
        });
        System.out.println("有向图BFS");

    }

    static void test() {
		ListGraph<String, Integer> graph = new ListGraph<>();
//		graph.addEdge("V0", "V1");
//		graph.addEdge("V1", "V0");
//
//		graph.addEdge("V0", "V2");
//		graph.addEdge("V2", "V0");
//
//		graph.addEdge("V0", "V3");
//		graph.addEdge("V3", "V0");
//
//		graph.addEdge("V1", "V2");
//		graph.addEdge("V2", "V1");
//
//		graph.addEdge("V2", "V3");
//		graph.addEdge("V3", "V2");
//
//		graph.print();


		graph.addEdge("V1", "V0", 9);
//        graph.addEdge("V1", "V0", 9);
        graph.addEdge("V1", "V2", 3);
		graph.addEdge("V2", "V0", 2);
		graph.addEdge("V2", "V3", 5);
		graph.addEdge("V3", "V4", 1);
		graph.addEdge("V0", "V4", 6);

//		graph.removeEdge("V0", "V4");
		graph.removeVertex("V0");

		graph.print();

    }

    /**
     * 有向图
     */
    private static Graph<Object, Double> directedGraph2(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
            }
        }
        return graph;
    }

    /**
     * 无向图
     * @param data
     * @return
     */
    private static Graph<Object, Double> undirectedGraph2(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
                graph.addEdge(edge[1], edge[0]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
                graph.addEdge(edge[1], edge[0], weight);
            }
        }
        return graph;
    }

    /**
     * 有向图
     */
    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>();
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
            }
        }
        return graph;
    }

    /**
     * 无向图
     * @param data
     * @return
     */
    private static Graph<Object, Double> undirectedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>();
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
                graph.addEdge(edge[1], edge[0]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
                graph.addEdge(edge[1], edge[0], weight);
            }
        }
        return graph;
    }
}
