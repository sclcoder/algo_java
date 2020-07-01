package com.interviewCode._00_偶遇的面试题;
import java.util.ArrayList;
import java.util.List;
public class _2_Counting_Bits {

    public static void main(String[] args) {
        // Write your code here
        int n = 37;
        List<Integer> listRemainder = new ArrayList<>();
//        List<Integer> result = new ArrayList<>();
        int remainder = 0;
        while( n != 0){
            remainder = n % 2;
            listRemainder.add(remainder);
            n = n / 2;
        }
        for(int i = listRemainder.size()-1; i >= 0 ;i--){
            System.out.println(listRemainder.get(i));
        }
    }
}
