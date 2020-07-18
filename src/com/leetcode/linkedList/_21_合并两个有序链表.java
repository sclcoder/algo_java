package com.leetcode.linkedList;

/** https://leetcode-cn.com/problems/merge-two-sorted-lists/
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * 示例：
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 */
public class _21_合并两个有序链表 {

    /**
     * 迭代的解法
    */
    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode tail  = dummy;
        ListNode cur1 = l1;
        ListNode cur2 = l2;
        while (cur1 != null && cur2 != null){

            if (cur1.val <= cur2.val){
                tail.next = cur1;
                tail = cur1;
                cur1 = cur1.next;
            } else {
                tail.next = cur2;
                tail = cur2;
                cur2 = cur2.next;
            }
        }
        tail.next = cur1 != null ? cur1 : cur2;
        return dummy.next;
    }


    /**
     * 递归法
     *
     * 1->2->4,
     * 1->3->4
     * 1->          0 l1  return 1-2-3-4-4  1-1-2-3-4-4
     * 1->          1 l2  return 2-3-4-4  1-2-3-4-4
     * 2->          2 l1  return 3->4->4  2-3-4-4
     * 3->          3 l2  return 4->4    3->4->4
     * 4->          4 l1  return l2的4   4->4
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val <= l2.val){
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists1(l1,l2.next);
            return l2;
        }
    }
}
