package com.ds_algo.p_unionFind;

/**
 * 并查集: 又叫做 不想交集合 DisJoint Set
 * 两个核心操作：
 * Find: 查找元素所在的集合
 * Union: 将两个元素所在的集合合并为一个集合
 *
 * 有两种常见的思路：
 * QuickFind:
 *   1. Find时O(1)
 *   2. Union: O(logN)
 *
 * QuickUnion:
 *  1. Find : O(logN)
 *  2. Union: O(logN)
 */
public abstract class UnionFind {
    // 简单版: 使用数组存储数据,parents存放每个值的根节点,数组的下表代表值
    protected int[] parents;

    protected UnionFind(int capacity) {
        if (capacity <= 0){
            throw new IllegalArgumentException("capacity must larger than zero");
        }
        parents = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            // 初始化数组：每个元素的根节点都是自己本身
            parents[i] = i;
        }
    }

    /**
     * 查找元素所在的集合(根节点)
     * @param v 要查找的元素
     * @return 元素的根节点
     */
    protected abstract int find(int v);

    /**
     * 将两个集合合并为一个集合
     * @param v1 元素1
     * @param v2 元素2
     */
    protected abstract void union(int v1, int v2);

    /**
     * 查看v1、v2是不是在同一个集合
     * @param v1 v1
     * @param v2 v2
     * @return 是不是在同一个集合
     */
    protected boolean isSame(int v1, int v2){
        return find(v1) == find(v2);
    }

    protected void rangeCheck(int v) {
        if (v < 0 || v >= parents.length) {
            throw new IllegalArgumentException("v is out of bounds");
        }
    }
}
