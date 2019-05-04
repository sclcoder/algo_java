package com.leetcode.binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/** https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
 *给定一个二叉树，返回它的 前序 遍历。
 *
 *  示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,2,3]
 *
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 * 重点是迭代算法!!!!!!!!!!
 */

public class _144_二叉树的前序遍历 {
    // 递归
    class Solution1 {
        List<Integer> list = new ArrayList<>();
        public List<Integer> preorderTraversal(TreeNode root) {
            if (root == null) {
                return list;
            }
            list.add(root.val);
            preorderTraversal(root.left);
            preorderTraversal(root.right);
            return list;
        }
    }
    // 非递归

    /**
     * 利用栈实现类似递归的函数调用栈
     */
    class Solution2 {
        List<Integer> list = new ArrayList<>();
        public List<Integer> preorderTraversal(TreeNode root) {
            if (root == null) return list;
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()){
                TreeNode node = stack.pop();
                list.add(node.val);
                if (node.right != null){
                    stack.push(node.right);
                }
                if (node.left != null){
                    stack.push(node.left);
                }
            }
            return list;
        }
    }
}
