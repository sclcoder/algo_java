package com.ds_algo.z_sorts;

import com.ds_algo.z_sorts.cmp.*;
import com.tool.common.Asserts;
import com.tool.common.Integers;
import java.util.Arrays;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class TestMain {

    public static void main(String[] args) {

        Integer[] array = Integers.random(1000,1,2000);
//        Integer[] array = Integers.same(10000000,0);
        testSorts(array,
                new BubbleSort(),
                new SelectSort(),
                new InsertSort(),
                new HeapSort(),
                new MergeSort(),
                new QuickSort()
        );
    }

    private static void testSorts(Integer[] array , Sort...sorts){
        for (Sort sort : sorts) {
            /*
             * 测试排序是否正确
             */
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
        }

        Arrays.sort(sorts);

        /*
         * 打印排序对象
         */
        for (Sort sort : sorts) {
            System.out.println(sort);
        }
    }
}
