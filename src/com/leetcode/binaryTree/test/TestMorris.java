package com.leetcode.binaryTree.test;
import com.leetcode.binaryTree.com.mj.printer.BinaryTrees;
import com.leetcode.binaryTree.common.BinarySearchTree;

import java.util.Random;

public class TestMorris {
    /**
     * 一个可以构建一个搜索二叉树的类
     */
    private static class MorrisTree extends BinarySearchTree<Integer> {

        public int kthSmallest_morris(int k) {
            System.out.println("k参数值: " + k);
            Node<Integer> node = root;
            while (node != null){
                if (node.left != null){
                    // 查找node的前驱节点
                    Node<Integer> pNode = node.left;
                    while (pNode.right != null && pNode.right != node){
                        pNode = pNode.right;
                    }
                    if (pNode.right == null){
                        pNode.right = node;
                        node = node.left; // 继续循环
                    }
                    if (pNode.right == node){
                        pNode.right = null;
                        System.out.print(node.element + " ");
                        node = node.right; // 继续循环
                    }
                } else { // node.left == null
                    System.out.print(node.element + " ");
                    node = node.right; // 继续循环
                }
            }
            return -1;
        }



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
        tree.kthSmallest_morris( 2);
//        tree.morris();

        System.out.println("------------------");
        BinaryTrees.println(tree);
    }


}
