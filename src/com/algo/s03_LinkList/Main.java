package com.algo.s03_LinkList;
import com.algo.s03_LinkList.circle.CircleLinkedList;
import com.algo.s03_LinkList.circle.SingleCircleLinkedList;
import com.algo.s03_LinkList.single.SingleLinkedList;
import com.algo.s03_LinkList.single.SingleLinkedList2;

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
        list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]
        list.remove(0); // [11, 66, 22, 33, 44, 77]
        list.remove(2); // [11, 66, 33, 44, 77]
        list.remove(list.size() - 1); // [11, 66, 33, 44]
        Asserts.test(list.indexOf(44) == 3);
        Asserts.test(list.indexOf(22) == List.ELEMENT_NOT_FOUND);
        Asserts.test(list.contains(33));
        Asserts.test(list.get(0) == 11);
        Asserts.test(list.get(1) == 66);
        Asserts.test(list.get(list.size() - 1) == 44);

        System.out.println(list);
    }

    /**
     * 约瑟夫问题
     */
    public static void josephus(){
        CircleLinkedList<Integer>list = new CircleLinkedList<>();
        for (int i = 1; i <= 8; i++) {
            list.add(i);
        }
        list.reset();
        while (!list.isEmpty()){
            list.next();
            list.next();
            Integer element = list.remove();
            System.out.println(element);
            /**
             * 3 6 1 5 2 8 4 7
             */
        }

    }

    public static void main(String[] args) {

          testArrayList();
          testLinkedList();
          testList(new ArrayList<>()); // 动态数组
          testList(new SingleLinkedList<>()); // 单向链表
          testList(new SingleLinkedList2<>()); // 单向链表（带虚节点）
          testList(new LinkedList<>());// 双向链表
          testList(new SingleCircleLinkedList<>()); // 单向循环链表
          testList(new CircleLinkedList<>()); // 双向循环链表

            josephus();
        /*
         * gc root对象
         * 1> 被局部变量指向的对象
         */
    }

}
