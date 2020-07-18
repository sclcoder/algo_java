package com.leetcode.linkedList;

import java.util.HashSet;

/** https://leetcode-cn.com/problems/linked-list-cycle/
 141. 环形链表
 给定一个链表，判断链表中是否有环。

 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
 如果 pos 是 -1，则在该链表中没有环。

 示例 1：
       3->2->0->-4
          ^      |
          |      |
          _______|
 输入：head = [3,2,0,-4], pos = 1
 输出：true
 解释：链表中有一个环，其尾部连接到第二个节点。

 示例 2：

 输入：head = [1,2], pos = 0
 输出：true
 解释：链表中有一个环，其尾部连接到第一个节点。


 示例 3：

 输入：head = [1], pos = -1
 输出：false
 解释：链表中没有环。

 进阶：

 你能用 O(1)（即，常量）内存解决此问题吗？
 */
public class _141_环型链表 {

    /**
     * 暴力迭代
     *         3->2->0->-4
     *           ^      |
     *           |      |
     *           _______|
     一个检测指针 cur2，遍历指针 cur1，count记录遍历指针cur1走的步数
     遍历指针每走一步，检测指针cur2就走遍历指针 cur1 之前走过的节点，若发现相同的节点便说明有环
     知道遍历节点 cur1 为 NULL 停止
     */

    public boolean hasCycle_iterate(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode cur2 = head;
        ListNode cur1 = head.next;
        int count = 1;
        while (cur1 != null){
            for (int i = count - 1; i >=0 ; i--) {
                if (cur2 == cur1) return true;
                cur2 = cur2.next;
            }
            cur2 = head;
            cur1 = cur1.next;
            count++;
        }
        return false;
    }
    /**
     * 借用HashSet
     */
    public boolean hasCycle_set(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        ListNode cur = head;
        while (cur != null){
            if (set.contains(cur)) return true;
            set.add(cur);
            cur = cur.next;
        }
        return false;
    }

    /**
     * 快慢指针
     * @param head
     * @return
     */
    public boolean hasCycle_double_point(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != null && fast.next != null){
            if (slow == fast) return true;
            fast = fast.next.next;
            slow = slow.next;
        }
        return false;

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
