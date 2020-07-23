package com.ds_algo.m_priorityQueue;


public class TestPriorityQueueMain {

    public static void main(String[] args) {

        PriorityQueue<Person> queue = new PriorityQueue<>();
        queue.enQueue(new Person(10,"Jack", 1));
        queue.enQueue(new Person(10,"Luce", 5));
        queue.enQueue(new Person(10,"Rose", 2));
        queue.enQueue(new Person(10,"Kobe", 10));
        queue.enQueue(new Person(10,"Tomas", 2));
        queue.enQueue(new Person(10,"James", 11));

        while (!queue.isEmpty()){
            System.out.println(queue.deQueue());
        }
    }
}
