package com.leetcode.priorityQueue;


import com.leetcode.linkedList.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 23. 合并K个排序链表
 *
 * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
 *
 * 示例:
 *
 * 输入:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 输出: 1->1->2->3->4->4->5->6
 */
public class _23合并k个有序链表 {

    /**
     * 优先级队列: (学习一下)
     * 复杂度分析: (k为链表数量、n为每条链表的元素数量)
     *
     */
    public ListNode mergeKLists_priority_queue(ListNode[] lists) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        for (ListNode node : lists) {
            if (node != null){
                queue.add(node);
            }
        }
        while (!queue.isEmpty()){
            ListNode node = queue.poll();
            tail.next = node;
            tail = node;
            if (node.next != null){
                queue.add(node.next);
            }
        }
        return dummy.next;
    }

    /**
     * 分治思想: 从底向上两两合并，最终大合并
     *
     * 复杂度分析: (k为链表数量、n为每条链表的元素数量)
     * 从底向上 合并的复杂度为  (k/2) * 2n  、 (k/4) * 4n(4n是因为上次合并链表链表增长了) 、 ... (k/2^i) *2^i*n
     * 求和 (k/2^i) *2^i*n 其中i为(0,k)
     * 简化：i个kn之和 i = logK 所以 O(knlogk)
     *
     */
    public ListNode mergeKLists_recurse(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        // [begin ,end] 前闭后闭
        return mergeList(0, lists.length-1,lists);
    }

    /**
     *  1 2 3 4 5   midIndex = 2
     *  1 2 3 4     minIndex = 1
     */
    public ListNode mergeList(int begin, int end , ListNode[] lists){
        if ((end-begin) == 0) return lists[begin];
        int mid = (begin + end) >> 1;
        ListNode left = mergeList(begin, mid, lists);
        ListNode right = mergeList(mid+1, end, lists);
        return merge(left, right);
    }
    public ListNode merge(ListNode left, ListNode right){
        ListNode dummy  = new ListNode();
        ListNode tail = dummy;
        while (left != null && right != null){
            if (left.val <= right.val){
                tail.next = left;
                tail = left;
                left = left.next;
            } else {
                tail.next = right;
                tail = right;
                right = right.next;
            }
        }
        tail.next = left != null ? left : right;
        return dummy.next;
    }



    /**
     * 我的想法: 依次合并
     *
     * 复杂度分析: (k为链表数量、n为每条链表的元素数量)
     * 依次合并的时间复杂度: n 2n 3n 4n ... 共k次
     * 求和： n + 2n + 3n ... + kn = (1+k) * k / 2  复杂度O(nk^2)
     */
    public ListNode mergeKLists_common(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        ListNode l1 = lists[0];
        for (int i = 1; i < lists.length ; i++) {
            ListNode l2 = lists[i];
            /// 合并两个链表
            while (l1 != null && l2 != null){
                if (l1.val <= l2.val){
                    tail.next = l1;
                    tail = l1;
                    l1 = l1.next;
                } else {
                    tail.next = l2;
                    tail = l2;
                    l2 = l2.next;
                }
            }
            // 千万要注意写的位置，第一次写到while中了
            tail.next = l1 != null ? l1 : l2;
            // 重置l1、tail
            l1 = dummy.next;
            tail = dummy;
        }
        return l1;
    }
}
