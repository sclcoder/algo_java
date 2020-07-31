package com.ds_algo.p_unionFind;

/**
 * Union的过程中可能会出现树的不平衡情况,甚至退化为链表的结构
 * 常见的两种优化方案：
 * 1.size : 将元素较少的集合树 嫁接到 元素较多的集合树 ，这种方式也有可能出现不平衡的情况
 * 2.rank : 矮的树 嫁接到 高的树
 */
public class UnionFind_QU extends UnionFind{
    public UnionFind_QU(int capacity) {
        super(capacity);
    }

    /**
     * QuickUnion-find:
     * @param v 要查找的元素
     * @return 找到v所在集合的根节点
     */
    @Override
    protected int find(int v) {
        rangeCheck(v);
        int p = parents[v];
        while (p != parents[p]){
            p = parents[p];
        }
        return p;
    }

    /**
     * QuickUnion-union: 将v1集合的根节点的父节点设置为v2所在集合的根节点
     * @param v1 元素1
     * @param v2 元素2
     */
    @Override
    protected void union(int v1, int v2) {
        int r1 = find(v1);
        int r2 = find(v2);
        if (r1 == r2) return;
        parents[r1] = r2;
    }
}
