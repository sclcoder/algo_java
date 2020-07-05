package com.leetcode.binaryTree;


import java.util.Stack;

/**
 * 230. 二叉搜索树中第K小的元素
 *
 * 给定一个二叉搜索树，编写一个函数 kthSmallest 来查找其中第 k 个最小的元素。
 *
 * 说明：
 * 你可以假设 k 总是有效的，1 ≤ k ≤ 二叉搜索树元素个数。
 *
 * 示例 1:
 *
 * 输入: root = [3,1,4,null,2], k = 1
 *    3
 *   / \
 *  1   4
 *   \
 *    2
 * 输出: 1
 *
 * 示例 2:
 *
 * 输入: root = [5,3,6,2,4,null,null,1], k = 3
 *        5
 *       / \
 *      3   6
 *     / \
 *    2   4
 *   /
 *  1
 * 输出: 3
 *
 * 进阶：
 * 如果二叉搜索树经常被修改（插入/删除操作）并且你需要频繁地查找第 k 小的值，你将如何优化 kthSmallest 函数？
 */
public class _230_搜索二叉树中第K小元素 {

    /**
     *  Morris遍历:
     *  假设当前遍历的节点为N
     *  1. 如果 N.left != null
     *     找到N的前序节点P
     *     a.如果 P.right == null , P.right = N
     *     N = N.left;
     *     回到步骤1
     *
     *     b.如果 P.right == N
     *       P.right = null;
     *       输出N
     *       N = N.right
     *
     *
     *  2. 如果 N.left == null;
     *     输出N, N = N.right
     *     回到步骤1
     *
     *
     *
     */

    public int kthSmallest_morris(TreeNode root, int k) {
        TreeNode node = root;
        while (node != null){
            if (node.left != null){
                // 查找node的前驱节点
                TreeNode pNode = node.left;
                while (pNode.right != null && pNode.right != node){
                    pNode = pNode.right;
                }
                if (pNode.right == null){
                    pNode.right = node;
                    node = node.left; // 继续循环
                }
                if (pNode.right == node){
                    pNode.right = null;
                    // 输出node
                    k--;
                    if (k == 0) {
                        return node.val;
                    }
                    node = node.right; // 继续循环
                }

            } else { // node.left == null
                // 输出node
                k--;
                if (k == 0) {
                    return node.val;
                }
                node = node.right; // 继续循环
            }
        }
        return -1;
    }









    int count = 0; // 计数
    int kth = 0;
    public int kthSmallest_recurse(TreeNode root, int k) {
        inorder(root, k);
        return kth;
    }

    public void inorder(TreeNode root , int k){
        if (root == null) return;
        inorder(root.left, k);
        count++;
        if (count == k){
            kth = root.val;
            return;
        }
        inorder(root.right, k);
        return;
    }


    /**
     * 很简单的思路: 利用中序遍历得到递增数据，查找第K个即可
     */
    public int kthSmallest_stack(TreeNode root, int k) {
        int kth = 0;
        int count = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode top = root;
        stack.push(top);
        while (!stack.isEmpty()){
            top = stack.pop();
            if (top != null){
                if (top.right != null) stack.push(top.right);
                stack.push(top);
                stack.push(null);
                if (top.left != null) stack.push(top.left);
            } else {
                // 回溯阶段
                top = stack.pop();
                count++;
                if (count == k){
                    kth = top.val;
                    break;
                }
            }
        }
        return kth;
    }


    public int kthSmallest_stack2(TreeNode root, int k) {
        int kth = 0;
        int count = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode top = root;
        while (!stack.isEmpty() || top != null){
            while (top != null){
                stack.push(top);
                top = top.left;
            }
            top = stack.pop(); // 回溯阶段
            count++;
            if (count == k) kth = top.val;
            top = top.right;
        }
        return kth;
    }
}

