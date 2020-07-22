package com.ds_algo.z_sorts;

import com.ds_algo.z_sorts.cmp.*;
import com.tool.common.Asserts;
import com.tool.common.Integers;
import java.util.Arrays;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class TestMain {

    public static void main(String[] args) {

//        Integer[] array = Integers.same(10000000,0);
//        Integer[] array = Integers.ascOrder(0,10000);
//        Integer[] array = Integers.descOrder(0,10000);
//        Integer[] array = Integers.centerAscOrder(0,10000,5000);
        Integer[] array = Integers.random(200000,1,200000);

//        System.out.println("排序前" + Arrays.toString(array));
        testSorts(array,
//                new BubbleSort(),
//                new SelectSort(),
//                new InsertSort(),
                new HeapSort(),
                new ShellSort(),
                new QuickSort(),
                new MergeSort()
                );
    }

    private static void testSorts(Integer[] array , Sort...sorts){
        for (Sort sort : sorts) {
            /*
             * 测试排序是否正确
             */
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
//            System.out.println("排序后" + Arrays.toString(newArray));
            Asserts.test(Integers.isAscOrder(newArray));
        }

        Arrays.sort(sorts);

        /*
         * 打印排序对象
         */
        for (Sort sort : sorts) {
            System.out.println(sort);
        }

        System.out.println("------------排序正确-----------");

    }
}
