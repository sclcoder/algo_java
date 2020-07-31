package com.ds_algo.p_unionFind;

/**
 * 路径分裂Path_half ()：
 *   使路径上的每间隔一个节点就指向其祖父节点(parent.parent)
 *
 *   7 (7)                                           7(7)
 *   |                                              /    \
 *   6 (7)                                        6(7)   5(7)
 *   |                                                  /   \
 *   5 (6)                                          4(6)     3(5)
 *   |            ---->path half                             /   \
 *   4 (5)                                                2(4)    1(3)
 *   |
 *   3 (4)
 *   |
 *   2 (3)
 *   |
 *   1 (2)
 *
 */
public class UnionFind_QU_Rank_PH extends UnionFind_QU_Rank{

    public UnionFind_QU_Rank_PH(int capacity) {
        super(capacity);
    }

    @Override
    protected int find(int v) {
        rangeCheck(v);
        int g = v;
        while (g != parents[parents[g]]){
            parents[g] = parents[parents[g]];
            g = parents[g];
        }
        return g;
    }

}
