package com.ds_algo.h_RBTree;

import com.ds_algo.f_BST.BBST;
import com.ds_algo.f_BST.BST;

import java.util.Comparator;

/**
 * 红黑树
 * @param <E> 元素
 * 红黑树必须满足一下5条性质:
 *           1.节点都是Red 或 Black
 *           2.根节点时Black
 *           3.叶子节点(空节点)都是Black : 注意空节点即null都认为是Black，而且空节点也算路径的一部分
 *           4.Red节点的子节点都是Black
 *             a.Red节点的parent是Black
 *             b.从根节点到叶子节点的所有路径上，不能有两个连续的Red节点
 *           5.从任意节点到叶子节点的所有路径都包含相同个数的Black节点
 *
 *   4阶B树和红黑树可以完美对应
 */
public class RBTree<E> extends BBST<E> {
    public static final boolean RED = false;
    public static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }


    // 红黑树旋转后不需要做额外操作，这与AVL树不同，AVL树旋转后要更新节点的高度
    @Override
    protected void afterRotate(Node<E> grandNode, Node<E> parentNode, Node<E> childNode) {
        super.afterRotate(grandNode, parentNode, childNode);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        /**
         * 4阶B树和红黑树可以完美对应
         * 添加红黑树节点一共有12中情况
         * 一、有4中情况满足红黑树的性质4 :  parent为Black
         *     a.同时满足4阶B树 b.不需要做额外处理
         *
         * 二、有8中情况不满足红黑树性质4 ： parent为Red 即出现连续的Red节点
         *   1. 有4中情况属于B树不会上溢 ，判定条件 uncle 不是 Red
         *     具体又分为 LL、RR 、RL、LR
         *
         *     LL、RR 修复红黑树性质4需要做的操作:
         *          a. parent 染为 Black , grand 染为 Red
         *          b. grand进行单次旋转 LL时右旋 RR时左旋
         *
         *     RL、LR 修复红黑树性质4需要做的操作:
         *          a. 将自己染Black, grand染 Red
         *          b. 进行双旋操作
         *                  LR : parent左旋 , grand右旋
         *                  RL : parent右旋 , grand左旋
         *
         *   2. 有4中情况属于B树上溢 ，判定条件 uncle 是 Red
         *      具体的4中情况为 LL、RR、RL、LR
         *      LL、RR、RL、LR修复红黑树性质4需要做的操作
         *          a. 将 parent、uncle染为Black
         *          b. grand向上合并: 将grand染Red，当做新添加的节点处理（递归操作）
         *
         *   经过以上步骤红黑树的添加操作结束
         */

        Node<E> parent = node.parent;

        // 添加的节点为根节点或上溢到根节点
        if (parent == null){
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;

        // 如果父节点是红色

        // 叔父节点
        Node<E> uncle = parent.sibling();
        Node<E> grand = parent.parent;
        if (isRed(uncle)){ // 叔父节点为Red【B树节点上溢】

            black(parent);
            black(uncle);
            // 将grand染Red并上溢
            afterAdd(red(grand));

        } else { // 叔父节点不是Red
            if (parent.isLeftChild()){ // L
                if (node.isLeftChild()){ // LL
                    red(grand);
                    black(parent);
                    // grand右旋
                    rotateRight(grand);
                } else { // LR
                    black(node);
                    red(grand);
                    rotateLeft(parent);
                    rotateRight(grand);
                }
            } else { // R
                if (node.isLeftChild()){ // RL
                    black(node);
                    red(grand);
                    rotateRight(parent);
                    rotateLeft(grand);
                } else { // RR
                    red(grand);
                    black(parent);
                    // grand左旋
                    rotateLeft(grand);
                }
            }
        }


    }

    @Override
    protected void afterRemove(Node<E> node) {
        super.afterRemove(node);
    }

    @Override
    protected Node<E> createNode(Node<E> parent, E element) {
        return new RBNode<>(parent, element);
    }


    private boolean isRed(Node<E> node){
        return colorOfNode(node) == RED;
    }

    private boolean isBlack(Node<E> node){
        return colorOfNode(node) == BLACK;
    }

    private boolean colorOfNode(Node<E> node){
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }


    private RBNode<E> color(Node<E> node, boolean color){
        if (node != null) ((RBNode<E>) node).color = color;
        return (RBNode<E>) node;
    }

    private RBNode<E> black(Node<E> node){
        return color(node,BLACK);
    }
    private RBNode<E> red(Node<E> node){
        return color(node, RED);
    }

    public static  class RBNode<E> extends Node<E>{
        // 新创建节点默认为红色,这样可以满足红黑树的第 1、2、3、5性质 性质4不一定满足
        boolean color = RED;
        public RBNode(Node<E> parent, E val) {
            super(parent, val);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + val.toString();
        }
    }
}
