package com.leetcode.linkedList;



import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 剑指 Offer 06. 从尾到头打印链表
 *
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 *
 * 示例 1：
 *
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 *
 * 限制：
 *
 * 0 <= 链表长度 <= 10000
 */
public class _剑指offer06从尾到头打印链表 {

    /**
     * 思路二 栈 先进后出
     */

    public int[] reversePrint_stack(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        while (head != null){
            stack.push(head.val);
            head = head.next;
        }
        int[] res = new int[stack.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = stack.pop();
        }
        return res;
    }


    /**
     * 思路一 :递归
     */
    List<Integer> res = new ArrayList<>();
    public int[] reversePrint(ListNode head) {
        helper(head);
        int[] ans = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ans[i] = res.get(i);
        }
        return ans;
    }

    public void recurse(ListNode head){
        if (head == null) return;
        recurse(head.next);
        res.add(head.val);
    }



    /**
     * 我的2b写法，用递归把链表翻转过来了
     */
    public ListNode helper(ListNode head){

        if (head == null) return head;
        if (head.next == null){
            res.add(head.val);
            return head;
        }
        ListNode newHead = helper(head.next);
        head.next.next = head;
        res.add(head.val);
        head.next = null;
        return newHead;
    }
}
