package com.leetcode.binaryTree;

/**
 * 106. 从中序与后序遍历序列构造二叉树
 *
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 *
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * 例如，给出
 *
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 *
 * 返回如下的二叉树：
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 */
public class _106_从中序和后序遍历中构造一个二叉树 {

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        /// 区间前闭后开[i_start, i_end)
        return buildTreeHelper(inorder,0,inorder.length,postorder,0,postorder.length);
    }
    public TreeNode buildTreeHelper(int[] inorder, int i_start, int i_end, int[] postorder, int p_start, int p_end) {
        if (p_start == p_end) return null;
        int root_val = postorder[p_end-1];
        TreeNode root = new TreeNode(root_val);
        int i_root_index = 0;
        for (int i = i_start; i < i_end ; i++) {
            if (root_val == inorder[i]){
                i_root_index = i;
                break;
            }
        }
        int len = i_root_index - i_start;
        //   中序遍历 inorder = [9,3,15,20,7]
        //   后序遍历 postorder = [9,15,7,20,3]
        /**
         * 思路。通过后序遍历找到root。通过root,可以在中序遍历中确定左右子树的大小。通过左右的大小，可以在后序遍历中找到左右子树的范围
         * 这样在后序、中序的数组中都确定了左右子树的范围。递归就出现了嘛
         */
        root.left = buildTreeHelper(
                inorder,i_start,i_root_index,
                postorder, p_start,p_start + len
        );

        root.right = buildTreeHelper(
                inorder,i_root_index + 1,i_end,
                postorder, p_start + len,p_end-1
        );

        return root;
    }
}
