package com.leetcode.linkedList;

/**
 * 1290. 二进制链表转整数
 * <p>
 * 给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
 * 请你返回该链表所表示数字的 十进制值 。
 * 示例 1：
 * <p>
 * 输入：head = [1,0,1]
 * 输出：5
 * 解释：二进制数 (101) 转化为十进制数 (5)
 * <p>
 * 示例 2：
 * <p>
 * 输入：head = [0]
 * 输出：0
 * <p>
 * 示例 3：
 * <p>
 * 输入：head = [1]
 * 输出：1
 * <p>
 * 示例 4：
 * <p>
 * 输入：head = [1,0,0,1,0,0,1,1,1,0,0,0,0,0,0]
 * 输出：18880
 * <p>
 * 示例 5：
 * <p>
 * 输入：head = [0,0]
 * 输出：0
 * <p>
 * <p>
 * <p>
 * 提示：
 * 链表不为空。
 * 链表的结点总数不超过 30。
 * 每个结点的值不是 0 就是 1。
 */
public class _1290_二进制链表转整数 {
    /**
     * 链表翻转可以用递归。我的第一想法 是用递归来解决该问题？
     * 可是真的能用递归吗？ 求出next节点的十进制 和 index 。然后便可求出当前的十进制数
     * 可是怎样返回两个参数 sum和index呢？？？ 使用全局变量呗
     */

    int base = 0; // 代表左移位数
    public int getDecimalValue(ListNode head) {
        if (head.next == null) return head.val;
        int subV = getDecimalValue(head.next);
        base++;
        return (head.val << base) + subV;
    }


    /**
     *  正序处理该问题，根本也不需要翻转链表
     */
    public int getDecimalValue_best(ListNode head) {

        /**
         * 问题的本质: 链表移动到右侧下一个节点的过程，其实就是二进制数左移1位的结果。
         * 不过具体写代码要注意好细节处理不然差一位就计算错误了
         */
        int res = 0;
        ListNode cur = head;
        while (cur != null){ /// cur是最后一个节点
            res <<= 1;
            res += cur.val;
            cur = cur.next;
        }
        return res;



//        int sum = 0;
//        ListNode cur = head;
//        while (cur != null){
//            sum = sum * 2 + cur.val;
//            cur = cur.next;
//        }
//        return sum;
    }

    public int getDecimalValue_2b2(ListNode head) {
        ListNode newHead = null;
        while (head != null){
            ListNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }

        ListNode cur = newHead;
        int index = 0;
        int sum = 0;
        while (cur != null){
            sum += cur.val << index;
            index++;
            cur = cur.next;
        }
        return sum;
    }

    public int getDecimalValue_2b(ListNode head) {
        /**
         * 思路一: 将链表翻转，翻转后从头遍历，计算每一位的十进制数，求和
         */
        ListNode newHead = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }

        ListNode cur = newHead;
        int index = 0;
        double sum = 0;
        while (cur != null) {
            sum = sum + cur.val * Math.pow(2, index);
            index++;
            cur = cur.next;
        }
        return (int) sum;
    }
}
