package com.leetcode.trackback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _90_子集II {
    public static void main(String[] args) {
        subsetsWithDup(new int[] {1,2,2});
        System.out.println(res);
    }

    /**
     *
     * 给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
     *
     * 说明：解集不能包含重复的子集。
     *
     * 示例:
     *
     * 输入: [1,2,2]
     * 输出:
     * [
     *   [2],
     *   [1],
     *   [1,2,2],
     *   [2,2],
     *   [1,2],
     *   []
     * ]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/subsets-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<Integer> result = new ArrayList<>();
        res.add(new ArrayList<>(result));
        Arrays.sort(nums);  // 先排序
        dfs(nums,result,0);
        return res;
    }
    private static void dfs(int[] nums, List<Integer> result,int start){
        for (int i = start; i < nums.length; i++) {
            // 和上个数字相等就跳过 以为上一轮已经保存过了
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            result.add(nums[i]);
            res.add(new ArrayList<>(result));
            dfs(nums,result,i+1);
            result.remove(result.size()-1);
        }

        /**
         *             1 2 2
         *          1       2     2
         *        2  2    2
         *       2
         */
    }
}
