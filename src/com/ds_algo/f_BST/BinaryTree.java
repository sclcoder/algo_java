package com.ds_algo.f_BST;

import com.tool.binaryTree.printer.BinaryTreeInfo;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树
 * @param <E> 泛型
 */
@SuppressWarnings({"unused","unchecked"})
public class BinaryTree<E> implements BinaryTreeInfo {
    protected Node<E> root;
    protected int size;

    /// 内部节点类
    public static class Node<E>{
        public E val;
        public Node<E> parent;
        public Node<E> left;
        public Node<E> right;

        public Node(Node<E> parent, E val) {
            this.parent = parent;
            this.val = val;
        }
        public boolean isLeaf(){
            return this.left == null && this.right == null;
        }
        public boolean hasTwoChildren(){
            return this.left != null && this.right != null;
        }
        public boolean isLeftChild(){
            if (parent != null){
                return parent.left == this;
            }
            return false;
        }

        public boolean isRightChild(){
            if (parent != null){
                return parent.right == this;
            }
            return false;
        }

        // 兄弟节点
        public Node<E> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }
    }

    /// 访问器
    public static abstract class Visitor<E>{
        boolean stop;
        /**
         * @return 如果返回true，就代表停止遍历
         */
        public abstract boolean visit(E element);
    }

    public int size(){
        return size;
    }

    public void clear(){
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 添加不同类型的Node<E>
     * @param parent 父节点
     * @param element 值
     * @return 新创建的节点
     */
    protected Node<E> createNode(Node<E> parent, E element){
        return new Node<>(parent, element);
    }

    /**
     * 查找前驱节点
     * 前驱节点: 中序遍历时的前一个节点
     * 步骤: 考虑到中序遍历的特点就可以思考出来 easy!!!
     * 情况1:     有左子树 node.left != null
     * 查找过程:  根据中序遍历的特点可知，其前驱肯定是左子树中的最后一个节点. 左子树的最后一个节点就是其最右的节点
     *           predecessor = node.left.right.right...
     *           终止条件: right = null;
     *
     * 情况2:    没有左子树 node.left == null && node.parent != null;
     * 查找过程:  没有左子树,那就不能向左子树寻找,由于是寻找前驱节点,所以肯定也不能寻找右子树.所以只能向上寻找即查找父节点。
     *           如果node在父节点的左子树中,那是父节点是该node后驱节点.只有当node在的父节点右子树中时,才能说明找到了前驱。否则没有前驱。
     *           predecessor = node.parent.parent...
     *           终止条件 node在parent的右子树中 或 parent == null(说明没有前驱)
     * 情况3:     node.left == null && node.parent == null;
     * 查找过程:  属于情况2中的一种中间情况 即没有前驱节点
     *
     * 查找node节点的前驱节点
     * @param node 目标节点
     * @return node的前驱节点
     */
    protected Node<E> predecessor(Node<E> node) {
        if (node == null) return null;
        // 前驱节点在左子树当中（left.right.right.right....）
        Node<E> p = node.left;
        if (p != null){
            while (p.right != null){
                p = p.right;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null &&  node.parent.left == node){
            node = node.parent;
        }

        // node.parent == null
        // node == node.parent.right
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
     * 查找后继节点
     * @param node 目标节点
     * @return node的后继节点
     */
    protected Node<E> successor(Node<E> node) {
        if (node == null) return null;
        // 前驱节点在左子树当中（right.left.left.left....）
        Node<E> p = node.right;
        if (p != null){
            while (p.left != null){
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node.parent.right == node){
            node = node.parent;
        }
        // node.parent == null
        // node == node.parent.right
        return node.parent;
    }

    /**
     * 前序遍历
     * @param visitor 遍历器
     */
    public void preorderTravel(Visitor<E> visitor){
        if (visitor == null) return;
//        preorder_recursive(root ,visitor);
        preorder_iterate(root,visitor);
    }

    /**
     * 中序遍历
     * @param visitor 遍历器
     */
    public void inorderTravel(Visitor<E> visitor){
        if (visitor == null) return;
//        inorder_recursive(root,visitor);
        inorder_iterate(root,visitor);
    }

    /**
     * 后序遍历
     * @param visitor 遍历器
     */
    public void postOrderTravel(Visitor<E> visitor){
        if (visitor == null) return;
//        postOrder_recursive(root,visitor);
        postOrder_iterate(root,visitor);
    }

    /**
     * 层次遍历
     * @param visitor 遍历器
     */
    public void levelOrderTravel( Visitor<E> visitor){
        if (visitor == null) return;
        levelOrder(root,visitor);
    }


    /**
     * 前序遍历递归版
     * @param root 根节点
     * @param visitor 遍历器
     */
    private void preorder_recursive(Node<E> root, Visitor<E> visitor){
        if (root == null || visitor.stop) return;
        visitor.stop = visitor.visit(root.val);
        preorder_recursive(root.left, visitor);
        preorder_recursive(root.right, visitor);
    }

    /**
     * 前序遍历迭代版
     * @param root 根节点
     */
    private void preorder_iterate(Node<E> root, Visitor<E> visitor){
        if (root == null || visitor == null) return;
        // 一统江湖的写法
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node<E> top = stack.pop();
            if (top != null){
                if (top.right != null){
                    stack.push(top.right);
                }
                if (top.left != null){
                    stack.push(top.left);
                }
                stack.push(top);
                stack.push(null);
            } else {
                if (visitor.visit(stack.pop().val)) return;
            }
        }

    }


    /**
     * 中序遍历递归版
     * @param root 根节点
     * @param visitor 遍历器
     */
    private void inorder_recursive(Node<E> root,Visitor<E> visitor){
        if (root == null || visitor.stop) return;
        inorder_recursive(root.left,visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(root.val);
        inorder_recursive(root.right,visitor);
    }

    /**
     * 中序遍历迭代版
     * @param root 根节点
     */
    private void inorder_iterate(Node<E> root, Visitor<E> visitor){
        if (root == null || visitor == null) return;
        // 一统江湖的写法
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node<E> top = stack.pop();
            if (top != null){
                if (top.right != null){
                    stack.push(top.right);
                }
                stack.push(top);
                stack.push(null);

                if (top.left != null){
                    stack.push(top.left);
                }

            } else {
                if (visitor.visit(stack.pop().val)) return;
            }
        }
    }

    /**
     * 后序遍历递归版
     * @param root 根节点
     * @param visitor 遍历器
     */
    private void postOrder_recursive(Node<E> root, Visitor<E> visitor){
        if (root == null || visitor.stop) return;
        postOrder_recursive(root.left, visitor);
        postOrder_recursive(root.right, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(root.val);
    }

    /**
     * 后序遍历迭代版
     * @param root 根节点
     * @param visitor 遍历器
     */
    private void postOrder_iterate(Node<E> root, Visitor<E> visitor){
        if (root == null || visitor == null) return;
        // 一统江湖的写法
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node<E> top = stack.pop();
            if (top != null){
                stack.push(top);
                stack.push(null);
                if (top.right != null){
                    stack.push(top.right);
                }
                if (top.left != null){
                    stack.push(top.left);
                }
            } else {
                if (visitor.visit(stack.pop().val)) return;
            }
        }
    }

    /**
     * 层次遍历迭代版
     * @param root 根节点
     * @param visitor 遍历器
     */
    public void levelOrder(Node<E> root, Visitor<E> visitor){
        if (root == null || visitor == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node<E> node = queue.poll();
            if (visitor.visit(node.val)) return;
            if (node.left != null){
                queue.add(node.left);
            }
            if (node.right != null){
                queue.add(node.right);
            }
        }
    }

    /**
     * 获取二叉数的高度
     * @return 二叉树的高度
     */
    public int height(){
        return getNodeHeight_recursive(root);
//        return getNodeHeight_iterate(root);
    }

    /**
     * 递归获取节点的高度
     * @param root 节点
     * @return 节点高度
     */
    public int getNodeHeight_recursive(Node<E> root){
        if (root == null) return 0;
        return Math.max(getNodeHeight_recursive(root.left) + 1, getNodeHeight_recursive(root.right) + 1);
    }

    /**
     * 迭代获取节点高度(层次遍历)
     * @param root 节点
     * @return 节点高度
     */
    public int getNodeHeight_iterate(Node<E> root){
        if (root == null) return 0;
        Queue<Node<E>> queue = new LinkedList<>();
        int height = 0;
        queue.add(root);
        while (!queue.isEmpty()){
            int count = queue.size();
            height++;
            while (count > 0){
                Node<E> node = queue.poll();
                count--;
                if (node.left != null){
                    queue.add(node.left);
                }
                if (node.right != null){
                    queue.add(node.right);
                }
            }
        }
        return height;
    }

    /**
     * 判断是否为完全二叉树
     * @return 是否为完全二叉树
     *             7
     *         3      11
     *       1   4   9
     *
     *    判断完全二叉树的条件:
     *    层次遍历节点
     *    1.node.left != null &&  node.right != null 继续判断子节点
     *    2.node.left != null && node.right == null 则后序节点必须叶子节点
     *    3.node.left == null && node.right == null 即node是叶子节点，后序节点必须是叶子节点
     *    其他情况都是非完全二叉树
     */
    public boolean isCompletedBinaryTree(){
        if (root == null) return true;
        boolean nextMustBeLeafNode = false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node<E> node = queue.poll();
            if (nextMustBeLeafNode){
                if (!node.isLeaf()) return false;
            }
            if (node.left != null){
                queue.add(node.left);
                if (node.right != null){
                    queue.add(node.right);
                } else {
                    nextMustBeLeafNode = true;
                }
            } else {
                if (node.right == null){
                    nextMustBeLeafNode = true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public void elementEmptyCheck(E element){
        if (element == null){
            throw new IllegalArgumentException("element must not be null");
        }
    }

    /// BinaryTreeInfo 接口实现
    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        return node;
          // 测试BinarySearchTree时这么写
//        BinaryTree.Node<E> newNode = (BinaryTree.Node<E>)node;
//        String parentString = "null";
//        if (newNode.parent != null){
//            parentString = newNode.parent.val.toString();
//        }
//        return ((Node<E>)node).val + "(p_" + parentString + ")";
    }
}
