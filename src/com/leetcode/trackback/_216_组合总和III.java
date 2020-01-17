package com.leetcode.trackback;

import java.util.ArrayList;
import java.util.List;

public class _216_组合总和III {
    public static void main(String[] args) {
        System.out.println(combinationSum3(3,9));
    }

    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(n,k,path,1,res);
        return res;
    }
    private static void dfs(int n, int k, List<Integer> path, int start,List<List<Integer>> res){
        if (n == 0 && path.size() == k){
            res.add(new ArrayList<>(path));
            System.out.println(path);
        } else {
            if (n < 0 || path.size() > k) return;
            for (int i = start; i < 10 ; i++) { // 1-9 就是选择列表
                // 选择路径
                path.add(i);
//                System.out.println(path);
                // 递归
                dfs(n-i,k,path,i+1,res);
                // 回溯
                path.remove(path.size()-1);
            }
        }
    }
}
/**

 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。

 说明：

 所有数字都是正整数。
 解集不能包含重复的组合。

 示例 1:

 输入: k = 3, n = 7
 输出: [[1,2,4]]

 示例 2:

 输入: k = 3, n = 9
 输出: [[1,2,6], [1,3,5], [2,3,4]]

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/combination-sum-iii
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */