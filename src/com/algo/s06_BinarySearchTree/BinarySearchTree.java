package com.algo.s06_BinarySearchTree;
import com.algo.s06_BinarySearchTree.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 真二叉树：所有节点的度要么是0 要么是2
 * 满二叉树：所有节点的度要么是0 要么是2 且叶子节点在最后一层
 * 完全二叉树：叶子节点最后二层 且最后一层的叶子节点左对齐
 */


/**
 *  * 二叉搜索树:
 *  任意节点的值都大于(或小于)其左子树所有节点的值
 *  任意节点的值都小于(或大于)其右子树所有节点的值
 *  左右子树也是一个搜索二叉树
 */
// public class BinarySearchTree<T extends Comparable>  设计：不在此处强制要求实现Comparable,而是在比较时再强制
public class BinarySearchTree<T> implements BinaryTreeInfo  {
    private int size;
    private Node<T> root;
    private Comparator<T> comparator; // java.util.Comparator 官方比较器
    private static class Node<T>{
        T element;
        Node<T> left;
        Node<T> right;
        Node<T> parent;
        public Node(T element, Node<T>parent){
            this.element = element;
            this.parent = parent;
        }
    }
    // 构造函数
    BinarySearchTree(){
        this(null);
    }
    BinarySearchTree(Comparator comparator){
        this.comparator = comparator;
    }

    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public void clear(){

    }
    public void add(T element){
        elementNotNullCheck(element);
        // 添加第一个节点
        if (root == null){
            root = new Node<>(element,null);
            size++;
            return;
        } else {
            /**
             * 找到父节点
             * 创建新节点
             * 添加到left or right
             */

            Node<T> parent = root; // 记录父节点
            Node<T> node = root;
            int cmp = 0;
            /**
             * 寻找父节点步骤
             * 1.如果插入的值小于当前节点: 沿着当前节点的左子树找
             * 2.如果插入的值大于当前节点: 沿着当前节点的右子树找
             */
            while (node != null){
                parent = node;
                cmp = compare(element,node.element);
                if (cmp < 0){ // 小于当前节点
                    node = node.left;
                } else if (cmp > 0){ // 大于当前节点
                    node = node.right;
                } else {  // 相等
                    node.element = element;
                    return;
                }
            }
            /**
             * 添加新节点
             */
            Node<T> newNode = new Node<>(element,parent);
            if (cmp < 0) { // 和父节点比较 小
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
            size++;
            return;
        }
    }
    public void remove(T element){

    }
    public boolean contains(T element){
        return true;
    }

    private void elementNotNullCheck(T element){
        if (element == null){
            throw new IllegalArgumentException("element must not null");
        }
    }

    /**
     * 比较元素大小
     * @return
     * 假设
     * e1 < e2 返回 -1
     * e1 = e2 返回 0
     * e1 > e2 返回 1
     */
    private int compare(T e1, T e2){
        // 优先使用比较器
        if (comparator != null){
            return comparator.compare(e1, e2);
        }
        // 没有比较器 将元素强制转化为可比较的（相当于强制要求元素是可比价的）
        return ((Comparable<T>) e1).compareTo(e2);
    }

    /**
     * BinaryTreeInfo接口方法
     * @return
     */
    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<T>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<T>)node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<T>)node).element;
    }
}
