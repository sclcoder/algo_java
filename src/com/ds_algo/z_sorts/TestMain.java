package com.ds_algo.z_sorts;

import com.tool.common.Asserts;
import com.tool.common.Integers;
import java.util.Arrays;

public class TestMain {

    public static void main(String[] args) {

        Integer[] array = Integers.random(1000000,1000,2000000);
//        Integer[] array = Integers.same(10000000,0);
        testSorts(array,
//                new BubbleSort(),
//                new SelectSort(),
                new HeapSort(),
//                new InsertSort(),
                new MegerSort(),
                new QuickSort()
        );
    }

    private static void testSorts(Integer[] array , Sort...sorts){
        for (Sort sort : sorts) {
            /**
             * 测试排序是否正确
             */
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
        }

//        Arrays.sort(sorts); /// 测试是否具备可比较性

        /**
         * 打印排序对象
         */
        for (Sort sort : sorts) {
            System.out.println(sort);
        }
    }
}
