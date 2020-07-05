package com.leetcode.binaryTree.test;
import com.leetcode.binaryTree.com.mj.printer.BinaryTrees;
import com.leetcode.binaryTree.common.BinarySearchTree;

import java.util.Random;

public class TestLargestBST {

    private static class TestTree extends BinarySearchTree<Integer> {
        /**
         * 获取二叉树的最大BST的元素数量
         * @return 最大BST的元素数量
         */
        public int largestBSTSubtree(){
            Node<Integer> node = root;
            if (node == null) return 0;
            if (isValidBST(node)) return getCount(node);
            int lCount = 0;
            int rCount = 0;
            if (isValidBST(node.left)) {
                lCount = getCount(node.left);
            }
            if (isValidBST(node.right)){
                rCount = getCount(node.right);
            }

            return Math.max(lCount,rCount);
        }

        /**
         * 获取二叉树节点数量
         * @param node
         * @return
         */
        public int getCount(Node<Integer> node){
            if (node == null) return 0;
            return 1 + getCount(node.left) + getCount(node.right);
        }

        /**
         * 判断二叉树是否为BST
         */
        long pre = Long.MIN_VALUE;
        public boolean isValidBST(Node<Integer> root){
            if (root == null) return true;
            if (!isValidBST(root.left)) return false;
            if (root.element <= pre) return false;
            pre = root.element;
            return isValidBST(root.right);
        }
    }

    public static void main(String[] args) {
        TestTree tree = new TestTree();
        for (int i = 0; i < 10; i++) {
            tree.add(new Random().nextInt(100));
        }
        BinaryTrees.println(tree);
        System.out.println("-------------");
        System.out.println(tree.size());
        System.out.println("-------------");
        System.out.println(tree.largestBSTSubtree());
    }
}
