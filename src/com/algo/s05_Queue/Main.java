package com.algo.s05_Queue;

public class Main {

    public static void test1(){
        Queue<Integer> queue  = new Queue<>();
        queue.enQueue(11);
        queue.enQueue(12);
        queue.enQueue(13);
        queue.enQueue(13);
        queue.enQueue(14);
        while (!queue.isEmpty()){
            System.out.println(queue.deQueue());
        }
    }
    public static void test2(){
        Deque<Integer> queue = new Deque<>();
		queue.enQueueFront(11);
		queue.enQueueFront(22);
		queue.enQueueRear(33);
		queue.enQueueRear(44);
		while (!queue.isEmpty()) {
            /* 尾  44  33   11  22 头 */
            System.out.println(queue.deQueueRear());
		}
    }
    public static void main(String[] args) {
        test2();
    }

}
