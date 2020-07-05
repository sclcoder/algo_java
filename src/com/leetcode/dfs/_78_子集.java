package com.leetcode.dfs;
import java.util.ArrayList;
import java.util.List;
/**
 * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 *
 * 说明：解集不能包含重复的子集。
 *
 * 示例:
 *
 * 输入: nums = [1,2,3]
 * 输出:
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
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/subsets
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _78_子集 {
    public static void main(String[] args) {
        subsets(new int[] {1,2,3});
        System.out.println(res);
    }

    static List<List<Integer>> res = new ArrayList<>();

    /**
     * 思路: 从集合中选择一个数 再从剩下的集合中选择其他数字 (可选可不选)
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<Integer> result = new ArrayList<>();
        res.add(new ArrayList<>(result));
        dfs(nums,result,0);
        return res;
    }
    private static void dfs(int[] nums, List<Integer> result,int start){
        // 组合问题
        for (int i = start; i < nums.length; i++) {
            result.add(nums[i]);
            res.add(new ArrayList<>(result));
            dfs(nums,result,i+1);
            result.remove(result.size()-1);
        }
    }
}
