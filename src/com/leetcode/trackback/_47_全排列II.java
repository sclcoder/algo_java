package com.leetcode.trackback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _47_全排列II {
    /**
     * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
     *
     * 示例:
     *
     * 输入: [1,1,2]
     * 输出:
     * [
     *   [1,1,2],
     *   [1,2,1],
     *   [2,1,1]
     * ]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/permutations-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static void main(String[] args) {
        permuteUnique(new int[]{1,2,2});
        System.out.println(res);
    }

    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int [] used = new int[nums.length];
        Arrays.sort(nums);
        dfs(nums,result,used); // nums 选择列表 result 路径
        return res;
    }
    private static void dfs(int[] nums, List<Integer> result,int[] used){
        if (result.size() == nums.length){
            res.add(new ArrayList<>(result));
        } else {
            for (int i = 0; i < nums.length ; i++) {
                if (used[i] == 1 ) continue;
                // 这个判断条件是在回溯后,要重新决策时起作用: 回溯时这层决策的状态已经重置, 如果这时决策的选择的值和之前决策选择的值相同, 那么则出现了重复
                // 如果是递归阶段则不需要跳过 而这时used[i-1] == 1
                if (i > 0 && nums[i] == nums[i-1] && used[i-1] == 0) continue;
                result.add(nums[i]);
                used[i] = 1;
                dfs(nums,result,used);
                result.remove(result.size()-1); // 开始回溯
                used[i] = 0; // 状态重置
            }
        }
    }
}
