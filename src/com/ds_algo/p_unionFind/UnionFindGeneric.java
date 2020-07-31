package com.ds_algo.p_unionFind;

import com.ds_algo.k_hashTable.hashMap.HashMap;
import java.util.Objects;

/**
 * 通用并查集
 * @param <E> 泛型
 *
 * 基于QuickUnion实现，并且基于Rank优化union和Path-half优化find
 */
public class UnionFindGeneric<E>{
    // 如何存储泛型数据, 因为涉及到泛型和扩容的问题
    // 考虑使用HashMap存储一传入的对象为key 设计节点Node记录该集合的信息

    // map中存储所有的Node<E>数据，相同的集合中其节点通过parent指针连接在一起
    private HashMap<E,Node<E>> map = new HashMap<>();

    public void makeSet(E element){
        if (map.containsKey(element)) return;
        map.put(element, new Node<>(element));
    }

    public Node<E> findNode(E element){
        Node<E> node = map.get(element);
        if (node == null) return null;
        // 进行path half对路径进行压缩
        while (! Objects.equals(node.parent.value,node.value)){
            node.parent = node.parent.parent;
            node = node.parent;
        }
        return node;
    }

    public E find(E element){
        Node<E> node = findNode(element);
        return node == null ? null : node.value;
    }

    public void union(E e1, E e2){
        Node<E> r1 = findNode(e1);
        Node<E> r2 = findNode(e2);
        if (r1 == null || r2 == null) return;
        if (Objects.equals(r1.value,r2.value)) return;

        // 基于rank的union
        if (r1.rank < r2.rank){
            r1.parent = r2;
        } else if (r1.rank > r2.rank){
            r2.parent = r1;
        } else {
            r1.parent = r2;
            r2.rank += 1;
        }
    }

    public boolean isSame(E e1, E e2){
        return Objects.equals(find(e1), find(e2));
    }

    private class Node<E>{
        E value;
        Node<E> parent = this;
        int rank = 1; // 节点所在集合的树高度
        public Node(E element) {
            this.value = element;
        }
    }

}
