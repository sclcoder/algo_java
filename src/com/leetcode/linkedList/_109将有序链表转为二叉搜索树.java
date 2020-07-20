package com.leetcode.linkedList;

import com.leetcode.binaryTree.TreeNode;

/**
 * 109. 有序链表转换二叉搜索树
 *
 * 给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。
 *
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 *
 * 示例:
 *
 * 给定的有序链表： [-10, -3, 0, 5, 9],
 *
 * 一个可能的答案是：[0, -3, 9, -10, null, 5], 它可以表示下面这个高度平衡二叉搜索树：
 *
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 */
public class _109将有序链表转为二叉搜索树 {
    /**
     * 思路: 找到链表的中间节点，构建二叉树节点，然后递归处理左右子节点
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        ListNode mid = mid(head);
        TreeNode root = new TreeNode(mid.val);
        /**
         * 注意点: mid == head 是左侧退出递归的条件
         * 当只有有个节点时 mid == head。在查找中间节点的函数中总是返回head,这里会造成死循环
         */
        if (mid == head) return root;

        root.left = sortedListToBST(head);
        root.right = sortedListToBST(mid.next);
        return root;
    }

    /**
     * 获取链表的中间节点
     * @param head
     * @return
     */
    public ListNode mid(ListNode head){
        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = null;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            prev = slow;
            slow = slow.next;
        }
        /**
         * 这个思路也是自己想出来的奥， 和官方一个样子
         */
        if (prev != null){
            prev.next = null; /// 将左右两部分链表切割开
        }

        return slow;
    }
}
