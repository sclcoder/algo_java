package com.leetcode.linkedList;



/**
 * 148. 排序链表
 *
 * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
 *
 * 示例 1:
 *
 * 输入: 4->2->1->3
 * 输出: 1->2->3->4
 *
 * 示例 2:
 *
 * 输入: -1->5->3->4->0
 * 输出: -1->0->3->4->5
 *
 * 我首先想到的是归并排序, 而且对于链表来说，归并排序比较容易实现
 *
 */
public class _148链表排序 {
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        return mergeSort(head);
    }

    /**
     * 归并排序
     * @param head
     * @return
     */
    public static ListNode mergeSort(ListNode head){
        if (head.next == null) return head;
        ListNode mid = getMidNode(head);
        ListNode left = mergeSort(head); // 左侧排序后的链表 头结点为left
        ListNode right = mergeSort(mid); // 右侧排序后的链表 头结点为right
       return merge(left,right);
    }



        /**
         * 分治
         * @param head 链表
         *     1 5 3
         *  这是个错误版本，因为比较有教育意义，留存！！！！！！
         */
        public static ListNode divided(ListNode head){
            /**
             * 有严重的问题: 这里merge的head是错误的，因为merge后链表head是会改变的
             * 当递归回来时，此时的递归栈中的head是未改变的head。而应该合并的是改变后的head
             *
             * 导致写成这个样子的原因，递归函数命名误导了自己。
             * 这里将递归函数定义为divided, 当时就认为只有切分的意思。可是该函数中包含merge部分
             * 所以该函数的本质作用是排序。
             * 既然是做的是排序的事情,那么每次divided即排序后，左右两侧的链表的头节点时会改变的。
             * 所以在merge的时，应该传入改变后的两侧头结点，而不是以前的head
             */
            if (head.next == null) return head; // 只有一个节点
            ListNode midNode = getMidNode(head);
            divided(head);
            divided(midNode);
            return merge(head,midNode);
    }

    /**
     * 有序的合并两个链表
     * @param head1 第一个链表
     * @param head2 第二个链表
     */
    public static ListNode merge(ListNode head1, ListNode head2){
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        while (head1 != null && head2 != null){
            if (head1.val <= head2.val){
                tail.next = head1;
                tail = head1;
                head1 = head1.next;
            } else {
                tail.next = head2;
                tail = head2;
                head2 = head2.next;
            }
        }
        tail.next = head1 != null ? head1 : head2;
        return dummy.next;
    }

    /**
     * 获取链表的中间节点
     * @param head 头结点
     * @return 中间节点
     *
     *  1 2 3 4 5 6 n   mid = 4(index:3)
     *  1 2 3 4 5 n     mid = 3(index:2)
     */
    public static ListNode getMidNode(ListNode head){
        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = null;
        while (fast != null && fast.next !=null){
            fast = fast.next.next;
            prev = slow;
            slow = slow.next;
        }
        if (prev.next != null){
            prev.next = null; // 将链表断开
        }
        return slow;
    }

    public static void main(String[] args) {
        ListNode list = new ListNode(1);
        list.next = new ListNode(5);
        list.next.next = new ListNode(3);

        sortList(list);
        System.out.println(list);
    }
}
