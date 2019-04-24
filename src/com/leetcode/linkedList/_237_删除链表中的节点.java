package com.leetcode.linkedList;

public class _237_删除链表中的节点 {

    /**
     * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
     * Definition for singly-linked linkedList.
     * 请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点，你将只被给定要求被删除的节点。
     * 说明:
     *     链表至少包含两个节点。
     *     链表中所有节点的值都是唯一的。
     *     给定的节点为非末尾节点并且一定是链表中的一个有效节点。
     *     不要从你的函数中返回任何结果。
     *     输入: head = [4,5,1,9], node = 5
     *     输出: [4,1,9]
     *     解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     */
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    /** 注意审题 题目并没有给出head节点,只给了要删除的节点
     * scl: 此题乍一看感觉很简单,仔细一看发现我曹没有head节点,这可没法做啊,无法获取到前一个节点,链要断了
     *  那换个思路，我们可以将节点的值依次想前移动,最后将最后一个节点断开即可,这样就可以不需要head节点了。
     *  时间复杂度O(n)
     *
     *  官方方案:更好一些O(1)
     *  思路：将要删除的节点的值用下一个节点的值覆盖掉,将要删除节点的下一个节点用下下一个节点覆盖即可！
     */
    public void deleteNode(ListNode node) {
        while (node.next != null){
            node.val = node.next.val;
            if (node.next.next == null){
                node.next = null;
                return;
            }
            node = node.next;
        }
        return;
    }
    /**
     *  so easy 但是很好
     */
    public void deleteNode2(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
