package com.leetcode.binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/** https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 * 给定一个二叉树，返回它的中序 遍历。
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
 * 输出: [1,3,2]
 *
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 */
public class _94_二叉树的中序遍历 {

    /**
     * 递归解法
     */
    class Solution {
        List<Integer> List = new ArrayList<>();
        public List<Integer> inorderTraversal(TreeNode root) {
            if (root == null) return List;
            inorderTraversal(root.left);// 找到左子树最左节点
            List.add(root.val);
            inorderTraversal(root.right);// 找到左子树最右节点
            return List;
        }
    }

    /**
     * 非递归解法
     * 好难弄 高票解法
     */
    class Solution2 {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            if (root == null) return list;
            TreeNode current = root;
            /**
             * 结束条件注意 current != null 因为在执行过程栈可能为空 如左子树遍历结束后的情况
             */
            while (!stack.isEmpty() || current != null){
                if (current != null){
                    stack.push(current);    // 节点入栈
                    current = current.left; // 节点更新为左节点
                } else {
                    current = stack.pop();
                    list.add(current.val);
                    current = current.right; // 节点更新为右节点
                }
            }
            return list;
        }
    }
}
