package com.leetcode.binaryTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 101. 对称二叉树
 *
 * 给定一个二叉树，检查它是否是镜像对称的。
 *
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 *
 *                    1
 *                 /       \
 *               2          2
 *              /  \       /  \
 *             3   4      4    3
 *            / \ / \    / \  / \
 *           5  6 6 5   5  6  6 5
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 *
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 *
 * 进阶：
 * 你可以运用递归和迭代两种方法解决这个问题吗？
 */
public class _101_对称二叉树 {

    /**
     * 首先我们引入一个队列，这是把递归程序改写成迭代程序的常用方法。初始化时我们把根节点入队两次。
     * 每次提取两个结点并比较它们的值（队列中每两个连续的结点应该是相等的，而且它们的子树互为镜像），然后将两个结点的左右子结点按相反的顺序插入队列中。
     * 当队列为空时，或者我们检测到树不对称（即从队列中取出两个不相等的连续结点）时，该算法结束。
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/symmetric-tree/solution/dui-cheng-er-cha-shu-by-leetcode-solution/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return check(root.left,root.right);
    }

    public boolean check(TreeNode m, TreeNode n){
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(m);
        q.offer(n);
        while (!q.isEmpty()){
            m = q.poll();
            n = q.poll();
            if (m == null && n == null) continue;
            if (m == null || n == null) return false;
            if (m.val != n.val) return false;
            q.offer(m.right);
            q.offer(n.left);

            q.offer(m.left);
            q.offer(n.right);
        }
        return true;
    }

    /**
     * 看似简单的题目我竟然写错了！！！！
     * 1.理解题意有误
     * 2.理解题意了,竟然没写出来
     */
    public boolean isSymmetric_recurse(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }
    public boolean isMirror(TreeNode tree1, TreeNode tree2){
        if (tree1 == null && tree2 == null) return true;
        if (tree1 == null || tree2 == null) return false;
        if (tree1.val != tree2.val) return false;
        return isMirror(tree1.right,tree2.left) && isMirror(tree2.right,tree1.left);
    }
}
