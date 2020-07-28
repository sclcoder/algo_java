package com.ds_algo.f_BST;

import java.util.Comparator;

/**
 * 平衡搜索二叉树
 * @param <E> 泛型
 */
@SuppressWarnings("unused")
public class BBST<E> extends BST<E> {
    public BBST(Comparator<E> comparator) {
        super(comparator);
    }

    public BBST() {
        this(null);
    }


    /**
     * 右旋
     * @param grandNode 要旋转的node
     *
     *                  简略版对g进行右旋后示意图 具体看ppt
     *              g                   p
     *             /                 /    \
     *            p      ------->   n      g
     *           /
     *          n
     *
     *     需要更改的指针
     *     g.left = p.right
     *     p.right = g;
     *     等等。。。
     */
    protected void rotateRight(Node<E> grandNode){
        Node<E> parentNode = grandNode.left;
        Node<E> childNode = parentNode.right;

        /// 更改left、right指针
        grandNode.left = childNode;
        parentNode.right = grandNode;

        afterRotate(grandNode,parentNode,childNode);
    }

    /**
     * 左旋
     * @param grandNode 要旋转的node
     */
    protected void rotateLeft(Node<E> grandNode){
        Node<E> parentNode = grandNode.right;
        Node<E> childNode = parentNode.left;

        // 更改 left、right指针
        grandNode.right = parentNode.left;
        parentNode.left = grandNode;

        afterRotate(grandNode,parentNode,childNode);
    }

    protected void afterRotate(Node<E> grandNode , Node<E> parentNode , Node<E> childNode){
        if (grandNode.isRightChild()){
            grandNode.parent.right = parentNode;
        } else if (grandNode.isLeftChild()){
            grandNode.parent.left = parentNode;
        } else {
            root = parentNode;
        }

        // 更改parent指针
        if (childNode != null){
            childNode.parent = grandNode;
        }
        parentNode.parent = grandNode.parent;
        grandNode.parent = parentNode;
    }
}
