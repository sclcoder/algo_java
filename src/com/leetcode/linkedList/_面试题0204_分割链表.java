package com.leetcode.linkedList;

/**
 * 面试题 02.04. 分割链表
 *
 * 编写程序以 x 为基准分割链表，使得所有小于 x 的节点排在大于或等于 x 的节点之前。
 * 如果链表中包含 x，x 只需出现在小于 x 的元素之后(如下所示)。分割元素 x 只需处于“右半部分”即可，其不需要被置于左右两部分之间。
 *
 * 示例:
 *
 * 输入: head = 3->5->8->5->10->2->1, x = 5
 * 输出: 3->1->2->10->5->5->8
 */

public class _面试题0204_分割链表 {

    /**
     * 不使用双链表的思路: 双指针法
     * 设计一个指针p, 其左边的值都是 小于x的节点。指针cur遍历链表。
     * 如果cur的值小x, 那么就和p的值交换，然后p指向下一个位置，cur指向下一个位置
     *
     */
    public ListNode partition_1(ListNode head, int x) {

        ListNode cur = head;
        ListNode p  = head;

        while (cur != null){
            if (cur.val < x){
                int temp = p.val;
                p.val = cur.val;
                cur.val = temp;
                p = p.next;
            }
            cur = cur.next;

        }
        return head;
    }

    /**
     * 思路: 将两端的值分别串接起来 ，最后将两条量表串到一起
     * 这种解法可以直接解决86题
     */
    public ListNode partition(ListNode head, int x) {

        ListNode lDummyHead = new ListNode();
        ListNode rDummyHead = new ListNode();

        ListNode lNode = lDummyHead;
        ListNode lTrail = lDummyHead; // 因为要向后拼接Node,所以要准备一个尾部节点

        ListNode rNode = rDummyHead;
        ListNode rTrail = rDummyHead;

        ListNode cur = head;

        while (cur != null){
            if (cur.val < x){
                lTrail.next = cur;
                lTrail = cur;
            } else {
                rTrail.next = cur;
                rTrail = cur;
            }
            cur = cur.next;
        }
        rTrail.next = null;
        lTrail.next = rDummyHead.next;
        return lDummyHead.next;
    }
}
