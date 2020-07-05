package com.leetcode.binaryTree;

/**
 * 108. 将有序数组转换为二叉搜索树
 * <p>
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 * <p>
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 * <p>
 * 示例:
 * <p>
 * 给定有序数组: [-10,-3,0,5,9],
 * <p>
 * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
 * <p>
 * 0
 * / \
 * -3   9
 * /   /
 * -10  5
 * <p>
 * 思路: 因为BST的中序遍历是升序,若是找到给定有序数组的中间数据，作为根节点，其左右两侧的数据分别为左子树和右子树。
 * 这样构造的BST数的高度符合题目要求,然后将左子树数据和右子树数据递归进行处理
 */
public class _108_将有序数组转为搜索二叉树 {
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(nums, 0, nums.length -1);
    }

    public TreeNode helper(int[] nums, int left, int right){
        // 1 2 3 4 5    mid 3 1
        if (left > right) return null;  // 注意边界条件的判断
        int mid = (left + right) >> 1;

        TreeNode root = new TreeNode(nums[mid]);
        root.left = helper(nums, left, mid-1);
        root.right = helper(nums, mid + 1, right);
        return root;
    }

}
