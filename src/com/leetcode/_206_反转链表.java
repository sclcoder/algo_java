package com.leetcode;

import com.algo.day03_LinkList.List;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list/
 * 反转一个单链表。
 * <p>
 * 示例:
 * <p>
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 * <p>
 * 进阶:
 * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
 */
public class _206_反转链表 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    // 非递归
    public ListNode reverseList1(ListNode head) {
        ListNode newNode = null;
        /**
         * 注意边界条件: head = null 才到达最后一个节点的next指针
         */
        while (head != null) {
            ListNode temp = head.next;
            head.next = newNode;
            newNode = head;
            head = temp;
        }
        return newNode;
    }
    // 递归
    public ListNode reversiList2(ListNode head){
        // head == null 直接返回就行没必要翻转,注意head == null一定要在前面
        if (head == null || head.next == null){
            // 最后一个节点,递归函数第一次退栈
            return head;
        }
        // 这层调用获取到尾部节点
        ListNode tailNode = reversiList2(head.next);
        // 注意此时的head是递归函数为退栈后的head
        // 获取到当前节点的下一个节点并改变其next指向为当前的节点
        head.next.next = head;
        // 清空当前节点的next指向
        head.next = null;
        // 返回尾部节点,函数退栈
        return tailNode;
    }
}
