package com.ds_algo.h_RBTree;

import com.tool.binaryTree.printer.BinaryTrees;

public class TestRBTreeMain {
    public static void main(String[] args) {
        // 对比测试结果 http://520it.com/binarytrees/
        RBTree<Integer> rb = new RBTree<>(null);
        Integer[] nums = new Integer[]{58, 56, 43, 88, 17, 65, 45, 6, 34, 5, 87, 60, 98, 78, 94, 50, 63, 3, 46};
        for (int i = 0; i < nums.length; i++) {
            rb.add(nums[i]);
            System.out.println("【" +  nums[i] + "】");
            System.out.println("---------------------");
            BinaryTrees.println(rb);
        }


//        for (int i = 0; i < nums.length; i++) {
//            avl.remove(nums[i]);
//            System.out.println("【" +  nums[i] + "】");
//            System.out.println("---------------------");
//            BinaryTrees.println(avl);
//        }
    }
}
