package com.leetcode.linkedList;

import java.util.List;
import java.util.Stack;

/**
 * 剑指 Offer 24. 反转链表
 *
 * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 *
 * 限制：
 *
 * 0 <= 节点个数 <= 5000
 */
public class _剑指offer24翻转链表 {
    /**
     * 迭代法
     */
    public ListNode reverseList_1(ListNode head) {
        ListNode newHead = null;
        while (head != null){
            ListNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }
        return newHead;
    }

    /**
     * 递归法
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * 借助栈
     */
    public ListNode reverseList_stack(ListNode head) {
        if (head == null) return head;
        Stack<ListNode> stack = new Stack<>();
        while (head!=null){
            stack.push(head);
            head = head.next;
        }
        ListNode newHead = stack.pop();
        ListNode tailNode = newHead;
        while (!stack.isEmpty()){
            ListNode cur =  stack.pop();
            tailNode.next = cur;
            tailNode = cur;
        }
        tailNode.next = null;
        return newHead;
    }

}
