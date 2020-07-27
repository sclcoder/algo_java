package com.ds_algo.h_RBTree;

import com.tool.binaryTree.printer.BinaryTrees;

public class TestRBTreeMain {
    public static void main(String[] args) {
        // 对比测试结果 http://520it.com/binarytrees/
        RBTree<Integer> rb = new RBTree<>(null);
        Integer[] nums = new Integer[]{54, 87, 77, 38, 45, 99, 81, 50, 94, 29, 41, 55, 98, 70, 16, 97, 19, 4, 63, 34, 11, 28, 88, 58, 20, 72, 1, 40, 79, 59, 57, 53, 85, 18, 30, 8, 3, 65, 75, 27, 10, 76, 15, 83, 96, 52, 47, 37, 71, 49, 2, 86, 44, 24, 33, 62, 84, 60, 95, 100, 82, 21, 5, 80};
        for (int i = 0; i < nums.length; i++) {
            rb.add(nums[i]);
            System.out.println("【" +  nums[i] + "】");
            System.out.println("---------------------");
            BinaryTrees.println(rb);
        }


        System.out.println("---------------------------------删除开始----------------------------");

        for (int i = 0; i < nums.length; i++) {
            rb.remove(nums[i]);
            System.out.println("【" +  nums[i] + "】");
            System.out.println("---------------------");
            BinaryTrees.println(rb);
        }
    }
}
