package com.leetcode.linkedList;

/**
 * 面试题 02.02. 返回倒数第 k 个节点
 *
 * 实现一种算法，找出单向链表中倒数第 k 个节点。返回该节点的值。
 *
 * 注意：本题相对原题稍作改动
 *
 * 示例：
 *
 * 输入： 1->2->3->4->5 和 k = 2
 * 输出： 4
 *
 * 说明：
 *
 * 给定的 k 保证是有效的。
 */
public class _面试题0202_返回倒数第k个节点 {

    int index = 0;
    int target = -1;
    public int kthToLast(ListNode head, int k) {
        if (head == null) return -1;
        kthToLast(head.next , k);
        index++;
        if (index == k) target = head.val;
        return target;
    }

    public int kthToLast_fs(ListNode head, int k) {

        ListNode fast = head;
        ListNode slow = head;
        /**
         *   1->2->3->4->5 和 k = 2
         *
         */

        while (k > 0){
            fast = fast.next;
            k--;
        }
        while (fast != null){
            fast = fast.next;
            slow = slow.next;
        }

        return slow.val;
    }
}
