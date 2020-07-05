package com.leetcode.dfs;
import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的数字可以无限制重复被选取。
 *
 * 说明：
 *
 *     所有数字（包括 target）都是正整数。
 *     解集不能包含重复的组合。
 *
 * 示例 1:
 *
 * 输入: candidates = [2,3,6,7], target = 7,
 * 所求解集为:
 * [
 *   [7],
 *   [2,2,3]
 * ]
 *
 * 示例 2:
 *
 * 输入: candidates = [2,3,5], target = 8,
 * 所求解集为:
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 *
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class _37_组合总和 {
    public static void main(String[] args) {
        int[] candidates = {2,3,5};
//        Arrays.sort(candidates); // 升序
        List<List<Integer>> res = combinationSum(candidates,8);
        System.out.println(res);
    }
    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> result = new ArrayList<>();
        // 思路: 先选出一个数字 n,将target更新为target-n,继续在candidates中选择数组(因为可以重复选择)以上思路是个递归过程
        // 从candidates中选出一个数字n需要循环
        dfs(candidates,target,result,0);
        return res;
    }
    public static void  dfs(int[] candidates, int target, List<Integer> result,int start){
        if (target > 0){
            for (int i = start; i < candidates.length ; i++) { // 使用start去重
                result.add(candidates[i]);
                dfs(candidates,target-candidates[i],result,i);
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
