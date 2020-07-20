package com.leetcode.linkedList;


import java.util.Stack;

/**
 * 24. 两两交换链表中的节点
 *
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 *
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 *
 * 示例: 给定 1->2->3->4, 你应该返回 2->1->4->3.
 *           4 -3 -2 -1
 *           2->1->4->3
 *
 */
public  class _22两两交换链表中的节点 {

    /**
     * 递归 :还是递归牛逼
     */
    public static ListNode swapPairs_recurse(ListNode head) {
        // 边界，不需要翻转
        if (head == null || head.next ==null) return head;
        // 写递归时，将head.next.next部分认为已经翻转好了
        ListNode nextPair = swapPairs_recurse(head.next.next);
        // 只考虑当前部分节点的翻转问题。
        ListNode second = head.next;
        head.next = nextPair;
        second.next = head;
        return second;
    }

    /**
     * 迭代法优化
     */
    public static ListNode swapPairs_iterate2(ListNode head) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode prev = dummy;
        ListNode cur = head;

        while (cur != null && cur.next != null){
            ListNode first = cur;
            ListNode second = cur.next;

            first.next = second.next;
            second.next = first;
            prev.next = second;

            prev = first;
            cur = first.next;
        }
        return dummy.next;
    }

    /**
     * 迭代法 : 为什么写的这么繁琐。 原因就是 while循环判断的条件写了两个变量。
     * 导致边界条件变得复杂 其实完全可以将left和right的判断使用几个变量表示
     */
    public static ListNode swapPairs_iterate(ListNode head) {
        if (head == null || head.next == null ) return head;
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode prev = dummy;
        ListNode left = head;
        ListNode right = head.next;

        while (left != null && right != null){
            ListNode temp = right.next;
            right.next = left;
            left.next = null;
            prev.next = right;
            prev = left;

            left = temp;
            if (temp == null){
                break;
            }
            right = temp.next;
        }
        prev.next = left;

        return dummy.next;
    }

    /**
     * 借助栈来简化操作
     */
    public static ListNode swapPairs_stack(ListNode head) {
        if (head == null || head.next == null ) return head;
        Stack<ListNode> stack = new Stack<>();
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode cur = head;
        ListNode fixNode = dummy;
        while (cur != null){
            stack.push(cur);
            if (stack.size() == 2){
                ListNode top = stack.pop();
                ListNode bottom = stack.pop();
                fixNode.next = top;
                bottom.next = null;
                cur = top.next;
                top.next = bottom;
                fixNode = bottom;
            } else {
                cur = cur.next;
            }
        }
        if (stack.size() == 1){
            fixNode.next = stack.pop();
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode list = new ListNode(1);
        list.next = new ListNode(2);
        list.next.next = new ListNode(3);
        list.next.next.next = new ListNode(4);
        list.next.next.next.next = new ListNode(5);

        System.out.println(list);

//        System.out.println(swapPairs_stack(list));
//        System.out.println(swapPairs_iterate(list));
//        System.out.println(swapPairs_iterate2(list));
        System.out.println(swapPairs_recurse(list));
    }

}
