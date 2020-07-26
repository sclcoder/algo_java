package com.ds_algo.h_RBTree;

import com.ds_algo.f_BST.BST;
import java.util.Comparator;

/**
 * 红黑树
 * @param <E> 元素
 * 红黑树满足的性质
 */
public class RBTree<E> extends BST<E> {
    public static final boolean RED = true;
    public static final boolean BLACK = false;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        super.afterAdd(node);
    }

    @Override
    protected void afterRemove(Node<E> node) {
        super.afterRemove(node);
    }

    @Override
    protected Node<E> createNode(Node<E> parent, E element) {
        return new RBNode<>(parent, element);
    }

    public static  class RBNode<E> extends Node<E>{
        // 新创建节点默认为红色,这样可以满足红黑树的第 1、2、3、5性质
        boolean color = RED;
        public RBNode(Node<E> parent, E val) {
            super(parent, val);
        }
    }
}
