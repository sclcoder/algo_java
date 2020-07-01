package com.leetcode.binaryTree;

import com.algo.s04_Stack.Stack;

/**
 * 99. 恢复二叉搜索树
 *
 * 二叉搜索树中的两个节点被错误地交换。
 *
 * 请在不改变其结构的情况下，恢复这棵树。
 *
 * 示例 1:
 *
 * 输入: [1,3,null,null,2]
 *
 *    1
 *   /
 *  3
 *   \
 *    2
 *
 * 输出: [3,1,null,null,2]
 *
 *    3
 *   /
 *  1
 *   \
 *    2
 *
 * 示例 2:
 *
 * 输入: [3,1,4,null,null,2]
 *
 *   3
 *  / \
 * 1   4
 *    /
 *   2
 *
 * 输出: [2,1,4,null,null,3]
 *
 *   2
 *  / \
 * 1   4
 *    /
 *   3
 *
 * 进阶:
 *
 *     使用 O(n) 空间复杂度的解法很容易实现。
 *     你能想出一个只使用常数空间的解决方案吗？ // morris遍历
 *
 *     利用中序遍历解决该问题:
 *     因为中序遍历是遍历结果应该是递增的。如果发现有逆序对，说明遇到了错误节点。
 *     错误节点的查找:
 *                 28
 *          44           37
 *       11    22          18
 *        17             42  62
 *      中序遍历结果:   11 17 44 22 28 37 42 18 62
 *      逆序对为 44 22 、 42 18
 *      错误的节点1: 第一个逆序对的较大者
 *      错误的节点1: 第二个逆序对的较小者
 */
public class _99_恢复二叉搜索树 {

    /**
     *   morris遍历步骤
     *   假设挡墙遍历的节点是N
     *   1. 如果N.left != null ,找到N的前驱节点P(中序遍历中的前一个节点)
     *      a.如果 P.right == null.
     *             P.right = N
     *             N = N.left
     *      回到步骤1
     *      b.如果 P.right == N
     *            p.right == null , 输出N
     *            N = N.right
     *       回到步骤1
     *   2. 如果 N.left == null, 输出N
     *           N = N.right
     *      回到步骤1
     *
     *
     */
    public void recoverTree_morris(TreeNode root) {
        TreeNode node = root;
        while (node != null){
            if (node.left != null){
                // 寻找前驱节点
                TreeNode pred = node.left;
                while (pred.right != null && pred.right != node){
                    pred = pred.right;
                }
                if (pred.right == null){
                    pred.right = node;
                    node = node.left;
                } else { // pred == node
                    // 输出node .....
                    find(node);
                    pred.right = null;
                    node = node.right;
                }
            } else { // node.left == null
                // 输出node
                find(node);
                node = node.right;
            }
        }
    }

    /**
     * 第一个错误节点
     */
    private TreeNode firstNode;
    /**
     * 第二个错误节点
     */
    private TreeNode secondNode;
    /**
     * 记录前一个节点
     */
    private TreeNode preNode;
    /**
     * 查找错误节点并记录为 firstNode、secondNode
     * @param node 中序遍历时遍历到的节点
     */
    private void find(TreeNode node){
        if (preNode != null){
            if (preNode.val > node.val){
                if (firstNode == null){
                    firstNode = preNode;
                    secondNode = node; // 两个相邻元素交换的情况（只有一个逆序对）
                } else {
                    secondNode = node; // 有两个逆序对
                }
            }
        }
        preNode = node;
    }


    public void recoverTree(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode fNode = null;
        TreeNode sNode = null;
        TreeNode preNode = null;
        TreeNode top = root;
        stack.push(top);
        while (!stack.isEmpty()){
            top = stack.pop();
            if (top != null){
                if (top.right != null) stack.push(top.right);
                stack.push(top);
                stack.push(null);
                if (top.left != null) stack.push(top.left);
            } else {
                top = stack.pop();
                // 回溯阶段，输出中序遍历的结果  1 3 2 4
                // 1 3 2
                if (preNode != null){
                    if (preNode.val > top.val){
                        if (fNode == null){
                            fNode = preNode;
                            sNode = top; // 两个相邻元素交换的情况（只有一个逆序对）
                        } else {
                            sNode = top; // 有两个逆序对
                        }
                    }
                }
                preNode = top;
            }
        }
        // 交换
        int tem = fNode.val;
        fNode.val = sNode.val;
        sNode.val = tem;
    }
}
