package com.ds_algo.h_RBTree;

import com.tool.binaryTree.printer.BinaryTrees;

public class TestRBTreeMain {
    public static void main(String[] args) {
        // 对比测试结果 http://520it.com/binarytrees/
        RBTree<Integer> rb = new RBTree<>(null);
        Integer[] nums = new Integer[]{54, 87, 77, 38, 45, 99, 81, 50, 94};


        for (Integer integer : nums) {
            rb.add(integer);
            System.out.println("【" + integer + "】");
            System.out.println("---------------------");
            BinaryTrees.println(rb);
        }

        System.out.println("root : " + rb.root());

        System.out.println("---------------------------------删除开始----------------------------");

//        for (Integer num : nums) {
//            rb.remove(num);
//            System.out.println("【" + num + "】");
//            System.out.println("---------------------");
//            BinaryTrees.println(rb);
//        }
    }
}
