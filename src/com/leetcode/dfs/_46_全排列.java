package com.leetcode.dfs;
import java.util.ArrayList;
import java.util.List;
/**
 * 给定一个没有重复数字的序列，返回其所有可能的全排列。
 *
 * 示例:
 *
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *                  1       2       3
 *                2   3    1  3    1  2
 *                3   2    3  1    2  1
 *               123 132  213 231  312 321
 */
public class _46_全排列 {
    public static void main(String[] args) {
        permute(new int[]{1,2,3});
        System.out.println(res);
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        int[] used = new int[nums.length];
        dfs(nums, nums.length,used, result);
        return res;
    }
    public static void dfs(int[] nums, int count , int[] used, List<Integer> result){
        if (count == 0){
            res.add(new ArrayList<>(result));
        }
        if (count > 0){
            for (int i = 0; i < nums.length ; i++) {
                if (used[i] == 1){
                    continue;
                }
                used[i] = 1;
                result.add(nums[i]);
                dfs(nums,count-1,used,result);
                result.remove(result.size()-1);
                used[i] = 0;
            }
        }
    }

    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> permute_1(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int [] used = new int[nums.length];
        int length = nums.length;
        dfs(nums,result,used); // nums 选择列表 result 路径
        return res;
    }
    private static void dfs(int[] nums, List<Integer> result,int[] used){
        if (result.size() == nums.length){
            res.add(new ArrayList<>(result));
        } else {
            /**
             * https://leetcode-cn.com/problems/permutations/solution/hui-su-suan-fa-xiang-jie-by-labuladong-2/
             * 讲的非常好!!!!!!!
             * 回溯算法流程
             * result = []
             * def backtrack(路径, 选择列表):
             *     if 满足结束条件:
             *         result.add(路径)
             *         return
             *      for 选择 in 选择列表:
             *          # 做选择
             *          将该选择从选择列表移除
             *          路径.add(选择)
             *          backtrack(路径, 选择列表)
             *          # 撤销选择
             *          路径.remove(选择)
             *          将该选择再加入选择列表
             *
             * 作者：labuladong
             * 链接：https://leetcode-cn.com/problems/permutations/solution/hui-su-suan-fa-xiang-jie-by-labuladong-2/
             * 来源：力扣（LeetCode）
             * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
             *
             */
            for (int i = 0; i < nums.length ; i++) {
                if (used[i] == 1) continue;
                result.add(nums[i]);
                used[i] = 1;
                dfs(nums,result,used);
                result.remove(result.size()-1); // 开始回溯
                used[i] = 0; // 状态重置
            }
        }
    }
}
