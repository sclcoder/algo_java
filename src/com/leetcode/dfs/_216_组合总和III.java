package com.leetcode.dfs;
import java.util.ArrayList;
import java.util.List;
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
                k = 3 ,n = 9     k个数决定递归k层
                1                                2          3          4          5         6       7      8      9
           23456789                           3456789     456789     56789      6789      789       8      9      n
  3456789 456789 56789 6789 789 8 n



 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/combination-sum-iii
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _216_组合总和III {
    public static void main(String[] args) {
        System.out.println(combinationSum(3,9));
    }

    public static List<List<Integer>> combinationSum(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        dfs(k,n,1, res, result);
        return res;
    }

    /**
     * 效率感人啊。。。。
     * 因为在迭代到一定的临界值时, n的已经小于0,因为后序的选项是越来越大的，所以可以不用继续迭代了
     */
    public static void dfs(int k, int n , int start,  List<List<Integer>> res, List<Integer> result){
        if (n < 0 || k < 0) return;
        if (n == 0 && k == 0){
            res.add(new ArrayList<>(result));
        }
        if (n > 0 && k > 0){
            for (int i = start; i < 10; i++) {
                if (n-i < 0) return;
                result.add(i);
                dfs(k-1,n-i,i+1,res,result);
                // 回溯阶段
                result.remove(result.size()-1);
            }
        }
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
