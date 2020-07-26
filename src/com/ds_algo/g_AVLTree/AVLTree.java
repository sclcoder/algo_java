package com.ds_algo.g_AVLTree;

import com.ds_algo.f_BST.BBST;
import com.ds_algo.f_BST.BST;

import java.util.Comparator;

/**
 * 平衡搜索二叉树之AVL树
 * @param <E> 泛型
 *
 * AVL树特点:
 *  一、平衡因子(balance factor): 某节点左右子树的高度差
 *  二、特点：
 *           1.每个节点的平衡因子只可能是 1、-1、0
 *           2.每个节点的左右子树的高度差不超过1
 *           3.搜索、添加、删除时间复杂度 O(logn)
 *
 */
public class AVLTree<E> extends BBST<E> {

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    /// AVL树 旋转后需要更新节点高度
    @Override
    protected void afterRotate(Node<E> grandNode, Node<E> parentNode, Node<E> childNode) {
        super.afterRotate(grandNode, parentNode, childNode);
        // 更新高度: 从下向上更新高度
        updateHeight(grandNode);
        updateHeight(parentNode);
        // 注意: grandNode.parent、childNode的高度没有变化,所以不用更新
    }

    /**
     * 恢复平衡
     * @param grand 高度最低的那个不平衡节点
     * 失衡的一定是祖先节点
     */
    private void reBalance(Node<E> grand){
        /// LL LR RR RL
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node  = ((AVLNode<E>) parent).tallerChild();
        if (parent.isLeftChild()){ // L
            if (node.isLeftChild()){ // LL
                rotateRight(grand);
            } else { // LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else { // R
            if (node.isLeftChild()){ // RL
                rotateRight(parent);
                rotateLeft(grand);
            } else { // RR
                rotateLeft(grand);
            }
        }
    }


    /**
     * 添加完节点后的操作: 查找失衡节点并恢复平衡
     *
     * 添加导致的失衡:
     *  1.可能导致所有的 祖先 节点都失衡
     *  2.父节点和其他节点不可能失衡
     */
    @Override
    protected void afterAdd(Node<E> node) {
        // 因为添加了节点，所以要更新node的高度和恢复平衡
        // 如果节点时平衡的，就更新节点的高度
        // 如果节点失衡了，就恢复平衡,高度更新在恢复平衡中更新
        node = node.parent;
        while ( node != null){
            if (isBalance(node)){
                // 更新高度
                updateHeight(node);
            } else {
                // 恢复平衡
                reBalance(node);
                // 整棵树平衡了
                break;
            }
            node = node.parent;
        }
    }

    /**
     *  1.删除操作，只会导致 父节点或者祖先节点中的一个节点失衡 。
     *      因为，只要导致失衡，删除肯定是高度较低的节点
     *  2.旋转失衡的节点后，可能导致祖先节点失衡。
     *      因为，本来删除节点后,失衡节点的高度没有变化，仅仅是平衡因子变化了。但是当将该节点旋转恢复平衡后，
     *      该节点的高度可能会发生变化，可能会导致祖先节点的平衡因子发生改变，导致其失衡
     *
     * @param node 删除的节点
     */
    @Override
    protected void afterRemove(Node<E> node) {
        node = node.parent;
        while ( node != null){
            if (isBalance(node)){
                // 更新高度
                updateHeight(node);
            } else {
                // 恢复平衡
                reBalance(node);
            }
            // 继续查看祖先节点是否失衡
            node = node.parent;
        }
    }

    private boolean isBalance(Node<E> node){
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }

    private void updateHeight(Node<E> node){
        ((AVLNode<E>) node).updateHeight();
    }


    /**
     * 重写创建Node方法
     * @param parent 父节点
     * @param element 值
     * @return AVLNode
     *
     * 在这里出现了java泛型的问题: 查了好久才找到问题
     * 错误: both methods have same erasure, yet neither overrides the other
     * 这是子类、父类泛型同时擦除的问题，导致无法重写方法
     * 泛型时注意点: https://blog.csdn.net/gpsyougo/article/details/89093150
     *
     * 问题在于
     * public class AVLTree<E> extends BinarySearchTree<E>
     * public class BinarySearchTree<E> extends BinaryTree
     * 继承extends BinaryTree时  BinaryTree没有泛型
     *
     * 改为public class BinarySearchTree<E> extends BinaryTree<E> 错误解除
     *
     */
    @Override
    protected Node<E> createNode(Node<E> parent, E element) {
        return new AVLNode<>(parent, element);
    }

    /**
     * AVL树的节点
     * @param <E> 泛型
     */
    public static class AVLNode<E> extends Node<E>{
        public int height = 1; // 节点的初始高度设置1
        public AVLNode(Node<E> parent, E val) {
            super(parent, val);
        }
        // 更新该节点树的高度, 自底向上获取所有节点的高度
        public void updateHeight(){
            height = Math.max(leftHeight(left),rightHeight(right)) + 1;
        }
        // 节点的平衡因子
        public int balanceFactor(){
            return (leftHeight(left) - rightHeight(right));
        }
        // 获取较高高度的子节点
        public Node<E> tallerChild(){
            // 注意此处相等时返回的是 左节点
            return  (leftHeight(left) - rightHeight(right)) < 0 ? right: left;
        }

        private int leftHeight(Node<E> leftNode){
            return leftNode == null ? 0 : ((AVLNode<E>)left).height;
        }

        private int rightHeight(Node<E> rightNode){
            return  rightNode == null ? 0 :((AVLNode<E>)right).height;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null){
                parentString = parent.val.toString();
            }
            return val + "(p_" + parentString + ")_h(" + height + ")";
        }
    }
}
