package com.leetcode.binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
 * 给定一个二叉树，返回它的 前序 遍历。
 * <p>
 * 示例:
 * <p>
 * 输入: [1,null,2,3]
 *      1
 *        \
 *          2
 *         /
 *       3
 * <p>
 * 输出: [1,2,3]
 * <p>
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 * 重点是迭代算法!!!!!!!!!!
 */

public class _144_二叉树的前序遍历 {

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode cur = stack.pop();
            res.add(cur.val);
            if (cur.right != null){
                stack.push(cur.right);
            }
            if (cur.left != null){
                stack.push(cur.left);
            }
        }
        return res;
    }
    public List<Integer> preorderTraversal_1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null){
            if (cur != null){
                res.add(cur.val);
                stack.add(cur); // 入栈是为了以后遍历cur.right
                cur = cur.left;
            } else {
                cur = stack.pop();
                cur = cur.right;
            }
        }
        return res;
    }

    public List<Integer> preorderTraversal_recursive(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(root, res);
        return res;
    }

    public void helper(TreeNode node, List<Integer> list) {
        if (node != null) {
            list.add(node.val);
            helper(node.left, list);
            helper(node.right, list);
        }
        return;
    }
}
