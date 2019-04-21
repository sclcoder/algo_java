package com.leetcode;

/**
 * https://leetcode-cn.com/problems/palindrome-linked-list/
 * 请判断一个链表是否为回文链表。
 * 示例 1:
 * 输入: 1->2
 * 输出: false
 * <p>
 * 示例 2:
 * 输入: 1->2->2->1
 * 输出: true
 * 进阶：
 * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 */

import com.algo.day03_LinkList.List;

/**
 * 之前看过此题的思路：利用快慢指针
 *
 */
public class _234_回文链表 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 在处理奇数、偶数个节点上还不够优雅，应该有改进方法
     * 好像翻转后半部分就不需要考虑奇数、偶数问题
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true; // 只有一个节点肯定是true
        ListNode fast = head;
        ListNode slow = head;
        ListNode temp = null;
        while (fast != null){
            /**
             * 1.快速找到中间的节点
             * 2.将慢指针指向的前半部分翻转
             * 3.从中间开始向左右两边遍历比较
             */
            if (fast.next != null){ // 偶数个节点
                slow = slow.next;
                fast = fast.next.next;
                // 遍历时顺便翻转前半部分
                head.next = temp; // 翻转代码
                temp = head;
                head = slow;
            } else { // 奇数个节点
                slow = slow.next;
                fast = fast.next;
                // 遍历时顺便翻转前半部分
                head.next = temp; // 翻转代码
                head = slow;
            }

        }
        while (temp != null){
            if (temp.val != head.val) return false;
            temp = temp.next;
            head = head.next;
        }
        return true;
    }

    public boolean isPalindrome2(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = null;
        /** 查找中间位置
         * 条件 fast != null && fast.next != null
         * 奇数  1 2 3 4 5
         * slow     3      slow定位到3
         * 偶数  1 2 3 4 5 6
         * slow       4    slow定位到4
         */
        while (fast != null && fast.next != null){
                slow = slow.next;
                fast = fast.next.next;
        }

        /** 翻转右半边
         * 奇数  1 2 3 2 1
         * 左边 1->2->3->null  右边 5->4->3->null
         * 偶数  1 2 3 4 5 6
         * 左边 1->2->3->4->null  右边 6->5->4->null
         *
         * 偶数  1 2 3 3 2 1  是回文字符
         * 左边 1->2->3->3->null  右边 1->2->3->null
         */
        while (slow != null){
            ListNode tail = slow.next;
            slow.next = prev;
            prev = slow;
            slow = tail;
        }
        /** 检查是否为回文字符
         *  有其一是null就说明比较完了,可以确定是回文字符了
         */
        while (prev != null && head != null){
            if (prev.val != head.val) return false;
            prev = prev.next;
            head = head.next;
        }
        return true;
    }

    /** 高票解法C++
     *
     * 其一，find mid node 使用快慢指针找到链表中点。 其二，reverse 逆序后半部分。 其三，check 从头、终点，开始比较是否相同。
     * bool isPalindrome(ListNode* head) {//O(n)、O(1)
     *     ListNode* slow = head, *fast = head,  *prev = null;
     *     while (fast){//find mid node
     *         slow = slow->next;
     *         fast = fast->next ? fast->next->next: fast->next;
     *     }
     *     while (slow){//reverse
     *         ListNode* ovn = slow->next;
     *         slow->next = prev;
     *         prev = slow;
     *         slow = ovn;
     *     }
     *     while (head && prev){//check
     *         if (head->val != prev->val){
     *             return false;
     *         }
     *         head = head->next;
     *         prev = prev->next;
     *     }
     *     return true;
     * }
     */
}
