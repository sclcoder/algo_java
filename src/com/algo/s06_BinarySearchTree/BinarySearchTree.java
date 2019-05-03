package com.algo.s06_BinarySearchTree;
import com.algo.s06_BinarySearchTree.printer.BinaryTreeInfo;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

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
    // 内部提供的访问器
    public interface Visitor<T>{
        void visit(T element);
    }
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

    /**
     * 添加元素
     * @param element
     */
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
                cmp = compare(element,parent.element);
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
             * 找到的父节点可能是叶子节点也可能是有一个子节点的节点 所以要比较
             */
            Node<T> newNode = new Node<>(element,parent);
            if (cmp < 0) { // 和父节点比较
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

    /**********     二叉树遍历   *******************/
    /**
     * 递归的方式
     * 注意体会递归的过程
     * 通过函数一层一层的调用,在最后的条件下又一层一层的返回
     */
    /**
     * 前序遍历 - 先访问父节点再访问子节点
     * 通过一个节点开始遍历
     */
//    public void  preOrderTraversal(){
//        preOrderTraversal(root);
//    }
//    private void  preOrderTraversal(Node<T> node){
//        if (node == null) return;
//        System.out.println(node.element);
//        preOrderTraversal(node.left);
//        preOrderTraversal(node.right);
//        return; // 一个完整流程结束后退出函数栈
//    }


    public void  preOrderTraversal(Visitor<T> visitor){
        preOrderTraversal(root,visitor);
    }
    private void  preOrderTraversal(Node<T> node,Visitor<T> visitor){
        if (node == null || visitor == null) return;
        visitor.visit(node.element);
        preOrderTraversal(node.left,visitor);
        preOrderTraversal(node.right,visitor);
        return; // 一个完整流程结束后退出函数栈
    }

    /**
     * 中序遍历 - 先访问父节点再访问子节点
     */
//    public void  inOrderTraversal(){
//        inOrderTraversal(root);
//    }
//    private void  inOrderTraversal(Node<T> node){
//        if (node == null) return;
//        inOrderTraversal(node.left);
//        System.out.println(node.element);
//        inOrderTraversal(node.right);
//        return; // 一个完整流程结束后退出函数栈
//    }
    public void  inOrderTraversal(Visitor<T> visitor){
        inOrderTraversal(root,visitor);
    }
    private void  inOrderTraversal(Node<T> node,Visitor<T> visitor){
        if (node == null || visitor == null) return;
        inOrderTraversal(node.left,visitor);
        visitor.visit(node.element);
        inOrderTraversal(node.right,visitor);
        return; // 一个完整流程结束后退出函数栈
    }
    /**
     * 后序遍历 - 先访问父节点再访问子节点
     */
//    public void  postOrderTraversal(){
//        postOrderTraversal(root);
//    }
//    private void  postOrderTraversal(Node<T> node){
//        if (node == null) return;
//        postOrderTraversal(node.left);
//        postOrderTraversal(node.right);
//        System.out.println(node.element);
//        return; // 一个完整流程结束后退出函数栈
//    }
    public void  postOrderTraversal(Visitor<T> visitor){
        postOrderTraversal(root,visitor);
    }
    private void  postOrderTraversal(Node<T> node,Visitor<T> visitor){
        if (node == null || visitor == null) return;
        postOrderTraversal(node.left,visitor);
        postOrderTraversal(node.right,visitor);
        visitor.visit(node.element);
        return; // 一个完整流程结束后退出函数栈
    }


    /** 层次遍历非常重要
     * 层次遍历 - 一层一层的遍历
     * 一时很难想到,引导一下:
     * 按照层次访问二叉树时的顺序 1.访问父节点node 2.访问左节点node.left 3.访问左节点node.right
     * 特点: 这个顺序是很自然的先访问先输出后访问后输出,可以联想到队列有该特点
     *
     * 步骤：
     * 1. 将根节点入队列
     * 2. 循环执行一下操作,直到队列为空
     *     a.将队头节点A出队列,进行访问
     *     b.将节点A的左入队列
     *     c.将节点A的左入队列
     */
    public void  levelOrderTraversal(){
        if (root == null) return;
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<T> node = queue.poll();
            System.out.println(node.element);
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
    }

    /**
     * 有访问器的层序遍历
     */
    public void levelOrder(Visitor<T> visitor){
        if (root == null || visitor == null) return;
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<T> node = queue.poll();
//            System.out.println(node.element);
            visitor.visit(node.element);
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
    }

    /**
     * 获取二叉树的高度
     * @return
     */
    public int height(){
        return height1(root);
    }

    /**
     * 递归思路: 树的高度就是根节点的高度
     * 根节点的高度 = 左右子树的最大高度 + 1
     */
    private int height1(Node<T> node){
        if (node == null) return 0;
        return 1 + Math.max(height1(node.left),height1(node.right));
    }

    /**
     * 非递归: 树的高度即层数 考虑层序遍历
     * 访问完一层height增加1  何时访问完一层: 记录每一层的值
     * @return
     */
    public int height2(){
        if (root == null) return 0;
        // 数的高度
        int height = 0;
        // 存储每一层的元素数量
        int levelSize = 1;
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<T>node = queue.poll();
            levelSize--;
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
            if (levelSize == 0){ // 即将访问下一层
                levelSize = queue.size();
                height++;
            }
        }

        return height;
    }


    /**
     * 二叉树自实现打印展示
     * 利用遍历算法实现打印展示逻辑
     */
    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       toString(root,sb,"");
       return sb.toString();
    }
    private void toString(Node<T> node, StringBuilder sb,String prefix){
        if (node == null) return;
        // 在输出内容之前先输出prefix
        sb.append(prefix).append(node.element).append("\n");
        toString(node.left,sb,prefix + "L-");
        toString(node.right,sb,prefix + "R-");
        return;
    }

    /**********     私有方法   *******************/
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
