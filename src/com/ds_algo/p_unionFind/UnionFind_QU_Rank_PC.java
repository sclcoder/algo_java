package com.ds_algo.p_unionFind;

/**
 * Quick Union - 基于rank的优化 - 路径压缩(Path Compression)
 * 虽然有了基于rank的优化，树相对平衡了，但是随着Union的次数增多，树高度会变得很高，
 * 从而导致find操作变慢，特别是底层节点
 *
 *  路径压缩：
 *  在find时使路径上的所有节点都指向根节点，从而降低树的高度
 *
 *
 *
 *      7 (7)
 *       |                                                7(7)
 *      6 (7)                                 6(7)  5(7)  4(7) 3(7) 2(7) 1(7)
 *       |
 *      5 (6)
 *       |            ------>path compress
 *      4 (5)
 *       |
 *      3 (4)
 *       |
 *      2 (3)
 *      |
 *      1 (2)
 */
public class UnionFind_QU_Rank_PC extends UnionFind_QU_Rank {
    public UnionFind_QU_Rank_PC(int capacity) {
        super(capacity);
    }

    @Override
    protected int find(int v) {
        int r =  super.find(v);
        int p = v;
        while (parents[p] != r){
            parents[p] = r;
            p = parents[p];
        }
        return r;
    }

    // 递归写法
//    @Override
//    public int find(int v) { // v == 1, parents[v] == 2
//        rangeCheck(v);
//        if (parents[v] != v) {
//            parents[v] = find(parents[v]);
//        }
//        return parents[v];
//    }
}
