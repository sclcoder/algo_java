package com.algo.s06_BinarySearchTree;

import com.algo.s06_BinarySearchTree.printer.BinaryTrees;

import java.util.Comparator;

public class Main {
    private static class PersonComparator1 implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getAge() - o2.getAge();
        }
    }

    private static class PersonComparator2 implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o2.getAge() - o1.getAge();
        }
    }
    private static void test1(){
        Integer data[] = new Integer[]{
                7,4,9,2,5,8,11,3
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);
    }

    private static void test2(){
        Integer data[] = new Integer[]{
                7,4,9,2,5,8,11,3
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

    private static void test3(){
        BinarySearchTree<Integer> bst = new BinarySearchTree();
        for (int i = 0; i < 30; i++) {
            bst.add((int)(Math.random() * 100));
        }
        BinaryTrees.println(bst);
    }
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }
}
