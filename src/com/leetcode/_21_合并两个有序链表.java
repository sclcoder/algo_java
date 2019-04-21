package com.leetcode;

/** https://leetcode-cn.com/problems/merge-two-sorted-lists/
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * 示例：
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 */
public class _21_合并两个有序链表 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 创建一个新的链表,将两个链表的比较结果按顺序存到新链表中
     * 思路: 比较两个链表的大小,依次进入新链表中
     * 第一次没写好原因:思路是对的,但是想多了,复制了l1,l2中的值创建新链表
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode l = new ListNode(0);
        ListNode c = l;
        while (l1 != null && l2 != null){
            if (l1.val <= l2.val){
                c.next = l1;
                c = c.next;
                l1 = l1.next;
            } else {
                c.next = l2;
                c = c.next;
                l2 = l2.next;
            }

        }
        return l;
    }
}
