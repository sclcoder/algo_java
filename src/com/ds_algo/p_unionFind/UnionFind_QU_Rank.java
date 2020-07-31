package com.ds_algo.p_unionFind;

import java.util.Arrays;

/**
 * 基于Rank的优化: 将矮的树 嫁接到 高的树，这样来降低树的高度
 */
public class UnionFind_QU_Rank extends UnionFind_QU{
    protected final int[] ranks;

    public UnionFind_QU_Rank(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        Arrays.fill(ranks,1); // 树的默认高度为1
    }

    /**
     * 将矮的树 嫁接到 高的树，这样来降低树的高度
     * @param v1 元素1
     * @param v2 元素2
     */
    @Override
    protected void union(int v1, int v2) {
        int r1 = find(v1);
        int r2 = find(v2);
        if (r1 == r2) return;

        if (ranks[r1] < ranks[r2]){
            parents[r1] = r2;
        } else if (ranks[r1] > ranks[r2]){
            parents[r2] = r1;
        } else {
            parents[r1] = r2;
            ranks[r2] += 1;
        }
    }
}
