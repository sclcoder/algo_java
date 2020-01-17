package com.leetcode.linkedList;

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

    /** 官方解答
     * 递归版本稍微复杂一些，其关键在于反向工作。假设列表的其余部分已经被反转，现在我该如何反转它前面的部分？假设列表为：n1 → … → nk-1 → nk → nk+1 → … → nm → Ø
     * 若从节点 nk+1 到 nm 已经被反转，而我们正处于 nk。
     * n1 → … → nk-1 → nk → nk+1 ← … ← nm
     * 我们希望 nk+1 的下一个节点指向 nk。
     * 所以，nk.next.next = nk;
     * 要小心的是 n1 的下一个必须指向 Ø 。如果你忽略了这一点，你的链表中可能会产生循环。如果使用大小为 2 的链表测试代码，则可能会捕获此错误。
     * @param head
     * @return
     */
    public ListNode reversiList2(ListNode head){
        if (head == null || head.next == null){
            return head;
        }
        ListNode newNode = reversiList2(head.next);
        head.next.next = head;
        head.next = null;
        // 翻转好的节点
        return newNode;
    }
}
