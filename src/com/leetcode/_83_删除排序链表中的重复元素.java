package com.leetcode;

/** https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
 * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
 * 示例 1:
 * 输入: 1->1->2
 * 输出: 1->2
 */


public class _83_删除排序链表中的重复元素 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 我的写法竟然和官方一模一样 哈哈哈
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode tempNode = head;
        while ( tempNode != null && tempNode.next != null){
            if (tempNode.val == tempNode.next.val){
                // 注意: 出现重复的直接删除该节点,tempNode指针不能移动。以为可能重复多次
                tempNode.next = tempNode.next.next;
            } else {
                // 不重复就将指针指向下一个节点
                tempNode = tempNode.next;
            }
        }
        return head;
    }
}
