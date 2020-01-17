package com.leetcode.trackback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的每个数字在每个组合中只能使用一次。
 *
 * 说明：
 *
 *     所有数字（包括目标数）都是正整数。
 *     解集不能包含重复的组合。
 *
 * 示例 1:
 *
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 所求解集为:
 * [
 *   [1, 7],
 *   [1, 2, 5],
 *   [2, 6],
 *   [1, 1, 6]
 * ]
 *
 * 示例 2:
 *
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 所求解集为:
 * [
 *   [1,2,2],
 *   [5]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _40_组合总和II {
    public static void main(String[] args) {
        int[] candidates = {2,5,2,1,2};
        List<List<Integer>> res = combinationSum(candidates,5);
        System.out.println(res);
    }
    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> result = new ArrayList<>();
        Arrays.sort(candidates); // 排序
        dfs(candidates,target,result,0);
        return res;
    }
    public static void  dfs(int[] candidates, int target, List<Integer> result, int start){
        if (target > 0){
            for (int i = start; i < candidates.length ; i++) {
                // 1 2 2 2 5         5
                if (i > start && candidates[i] == candidates[i-1]){
                    continue;
                }
                result.add(candidates[i]);
                dfs(candidates,target-candidates[i],result,i+1);
                result.remove(result.size()-1); // 回溯
            }
        } else {
            if (target == 0){
                res.add(new ArrayList<>(result));
            } else {
                return;
            }
        }
    }
}
