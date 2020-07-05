package com.leetcode.binaryTree;
/**
 * 105. 从前序与中序遍历序列构造二叉树
 *
 * 根据一棵树的前序遍历与中序遍历构造二叉树。
 *
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * 例如，给出
 *
 * 前序遍历 preorder = [3,9,20,15,7]
 * 中序遍历 inorder = [9,3,15,20,7]
 *
 * 返回如下的二叉树：
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 */
public class _105_从前序和中序遍历中构造二叉树 {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // [p_start, p_end) 前闭后开区间
        return buildTreeHelper(preorder,0,preorder.length,inorder,0,inorder.length);
    }
    public TreeNode buildTreeHelper(int[] preorder, int p_start, int p_end, int[] inorder, int i_start, int i_end){
        if (p_start == p_end) return null;
        int root_val = preorder[p_start];
        TreeNode root = new TreeNode(root_val);
        int i_root_index = 0;
        for (int i = i_start; i < i_end; i++) {
            if (root_val == inorder[i]){
                i_root_index = i;
                break;
            }
        }
        int len = i_root_index - i_start;
    //         * 前序遍历 preorder = [3,9,20,15,7]
    //         * 中序遍历 inorder = [9,3,15,20,7]
        root.left = buildTreeHelper(
                preorder,p_start + 1,p_start + 1 + len,
                inorder,i_start, i_root_index);

        root.right = buildTreeHelper(
                preorder, p_start + 1 + len, p_end,
                inorder, i_root_index + 1,i_end
                );
        return root;
    }

}

