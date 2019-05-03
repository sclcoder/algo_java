package com.leetcode.queue;
/**
 * 使用队列实现栈的下列操作：
 *
 *     push(x) -- 元素 x 入栈
 *     pop() -- 移除栈顶元素
 *     top() -- 获取栈顶元素
 *     empty() -- 返回栈是否为空
 *
 * 注意:
 *
 *     你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合法的。
 *     你所使用的语言也许不支持队列。 你可以使用 list 或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
 *     你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 思路:
 * 使用两个队列 A 、B
 * 1.push时 入队列A
 * 2.pop时  将队列A中的前size-1个元素入队列B, A中最后一个就是pop时的值
 */
public class _225_用队列实现栈 {
    class MyStack {
        Queue<Integer> queueA;
        Queue<Integer> queueB;
        /** Initialize your data structure here. */
        public MyStack() {
            queueA = new LinkedList<>();
            queueB = new LinkedList<>();
        }

        /** Push element x onto stack. */
        public void push(int x) {
            queueA.offer(x);
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            while (!queueA.isEmpty() && queueA.size() > 1){
                 queueB.offer(queueA.poll());
            }
            int topElement = queueA.poll(); // poll删除元素
            queueA = queueB;
            queueB = new LinkedList<>();
            return topElement;
        }

        /** Get the top element. */
        public int top() {
            while (!queueA.isEmpty() && queueA.size() > 1){
                queueB.offer(queueA.poll());
            }
            return queueA.peek(); // peek不删除元素
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return queueA.isEmpty() && queueB.isEmpty();
        }
    }

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
}
