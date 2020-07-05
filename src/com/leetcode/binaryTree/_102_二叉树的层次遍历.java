package com.leetcode.binaryTree;


import java.util.*;

/**
 * 102. 二叉树的层序遍历
 *
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 *
 *
 *
 * 示例：
 * 二叉树：[3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 返回其层次遍历结果：
 *
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 */
public class _102_二叉树的层次遍历 {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            List<Integer> list = new ArrayList<>();
            int count = queue.size();
            while (count > 0){
                TreeNode top = queue.poll();
                list.add(top.val);
                count--;

                if (top.left!=null){
                    queue.add(top.left);
                }
                if (top.right!=null){
                    queue.add(top.right);
                }
            }
            res.add(list);
        }
        return res;
    }
}
