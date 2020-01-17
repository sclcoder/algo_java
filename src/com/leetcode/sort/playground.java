package com.leetcode.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class playground {
    public static void main(String[] args) {
        intersection(new int[]{1,2,2,1},new int[]{2,2});
    }
    public static int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                if (nums1[i] == nums2[j]){
                    set.add(nums1[i]);
                }
            }
        }

        System.out.println(set);
        return null;
    }
}
