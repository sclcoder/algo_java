package com.leetcode.linkedList;

/**
 * 剑指 Offer 35. 复杂链表的复制
 *
 * 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
 *

 * 示例 1：
 *
 * 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * 输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
 *
 * 示例 2：
 *
 * 输入：head = [[1,1],[2,1]]
 * 输出：[[1,1],[2,1]]
 *
 * 示例 3：
 *
 * 输入：head = [[3,null],[3,0],[3,null]]
 * 输出：[[3,null],[3,0],[3,null]]
 *
 * 示例 4：
 *
 * 输入：head = []
 * 输出：[]
 * 解释：给定的链表为空（空指针），因此返回 null。
 *
 *
 *
 * 提示：
 *
 *     -10000 <= Node.val <= 10000
 *     Node.random 为空（null）或指向链表中的节点。
 *     节点数目不超过 1000 。
 *
 *
 *
 * 注意：本题与主站 138 题相同：https://leetcode-cn.com/problems/copy-list-with-random-pointer/
 */

/**
 * 复杂链表的节点
 */
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }

    public Node(int val, Node random) {
        this.val = val;
        this.random = random;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String tem = random == null ? "null": Integer.toString(random.val);
        /// 关键点在于.append(next) 这样在外部只要输出链表头，就可以输出整个链表的结构
        sb.append(val).append("[").append(tem).append("]").append("-->").append(next);
        return sb.toString();
    }
}

public class _剑指offer35复制一个复杂链表 {
    /**
     * 优秀的解法
     * 看到的奇特的思路:
     * 1.在原链表的基础上一次复制每个节点 : 注意此处的复制 将random的值也复制了
     * 如 1 2 3 4 5 复制后 1 1' 2 2' 3 3' 4 4' 5 5'
     * 2.修改复制后节点的random的值。node'.random = node.next
     * 3.恢复原始链表、构建出新链表
     *
     *
     * 复盘: 复制链表本身没有任何难度。关键的设计就在于新链表中如何查找到random节点。
     * 该解法的优秀之处便在处理random节点上，将新复制的节点追加在原始节点的尾部,这样新节点的random
     * 指向的是原始节点的值，那怎样找到random指向的新节点呢, so easy, 原始节点的下一个节点及复制的节点
     */
    public static Node copyRandomList(Node head) {
        if (head == null) return head;
        Node cur = head;
        /**
         * 复制Node节点
         */
        while (cur != null){
            Node node = new Node(cur.val * 100);
            node.random = cur.random;
            node.next = cur.next;
            cur.next = node;
            cur = node.next;
        }
        /**
         * 更改新创建节点的random指向
         */
        cur = head;
        while (cur != null){
            Node copyNode = cur.next;
            if (copyNode.random != null){
                copyNode.random = copyNode.random.next;
            }
            cur = copyNode.next;
        }

        System.out.println("中间过程： " + head);

        /**
         * 拆分链表
         */
        Node newHead = head.next;
        Node newCur = newHead;
        cur = head;

        while (cur.next != null && newCur.next != null){
            cur.next = newCur.next;
            cur = newCur.next;
            newCur.next = cur.next;
            newCur = cur.next;
        }
        cur.next = null;
        newCur.next = null;
        return newHead;
    }


    public static void main(String[] args) {
        // 7-n    13-0   11-4   10-2   1-0
        Node node7 = new Node(7,null);
        Node node13 = new Node(13,node7);
        Node node11 = new Node(11,null);
        Node node10 = new Node(10,node13);
        Node node1 = new Node(1,node7);
        node11.random = node10;
        node7.next = node13;
        node13.next = node11;
        node11.next = node10;
        node10.next = node1;

        Node cur = node7;
        System.out.println("为了区分新旧值，复制的链表值都 x 100");

        System.out.println("原始数据： " + cur);

        System.out.println("最终结果： " + copyRandomList(cur));


    }


    /**
     * 思路: 就是构建一条新的链表呗
     * 在设置random节点的过程中存在多余的查找。
     * 可以使用HashMap 记录对应Node所在链表中的index, 这样能查找速度会更快。本质思想没有区别。
     */
    public Node copyRandomList_iterate(Node head) {

        Node dummy = new Node(0);
        Node tail = dummy;
        Node cur1 = head;
        /**
         * 复制原始链表的基本结构
         */
        while (cur1 != null){
            Node node = new Node(cur1.val);
            if (cur1.random == null){
                node.random = null;
            }
            tail.next = node;
            tail = node;
            cur1 = cur1.next;
        }
        tail.next = null;

        /**
         * 设置新链表的random值
         */
        Node newHead = dummy.next;
        Node cur2 = newHead;
        cur1 = head;
        while (cur1 != null){
            if (cur1.random != null){
                /**
                 * 获取random节点在原始链表中的位置
                 */
                int index = getIndex(head,cur1.random);

                // 测试
                System.out.println("节点:" +cur1.val + "index: " + index);

                /**
                 * 获取并设置新链表的random节点
                 */
                cur2.random = getRandom(newHead, index);
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }

        return dummy.next;
    }

    /**
     * 获取random节点(非空)在链表中的位置
     * @param head
     * @param random
     * @return
     */
    public int getIndex(Node head, Node random){
        int index = 0;
        Node cur = head;
        while (cur != null){
            if (cur.equals(random)) return index;
            cur = cur.next;
            index++;
        }
        return index;
    }

    /**
     * 通过index获取链表中的random节点
     */
    public Node getRandom(Node head, int index){
        Node cur = head;
        while (index > 0){
            cur = cur.next;
            index--;
        }
        return cur;
    }
}

