package com.ds_algo.l_heap;

import com.tool.binaryTree.printer.BinaryTrees;
import java.util.Comparator;

@SuppressWarnings({"unused"})
public class TestHeapMain {

    public static void main(String[] args) {
//        test();
//        test1();
//        test2();
        test3();
    }

    // topK 问题
    public static void test3(){
        // 构建小顶堆
        BinaryHeap<Integer> heap = new BinaryHeap<>( new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) { return o2 - o1; }
        });


        /*
         * 找出最大的前k个数
         * 1.构建一个大小为k的小顶堆
         * 2.每次扫描元素V与堆顶元素top比较大小
         *   如果 V > top, 替换top。
         *   如果 V <= top , 继续扫描
         *
         */
        int k = 3;
        Integer[] data = {51, 30, 39, 92, 74, 25, 16, 93,
                91, 19, 54, 47, 73, 62, 76, 63, 35, 18,
                90, 6, 65, 49, 3, 26, 61, 21, 48};

        for (int i = 0; i < data.length; i++) {
            if (heap.size() < k){
                // 前k个数添加到小顶堆
                heap.add(i);
            } else if (data[i] > heap.get()){
                heap.replace(data[i]);
            }
        }

        BinaryTrees.println(heap);
    }



    public static void test2(){
        // 构建小顶堆
        Integer[] data = new Integer[]{44,2,88,34,5,25,94,72};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) { return o2 - o1; }
        });
        BinaryTrees.println(heap);
    }


    public static void test1(){
        Integer[] data = new Integer[]{44,2,88,34,5,25,94,72};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data);
        BinaryTrees.println(heap);
        data[0] = 100;
        data[3] = 300;
        BinaryTrees.println(heap);
    }


    public static void test(){
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(44); // java会自动装箱、拆箱
        heap.add(2);
        heap.add(88);
        heap.add(34);
        heap.add(5);
        heap.add(25);
        heap.add(94);
        heap.add(72);

        BinaryTrees.println(heap);

        heap.remove();

        BinaryTrees.println(heap);

        heap.remove();

        BinaryTrees.println(heap);

        System.out.println(heap.replace(1));

        BinaryTrees.println(heap);

    }
}

