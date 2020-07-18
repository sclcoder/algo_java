package com.leetcode.linkedList;

/**
 * 86. 分隔链表
 *
 * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
 *
 * 你应当保留两个分区中每个节点的初始相对位置。
 *
 * 示例:
 *
 * 输入: head = 1->4->3->2->5->2, x = 3
 * 输出: 1->2->2->4->3->5
 */
public class _86分割链表 {

    /**
     * 构建两条链表， 最后合并
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {

        ListNode lhead = new ListNode();
        ListNode ltail = lhead;

        ListNode rhead = new ListNode();
        ListNode rtail = rhead;

        ListNode cur = head;
        while (cur != null){
            if (cur.val < x){
                ltail.next = cur;
                ltail = cur;
            } else {
                rtail.next = cur;
                rtail = cur;
            }
            cur = cur.next;
        }
        rtail.next = null;
        ltail.next = rhead;
        return lhead.next;
    }
}
