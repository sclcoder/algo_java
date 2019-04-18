package com.algo.day02_ArrayList;


public class Main {
    public static void main(String[] args) {
//        java.util.ArrayList
        test();
        test2();
    }

    private static void test(){
        ArrayList<Integer> list  = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(list);
        list.remove(9);
        Assert.test(list.get(1) == 1);
        System.out.println(list);
    }

    private static void test2(){
        // 所有的类，最终都继承java.lang.Object 需要注意内存泄漏问题

        ArrayList<Object> list1 = new ArrayList<>();

        list1.add(new Person(20,"Jack"));
        list1.add(new Person(21,"Rock"));
        list1.add(null);
        list1.add(new Person(22,"Joan"));
        list1.remove(list1.size()-1);
        System.out.println(list1.indexOf(null));

        System.out.println(list1);
    }
}
