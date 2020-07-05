package com.leetcode.binaryTree;

/**
 * 题目描述:
 *
 * 给定一个二叉树，找到其中最大的二叉搜索树（BST）子树，其中最大指的是子树节点数最多的。
 *
 * 注意:
 *
 * 子树必须包含其所有后代。
 * 示例:
 *
 * 示例:
 *
 * 输入: [10,5,15,1,8,null,7]
 *
 *    10
 *    / \
 *   5  15
 *  / \   \
 * 1   8   7
 *
 * 输出: 3
 * 解释: 高亮部分为最大的 BST 子树。
 *      返回值 3 在这个样例中为子树大小。
 *
 * 进阶:
 *
 * 你能想出用 O(n) 的时间复杂度解决这个问题吗？
 */
public class _333_最大BST子树 {

    /**
     * 自下而上: 类似动态规划，从下而上推导
     * @param root
     * @return
     *
     *  先不写了。。。。。。。
     *  如果 左边是BST、右边是BST:
     */

    public int largestBSTSubtree_dp(TreeNode root){

        return 0;
    }

    /**
     * 自上而下 : 有很多冗余的计算
     * 获取二叉树的最大BST的元素数量
     * @return 最大BST的元素数量
     */
    public int largestBSTSubtree_recursive(TreeNode root){
        /**
         * 判断每个节点是否是BST. 并计算BST中节点数量
         */
        if (root == null) return 0;
        if (isValidBST(root)) return getCount(root);
        int lCount = 0;
        int rCount = 0;
        if (isValidBST(root.left)) {
            lCount = getCount(root.left);
        }
        if (isValidBST(root.right)){
            rCount = getCount(root.right);
        }

        return Math.max(lCount,rCount);
    }

    /**
     * 获取二叉树节点数量
     * @param node
     * @return
     */
    public int getCount(TreeNode node){
        if (node == null) return 0;
        return 1 + getCount(node.left) + getCount(node.right);
    }

    /**
     * 判断二叉树是否为BST
     */
    long pre = Long.MIN_VALUE;
    public boolean isValidBST(TreeNode root){
        if (root == null) return true;
        if (!isValidBST(root.left)) return false;
        if (root.val <= pre) return false;
        pre = root.val;
        return isValidBST(root.right);
    }
}

