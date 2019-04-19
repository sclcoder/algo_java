package com.algo.day03_LinkList;


public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(linkedList.size(),600);
        linkedList.add(4);
        linkedList.remove(2);
        linkedList.add(4,20);
        linkedList.remove(0);
        linkedList.remove(linkedList.size()-1);
        System.out.println(linkedList);
        System.out.println(linkedList.get(2));

    }
}
