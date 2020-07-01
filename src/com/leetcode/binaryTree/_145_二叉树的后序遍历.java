package com.leetcode.binaryTree;


import java.util.*;

/**
 * 145. 二叉树的后序遍历
 *
 * 给定一个二叉树，返回它的 后序 遍历。
 *
 * 示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [3,2,1]
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 */
public class _145_二叉树的后序遍历 {
    public List<Integer> postorderTraversal_1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode cur = stack.peek();
            stack.pop();
            if (cur != null){
                stack.push(cur);
                stack.push(null);
                if (cur.right != null) {
                    stack.push(cur.right);
                }
                if (cur.left != null) {
                    stack.push(cur.left);
                }
            } else {
                res.add(stack.peek().val);
                stack.pop();
            }

        }
        return res;
    }



    public List<Integer> postorderTraversal(TreeNode root) {
        /** 非递归
         *  思路：利用前序遍历的理思路改造一下
         *  前序  父 左 右
         *  这里  父 右 左 然后倒序输出即可
         */
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        stack.push(cur);
        while (!stack.isEmpty()){
            cur = stack.pop();
            res.add(cur.val);
            // 这里先将左子树入栈、再将右子树入栈 和前序遍历相反
            if (cur.left != null){
                stack.push(cur.left);
            }
            if (cur.right != null){
                stack.push(cur.right);
            }
        }
        Collections.reverse(res);
        return res;
    }

    public List<Integer> postorderTraversal_recursive(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(root, res);
        return res;
    }
    public void helper(TreeNode node, List<Integer> list){
        if (node == null) return;
        helper(node.left, list);
        helper(node.right, list);
        list.add(node.val);
        return;
    }
}
