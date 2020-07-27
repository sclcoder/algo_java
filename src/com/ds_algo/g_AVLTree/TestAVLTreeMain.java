package com.ds_algo.g_AVLTree;

import com.tool.binaryTree.printer.BinaryTrees;

public class TestAVLTreeMain {

    public static void main(String[] args) {
        // 对比测试结果 http://520it.com/binarytrees/
        AVLTree<Integer> avl = new AVLTree<>(null);
        Integer[] nums = new Integer[]{58, 56, 43, 88, 17, 65, 45, 6, 34, 5, 87, 60, 98, 78, 94, 50, 63, 3, 46};
        for (int i = 0; i < nums.length; i++) {
            avl.add(nums[i]);
            System.out.println("【" +  nums[i] + "】");
            System.out.println("---------------------");
            BinaryTrees.println(avl);
        }

        System.out.println("---------------------------------删除开始----------------------------");

        for (int i = 0; i < nums.length; i++) {
            avl.remove(nums[i]);
            System.out.println("【" +  nums[i] + "】");
            System.out.println("---------------------");
            BinaryTrees.println(avl);
        }
    }
}
