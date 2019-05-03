package com.algo.s06_BinarySearchTree;

import com.algo.s06_BinarySearchTree.file.Files;
import com.algo.s06_BinarySearchTree.printer.BinaryTrees;

import java.util.Comparator;

public class Main {
    private static class PersonComparator1 implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getAge() - o2.getAge();
        }
    }

    private static class PersonComparator2 implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            return o2.getAge() - o1.getAge();
        }
    }

    private static void test1() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        System.out.println(bst.height2());
        BinaryTrees.println(bst);
    }

    private static void test2() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3
        };
        BinarySearchTree<Person> bst = new BinarySearchTree();
        for (int i = 0; i < data.length; i++) {
            bst.add(new Person(data[i]));
        }
        BinaryTrees.println(bst);


        BinarySearchTree<Person> bst2 = new BinarySearchTree(new PersonComparator1());
        for (int i = 0; i < data.length; i++) {
            bst2.add(new Person(data[i]));
        }
        BinaryTrees.println(bst2);

        BinarySearchTree<Person> bst3 = new BinarySearchTree<>(new PersonComparator2());
        for (int i = 0; i < data.length; i++) {
            bst3.add(new Person(data[i]));
        }
        BinaryTrees.println(bst3);
    }

    private static void test3() {
        BinarySearchTree<Integer> bst = new BinarySearchTree();
        for (int i = 0; i < 30; i++) {
            bst.add((int) (Math.random() * 100));
        }
//        BinaryTrees.println(bst);
        Files.writeToFile("/Users/tiny/Desktop/tree.txt", BinaryTrees.printString(bst));
    }

    public static void test4() {
        BinarySearchTree<Person> bst = new BinarySearchTree<>();
        bst.add(new Person(10, "jack"));
        bst.add(new Person(13, "rose"));
        bst.add(new Person(8, "tiny"));
        bst.add(new Person(13, "water"));
        BinaryTrees.println(bst);
    }

    // 遍历
    private static void test5() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 10, 3, 1
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);

//        bst.preOrderTraversal(); // 先序遍历
//          bst.preOrderTraversal(new BinarySearchTree.Visitor<Integer>() {
//              @Override
//              public void visit(Integer element) {
//                  System.out.println("pre_"+element);
//              }
//          });
//        bst.inOrderTraversal();// 中序
//        bst.inOrderTraversal(new BinarySearchTree.Visitor<Integer>() {
//            @Override
//            public void visit(Integer element) {
//                System.out.println("in_"+element);
//            }
//        });
//        bst.postOrderTraversal(); // 后序遍历
//        bst.postOrderTraversal(new BinarySearchTree.Visitor<Integer>() {
//            @Override
//            public void visit(Integer element) {
//                System.out.println("post_" + element);
//            }
//        });

//        bst.levelOrderTraversal(); // 层次遍历
//          bst.levelOrder(new BinarySearchTree.Visitor<Integer>() {
//              @Override
//              public void visit(Integer element) {
//                  System.out.println("av_"+element);
//              }
//          });
    }

    public static void test6() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 10, 3, 1
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        System.out.println(bst);
    }

    public static void main(String[] args) {
        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();

    }
}
