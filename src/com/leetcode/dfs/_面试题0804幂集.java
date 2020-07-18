package com.leetcode.dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * 面试题 08.04. 幂集
 * 幂集。编写一种方法，返回某集合的所有子集。集合中不包含重复的元素。
 * 说明：解集不能包含重复的子集。
 *
 * 示例:
 *  输入： nums = [1,2,3]
 *  输出：
 * [
 *   [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 */
public class _面试题0804幂集 {

    static List<List<Integer>> res = new ArrayList<>();

    static public List<List<Integer>> subsets(int[] nums) {
        List<Integer> list = new ArrayList<>();
        dfs(0,nums,list);
        res.add(new ArrayList<>());
        return res;
    }
    static public void dfs(int cur , int[] nums , List<Integer> list){

        /// 每次面临的选择 : 只要没选过的都算
        for (int i = cur; i < nums.length; i++) {
            list.add(nums[i]);
            res.add(new ArrayList<>(list));
            dfs(i + 1, nums ,list);
            list.remove(list.size()-1);
        }
    }

    public static void main(String[] args) {
        System.out.println(subsets(new int[]{1,2,3}));
    }
}
