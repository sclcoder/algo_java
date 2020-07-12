package com.leetcode.dfs;

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
        List<List<Integer>> res = combinationSum_rewrite(candidates,5);
        System.out.println(res);
    }

    /**
     * 问题的难点在于解集不能重复? 怎样解决解集重复呢？
     * 常规方案不是很好解决啊！！！ 解决方案很巧妙需要对dfs过程非常清晰才能想到
     * 方案: 先将数组candidates排序
     * 在回溯阶段: 找到合适的组合或者是没有合适的组合都会导致本次递归结束, 然后进行回溯,
     * 这时会进行剪枝操作,剪枝操作后会循环迭代,如果本次递归的第一迭代，那么这个值可以使用。如果非本次递归的第一次迭代且
     * 这次迭代的值和第一次迭代的值相同，那么这个值就是会重复的值。可以跳过了
     */
    static public List<List<Integer>> combinationSum_rewrite(int[] candidates, int target){
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, result, res);
        return res;
    }
    static public void dfs(int[] candidates, int target, int start , List<Integer> result , List<List<Integer>> res){
        if (target == 0){
            res.add(new ArrayList<>(result));
        }
        if (target > 0){
            for (int i = start; i < candidates.length; i++) {
                /**
                 * 如果是同一层的dfs, 在非第一迭代时的值如果和前一次相同，说明遇到重复的组合了,需要进行剪枝
                 * 因为第一次迭代的那个分支是个非常大的分支，其已经包含这次迭代的结果了
                 * 画图说明一切问题
                 *  [1,2,2,2,5]   5;
                 *               1            2          2            2         5
                 *         2   2   2  5     2 2 5       2 5           5        n
                 *      2 2 5  25   5  n    25 5 n      5 n
                 *  从图中可以看到 前边的分支可能会包含后序分支
                 */
                if (i > start && candidates[i] == candidates[i-1]) continue;
                result.add(candidates[i]);
                dfs(candidates,target - candidates[i], i + 1, result, res);
                result.remove(result.size()-1);
            }
        }
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
