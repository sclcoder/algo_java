package com.leetcode.linkedList;

/** https://leetcode-cn.com/problems/remove-linked-list-elements/
 * 删除链表中等于给定值 val 的所有节点。
 * 示例:
 * 输入: 1->2->6->3->4->5->6, val = 6
 * 输出: 1->2->3->4->5
 */
public class _203_移除链表元素 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 遍历每个节点，遇到相等的节点删除
     */
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) return head;
        // 头结点的值与val不相等
        if (head.val != val) {
            ListNode p = head;
            ListNode c = head.next;
            while (c != null) {
                if (c.val == val) {
                    p.next = c.next;
                    c = c.next;
                } else {
                    p = c;
                    c = c.next;
                }
            }
        } else {
            // 头结点的值与val相等
            head = head.next;
            if (head != null){
               head = removeElements(head,val);
            }
        }
        return head;
    }

    /**
     * 使用这种递归方式实现非常精巧？为什么我没有想到呢？ 下面来分析一下这种方式的思路
     * 常规思路: 正向思维来做节点的删除,肯定要知道要删除节点的前一个节点,然后一步步做下去。这思路是沿着next指针从头到尾处理
     * 递归思路: 将next指向交给递归函数来控制,递归函数返回什么我就指向什么,递归函数决定删除那些节点。我只负责接收先的指向。
     * 递归的思路正好相反: 递归获取到最后一个节点,然后从最后一个节点开始比较,如果相等就返回节点的下一个节点,不相等就返回该节点。这样就可以通过函数
     * 调用栈避免设计的prev节点,因为递归函数的参数是个天然的prev节点。
     */
    public ListNode removeElements1(ListNode head, int val) {
        if (head == null) return head;
        head.next = removeElements1(head.next,val);
       if (head.val == val){
           return head.next;
       } else {
           return head;
       }
    }
}
