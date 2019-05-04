package com.leetcode.binaryTree;

/** https://leetcode-cn.com/problems/invert-binary-tree/
 * 翻转一棵二叉树。
 *
 * 示例：
 *
 * 输入：
 *
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 *
 * 输出：
 *
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 */
import java.util.LinkedList;
import java.util.Queue;

/**
 * 该题目的本质就是二叉树的遍历
 * 只要获取到节点交换左右子节点即可
 */
public class _226_翻转二叉树 {
    class Solution {
        // 先序遍历--从顶向下交换
        public TreeNode invertTree(TreeNode root) {
            if (root == null) return null;
            // 保存右子树
            /**            两种方式其实是一样的
             *              TreeNode rightTree = root.right;
             *             // 交换左右子树的位置
             *             root.right = invertTree(root.left);
             *             root.left = invertTree(rightTree);
             */
            TreeNode tem = root.left;
            root.left = root.right;
            root.right = tem;
            invertTree(root.left);
            invertTree(root.right);
            return root;
        }
    }

    class Solution1 {
        public TreeNode invertTree(TreeNode root) {
            // 中序遍历-- 从下向上交换
            if (root == null) return null;
            /**
             * 仔细考虑递归过程 以及函数堆栈的调用
             */
            // 递归找到左节点
            invertTree(root.left);
            // 交换左右子节点
            TreeNode rightNode= root.right;
            root.right = root.left;
            root.left = rightNode;
            // 递归找到右节点 继续交换 : 因为此时左右节点已经交换了,所以此时的右节点为root.left
            invertTree(root.left);
            return root;
        }
    }

    class Solution2 {
        public TreeNode invertTree(TreeNode root) {
            // 后序遍历-- 从下向上交换
            if (root == null) return null;
            TreeNode leftNode = invertTree(root.left);
            TreeNode rightNode = invertTree(root.right);
            root.right = leftNode;
            root.left = rightNode;
            return root;
        }
    }
    class Solution3 {
        public TreeNode invertTree(TreeNode root) {
            // 层次遍历--直接左右交换即可
            if (root == null) return null;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()){
                TreeNode node = queue.poll();
                TreeNode rightTree = node.right;
                node.right = node.left;
                node.left = rightTree;
                if (node.left != null){
                    queue.offer(node.left);
                }
                if (node.right != null){
                    queue.offer(node.right);
                }
            }
            return root;
        }
    }
}
