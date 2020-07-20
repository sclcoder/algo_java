package com.leetcode.binaryTree;

import com.ds_algo.d_stack.Stack;

/**
 * 98. 验证二叉搜索树
 *
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 *
 * 假设一个二叉搜索树具有如下特征：
 *
 *     节点的左子树只包含小于当前节点的数。
 *     节点的右子树只包含大于当前节点的数。
 *     所有左子树和右子树自身必须也是二叉搜索树。
 *
 * 示例 1:
 *
 * 输入:
 *     2
 *    / \
 *   1   3
 * 输出: true
 *
 * 示例 2:
 *
 * 输入:
 *     5
 *    / \
 *   1   4
 *      / \
 *     3   6
 * 输出: false
 * 解释: 输入为: [5,1,4,null,null,3,6]。
 *      根节点的值为 5 ，但是其右子节点值为 4 。
 */
public class _98_验证二叉搜索树 {

    long pre = Long.MIN_VALUE;
    public boolean isValidBST_recurse(TreeNode root) {
        /**
         *  递归版本中序遍历
         *  https://leetcode-cn.com/problems/validate-binary-search-tree/solution/zhong-xu-bian-li-qing-song-na-xia-bi-xu-miao-dong-/
         *  大佬的这个精髓全在 pre这个变量之中~
         *  在判断left时,pre会更新为root
         */
        if (root == null) return true;
        // 访问左子树
        if (!isValidBST_recurse(root.left)) return false;
        // 访问当前节点：如果当前节点小于等于中序遍历的前一个节点，说明不满足BST，返回 false；否则继续遍历。
        if (root.val <= pre) return false;
        pre = root.val;
        // 访问右子树
        return isValidBST_recurse(root.right);
    }

    public boolean isValidBST_stack(TreeNode root) {
        if (root == null) return true;
        /**
         * 迭代版本--中序遍历,判断节点值是否为递增
         */
        Stack<TreeNode> stack = new Stack<>();
        TreeNode top = root;
        stack.push(top);
        TreeNode preNode = null;
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
                if (preNode != null && preNode.val >= top.val) return false;
                preNode = top;
            }
        }
        return true;
    }
}
