package com.interviewCode._00_偶遇的面试题;
import java.util.ArrayList;
import java.util.List;

public class _3_Balance_scals_array {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);
//        list.add(8);
        // 1 3 4 4
        for (int i = 0; i < list.size(); i++) {
            int leftSum = 0;
            int rightSum = 0;
            for (int j = 0; j < i ; j++) {
                leftSum = leftSum + list.get(j);
//                System.out.println(leftSum);
            }
            for (int k = i + 1; k < list.size() ; k++) {
                rightSum = rightSum + list.get(k);
            }
            if (leftSum == rightSum){
                System.out.println(i);
            }
        }

    }
}
