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
        public boolean isLeaf(){
            return left == null && right == null;
        }
        public boolean hasTwoChildren(){
            return left != null && right != null;
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
     * 访问完一层height增加1  何时访问完一层: 当这一层的节点全部出队列后。所以还需要记录每一层的值。
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
     * 判断是否是完全二叉树
     */
    public boolean isComplate(){
        /**
         * 判断逻辑
         * 1.如果树为空 返回false
         * 2.如果树不是空，开始层次遍历二叉树
         *   a.如果node.left!=null && node.right!=null将node.left、node.right按顺序入队列
         *   b.如果node.left==null && node.right!=null返回false
         *   c.如果node.left!=null && node.right==null
         *     或者 如果node.left ==null && node.right==null
         *     那么只有接下来的节点都是叶子节点才返回true 否则返回false
         */
        if (root == null) return false;
        Queue<Node<T>> queue = new LinkedList<>();
        boolean needLeafNode = false;
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<T> node = queue.poll();

            if (needLeafNode){ // 要求是叶子节点
                // 如果不是叶子节点返回false
                if (node.left != null || node.right != null) return false;
            }

            if (node.left != null && node.right != null){
                // 入队列
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left == null && node.right != null){
                return false;
            } else {
                /**
                 * 我的开始的错误写法
                 *  if (node.left != null || node.right != null){
                 *     return false;
                 *  }
                 *  错误原因: 将当前的节点node和其node.left、node.right节点搞混乱了
                 *           当前遍历到（出队列）的的节点是node 而不是其子节点
                 */

                /**
                 * 明确的思路: 来到这说明要求下一次访问的节点必须是叶子节点。
                 * 所以这里搞个标志,标明下次出栈的节点必须为叶子节点
                 */
                needLeafNode = true;
            }
        }
        return true;
    }

    /**
     * 判断是否是完全二叉树
     */
    public boolean isComplate2(){
        if (root == null) return false;
        boolean needLeafNode = false;
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<T> node = queue.poll();
            if (needLeafNode && !node.isLeaf()) return false;
            if (node.hasTwoChildren()){ // 有左右节点
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left == null && node.right != null){
                return false;
            } else {
                needLeafNode = true;
            }
        }
        return true;
    }

    /**
     * 查找前驱节点
     * 前驱节点: 中序遍历时的前一个节点
     * 步骤：比较难想出来
     * 情况1:     node.left != null
     * 查找过程:  根据中序遍历的特点可知，其前驱肯定是左子树中的最后一个节点
     *           predecessor = node.left.right.right...
     *           终止条件: right = null;
     *
     * 情况2:    node.left == null && node.parent != null;
     * 查找过程:  没有左子树,那就不能向左子树寻找,由于是寻找前驱节点,所以肯定也不能寻找右子树.所以只能向上寻找即查找父节点。
     *           如果node在父节点的左子树中,那是该节点的后驱节点.只有当node在的父节点右子树中时,才能说明找到了前驱。否则没有前驱。
     *           predecessor = node.parent.parent...
     *           终止条件 node在parent的右子树中 或 parent == null(说明没有前驱)
     * 情况3:     node.left == null && node.parent == null;
     * 查找过程:  属于情况2中的一种中间情况 即没有前驱节点
     *
     */
    public Node<T> predecessor(Node<T> node){
        if (node == null) return null;
        // 1.前驱节点在左子树中
        Node<T> p = node.left;
        if (p != null){
            while (p.right != null){
                p = p.right;
            }
            return p;
        }

        // 2.前驱节点在父节点(祖父节点...)中的情况
        while (node.parent != null && node.parent.left == node){
            node = node.parent;
        }
        return node.parent;
    }

    /**
     * 查找后驱节点
     * 后驱节点: 中序遍历时的后一个节点
     * 步骤：比较难想出来
     * 情况1:     node.right != null
     * 查找过程:  根据中序遍历的特点可知，其后驱肯定是右子树中的第一个节点
     *           predecessor = node.right.left.left...
     *           终止条件: left = null;
     *
     * 情况2:    node.right == null && node.parent != null;
     * 查找过程:  没有右子树,那就不能向右子树寻找,由于是寻找后驱节点,所以肯定也不能寻找左子树.所以只能向上寻找即查找父节点。
     *           如果node在父节点右子树中,那是该节点的前驱节点.只有找到的node在父节点的左子树中,才能说明找到了后驱。否则没有后驱。
     *           predecessor = node.parent.parent...
     *           终止条件 node在parent的左子树中 或 parent == null(说明没有前驱)
     * 情况3:     node.right == null && node.parent == null;
     * 查找过程:  属于情况2中的一种中间情况 即没有后驱节点
     *
     */
    public Node<T> successor(Node<T> node){
        if (node == null) return null;

        // 后驱节点在右子树中
        Node<T> s = node.right;
        if (s != null){
            while (s.left != null){
                s = s.left;
            }
            return s;
        }
        // 后驱节点在父节点(祖父节点...)中的情况
        while (node.parent != null && node.parent.right == node){
            node = node.parent;
        }
        return node.parent;
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
