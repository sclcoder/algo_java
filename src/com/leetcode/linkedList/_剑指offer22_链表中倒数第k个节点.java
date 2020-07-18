package com.leetcode.linkedList;

public class _剑指offer22_链表中倒数第k个节点 {
    /**
     *  思路一： 最容易想到就是翻转链表。然后找翻转链表的第k个node.然后再将链表翻转回来
     *
     *  思路二:  记住即可， 倒数第几个数，要想到双指针pf 、 ps 。 两个指针都指向一开始都指向头结点，让ps向前走k次。然后ps、pf
     *  一起向后走，直到ps指向null, 此时pf正好指向链表的倒数第k个节点
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode pf = head;
        ListNode ps = head;
        ///  1 2 3 4 5     k = 2   4
        for (int i = 0; i < k; i++) {
            ps = ps.next;
        }
        while (ps != null){
            pf = pf.next;
            ps = ps.next;
        }
        return pf;
    }
}
