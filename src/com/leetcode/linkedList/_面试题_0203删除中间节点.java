package com.leetcode.linkedList;

/**
 * 面试题 02.03. 删除中间节点
 * 实现一种算法，删除单向链表中间的某个节点（即不是第一个或最后一个节点），假定你只能访问该节点。
 *
 * 示例：
 * 输入：单向链表a->b->c->d->e->f中的节点c
 * 结果：不返回任何数据，但该链表变为a->b->d->e->f
 */
public class _面试题_0203删除中间节点 {
    /**
     * 这么简单的题竟然让我写的那么麻烦
     * 思路 :  将该节点的值用后继节点的值覆盖，然后跳过后继节点即可。。。。
     * @param node
     */
    public void deleteNode1(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * 这题我写的太傻逼了
     * @param node
     */
    public void deleteNode(ListNode node) {

        ListNode prev = node;
        ListNode cur = node.next;
        while (cur.next != null){
            prev.val = cur.val;
            prev = cur;
            cur = cur.next;
        }
        prev.val = cur.val;
        prev.next = null;

    }
}
