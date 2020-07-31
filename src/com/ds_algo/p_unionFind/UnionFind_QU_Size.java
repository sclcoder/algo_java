package com.ds_algo.p_unionFind;

import java.util.Arrays;
/**
 * Union的过程中可能会出现树的不平衡情况,甚至退化为链表的结构
 * 基于Size，对UnionFind_QU进行优化:
 *  --基于size的优化: 将元素少的树嫁接到元素多的树的根节点
 *  --这样可能减少树的高度,但也有时不会减少，可改为Rank的优化方式
 *
 * 为了记忆集合的size，这里使用数组，记录每个元素所在集合的size
 */
public class UnionFind_QU_Size extends UnionFind_QU{
    private final int[] sizes;
    public UnionFind_QU_Size(int capacity) {
        super(capacity);
        sizes = new int[capacity];
        Arrays.fill(sizes,1); // 默认每个元素都是一个集合，其size为1
    }


    /**
     * 比较v1所在集合和v2所在集合size,将较小集合的根设置为较大集合的根
     * @param v1 元素1
     * @param v2 元素2
     */
    @Override
    protected void union(int v1, int v2) {
        int r1 = find(v1);
        int r2 = find(v2);
        if (r1 == r2) return;

        // 寻找size较小的树
        int p1 = parents[v1];
        int p2 = parents[v2];

        // union操作一开始就是自底向上的
        if (sizes[p1] < sizes[p2]){
            parents[p1] = p2;
            sizes[p2] += sizes[p1];
        } else {
            parents[p2] = p1;
            sizes[p1] += sizes[p2];
        }
    }
}
