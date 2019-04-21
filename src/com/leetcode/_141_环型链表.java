package com.leetcode;

import com.algo.day03_LinkList.List;

/** https://leetcode-cn.com/problems/linked-list-cycle/
 * 给定一个链表，判断链表中是否有环。
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 */
public class _141_环型链表 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /** 快慢指针,两者能相遇说明有环 快指针一次走2步 慢指针一次走1步
     *  为什么快慢指针一定能相遇而不会完美错过呢？
     *  假设有环,那么有一下两种情况
     *  1.快指针在环中落后慢指针1步,那么下一次循环两者肯定相遇
     *  2.快指针在环中落后慢指针2步,那么下一次肯定落后慢指针1步,回到情况1
     *  综上若有环两者一定相遇
     *  证明: fast和slow相差n步  一次循环 n = n+1-2 所以n肯定可以为0 说明可以相遇
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast!=null && fast.next!=null){
            if (slow.val == fast.val) return true;
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }
}
