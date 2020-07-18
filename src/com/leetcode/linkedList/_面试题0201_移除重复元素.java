package com.leetcode.linkedList;

import java.util.HashSet;

/**
 * 面试题 02.01. 移除重复节点
 *
 * 编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
 *
 * 示例1:
 *
 *  输入：[1, 2, 3, 3, 2, 1]
 *  输出：[1, 2, 3]
 *
 * 示例2:
 *
 *  输入：[1, 1, 1, 1, 2]
 *  输出：[1, 2]
 *
 * 提示：
 *
 *     链表长度在[0, 20000]范围内。
 *     链表元素在[0, 20000]范围内。
 *
 * 进阶：
 *
 * 如果不得使用临时缓冲区，该怎么解决？
 */
public class _面试题0201_移除重复元素 {

    /**
     * 不借用临时缓冲区怎么解决？ 将链表排序？链表怎么排序呢？
     * 我一时没想到。。。。
     *  看了题解： 原来是用最原始的暴力解法 双重循环，最原始的迭代
     */


    public ListNode removeDuplicateNodes(ListNode head) {
        ListNode fNode = head;
        while (fNode != null){
            ListNode sNode = fNode;
            while (sNode.next != null){
                if (sNode.next.val == fNode.val){
                    sNode.next = sNode.next.next;
                } else {
                    sNode = sNode.next;
                }
            }
            fNode = fNode.next;
        }
        return head;
    }
    /**
     * 借用HashSet来记录已经存在的元素
     */
    public ListNode removeDuplicateNodes_set(ListNode head) {
        HashSet<Integer> set = new HashSet();
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode prev = dummy;
        ListNode cur = head;
        while (cur != null){
            if (set.contains(cur.val)){
                prev.next = cur.next;
            } else {
                set.add(cur.val);
                prev = cur;
            }
            cur = cur.next;
        }
        return dummy.next;
    }
}
