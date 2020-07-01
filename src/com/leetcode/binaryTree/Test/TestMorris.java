package com.leetcode.binaryTree.Test;
import com.leetcode.binaryTree.com.mj.printer.BinaryTrees;
import com.leetcode.binaryTree.common.BinarySearchTree;

import java.util.Random;

public class TestMorris {
    /**
     * 一个可以构建一个搜索二叉树的类
     */
    private static class MorrisTree extends BinarySearchTree<Integer> {
        /**
         * morris遍历方法
         */
        public void morris(){
            Node<Integer> node = root;
            while (node != null){
                if (node.left != null){
                    // 寻找前驱节点
                    Node<Integer> pred = node.left;
                    while (pred.right != null && pred.right != node){
                        pred = pred.right;
                    }
                    if (pred.right == null){
                        pred.right = node;
                        node = node.left;
                    } else { // pred == node
                        // 输出node .....
                        System.out.print(node.element + " ");
                        pred.right = null;
                        node = node.right;
                    }
                } else { // node.left == null
                    // 输出node
                    System.out.print(node.element + " ");
                    node = node.right;
                }
            }
        }
    }

    public static void main(String[] args) {
        MorrisTree tree = new MorrisTree();
        for (int i = 0; i < 10; i++) {
            tree.add(new Random().nextInt(200));
        }
        BinaryTrees.println(tree);
        System.out.println("------------------");
        tree.morris();
        System.out.println("------------------");
        BinaryTrees.println(tree);
    }


}
