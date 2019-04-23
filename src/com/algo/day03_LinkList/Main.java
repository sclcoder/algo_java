package com.algo.day03_LinkList;


import com.algo.day03_LinkList.single.SingleLinkedList2;

public class Main {
    public static void  testLinkedList(){
        SingleLinkedList2<Integer> linkedList = new SingleLinkedList2<>();
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

    public static void testArrayList(){
        ArrayList2<Integer> list2 = new ArrayList2<>();
        for (int i = 0; i < 50; i++) {
            list2.add(i);
        }
        for (int i = 0; i < 50; i++) {
            list2.remove(0);
        }
        System.out.println(list2);
    }


    static void testList(List<Integer> list) {
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);

        list.add(0, 55); // [55, 11, 22, 33, 44]
        list.add(2, 66); // [55, 11, 66, 22, 33, 44]
//        list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]

//        list.remove(0); // [11, 66, 22, 33, 44, 77]
//        list.remove(2); // [11, 66, 33, 44, 77]
//        list.remove(list.size() - 1); // [11, 66, 33, 44]
//
//        Asserts.test(list.indexOf(44) == 3);
//        Asserts.test(list.indexOf(22) == List.ELEMENT_NOT_FOUND);
//        Asserts.test(list.contains(33));
//        Asserts.test(list.get(0) == 11);
//        Asserts.test(list.get(1) == 66);
//        Asserts.test(list.get(list.size() - 1) == 44);

        System.out.println(list);
    }

    public static void main(String[] args) {

//        testArrayList();
//        testLinkedList();

//        testList(new ArrayList<>());
        testList(new LinkedList<>());


        /*
         * gc root对象
         * 1> 被局部变量指向的对象
         */
    }

}
