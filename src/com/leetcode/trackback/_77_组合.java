package com.leetcode.trackback;
import java.util.ArrayList;
import java.util.List;

/**
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 *
 * 示例:
 *
 * 输入: n = 4, k = 2
 * 输出:
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combinations
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *
 */
public class _77_组合 {
    public static void main(String[] args) {
        List<List<Integer>> res = combine(4,3);
        System.out.println(res);
    }
    static List<List<Integer>> res = new ArrayList<>();
    static public List<List<Integer>> combine(int n, int k) {
        List<Integer> result = new ArrayList<>();
        dfs(1,result,n,k);
        return res;
    }

    private static void dfs(int start,List<Integer> result, int n, int k){
        if (k == 0){ // 说明结束
            res.add(new ArrayList<>(result));
        } else {
            for (int i = start; i <= n; i++) {
                result.add(i);
                dfs(i+1,result,n,k-1);
                result.remove(result.size() -1);
            }
        }
    }
}

