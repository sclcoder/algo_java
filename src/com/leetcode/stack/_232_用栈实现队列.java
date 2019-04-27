package com.leetcode.stack;


import java.util.Stack;

/**
 * 使用栈实现队列的下列操作：
 *
 *     push(x) -- 将一个元素放入队列的尾部。
 *     pop() -- 从队列首部移除元素。
 *     peek() -- 返回队列首部的元素。
 *     empty() -- 返回队列是否为空。
 *
 * 示例:
 *
 * MyQueue queue = new MyQueue();
 *
 * queue.push(1);
 * queue.push(2);
 * queue.peek();  // 返回 1
 * queue.pop();   // 返回 1
 * queue.empty(); // 返回 false
 *
 * 说明:
 *
 *     你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
 *     你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
 */

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
public class _232_用栈实现队列 {
    /**
     * 特点: 队列:先进先出 栈:先进后出
     * 思路: 使用两个栈A、B.
     * 1.入队列时,将数据进入A栈
     * 2.出队列时,如果B栈不为空，直接从B栈出栈。如果B栈为空,将A栈中的数据按依次出栈并依次进入B栈,结束后,B栈的栈顶出栈完成一次出队列
     *
     */
    class MyQueue {
        private Stack<Integer> inStack;
        private Stack<Integer> outStack;
        /** Initialize your data structure here. */
        public MyQueue() {
            inStack = new Stack<>();
            outStack = new Stack<>();
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            inStack.push(x);
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            if (outStack.isEmpty()){
                while (!inStack.isEmpty()){
                    outStack.push(inStack.pop());
                }
            }
            return outStack.pop();
        }

        /** Get the front element. */
        public int peek() {
            if (outStack.isEmpty()){
                while (!inStack.isEmpty()){
                    outStack.push(inStack.pop());
                }
            }
            return outStack.peek();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return inStack.isEmpty() && outStack.isEmpty();
        }
    }
}
