package com.ds_algo.p_unionFind;

public class UnionFind_QF extends UnionFind{

    public UnionFind_QF(int capacity) {
        super(capacity);
    }

    /**
     * QuickFind-find : 操作每个节点的根节点都是其父节点
     * @param v 要查找的元素
     * @return 找到的根节点
     */
    @Override
    public int find(int v) {
        rangeCheck(v);
        return parents[v];
    }

    /**
     * QuickFind-union操作 : 将v1集合的所有元素的根设置为v2所在集合的根
     * @param v1 元素1
     * @param v2 元素2
     * @return 合并后的根节点
     */
    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        // 将v1所在集合的所有元素的根设置为v2所在集合的根 父节点就是根节点
        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == p1){
                parents[i] = p2;
            }
        }
    }
}
