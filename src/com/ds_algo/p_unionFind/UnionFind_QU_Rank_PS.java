package com.ds_algo.p_unionFind;

/**
 * 路径分裂Path_Split ()：
 *   使路径上的每个节点都指向其祖父节点(parent.parent)
 *
 *   7 (7)                                      7 (7)
 *   |                                          /    \
 *   6 (7)                                  6 (7)     5 (7)
 *   |                                        |        |
 *   5 (6)                                  4 (6)     3 (5)
 *   |            ---->path split          |          |
 *   4 (5)                                2 (4)     1 (3)
 *   |
 *   3 (4)
 *   |
 *   2 (3)
 *   |
 *   1 (2)
 *
 */
public class UnionFind_QU_Rank_PS extends UnionFind_QU_Rank{
    public UnionFind_QU_Rank_PS(int capacity) {
        super(capacity);
    }

    @Override
    protected int find(int v) {
        rangeCheck(v);
        int p = v;
        while (p  != parents[p]){
            p = parents[p];
            parents[p] = parents[parents[p]];
            p = parents[p];
        }
        return p;
    }
}
