package com.leetcode.dfs;
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

    /**
     *   给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
     *  * 示例
     *  * 输入: n = 4, k = 2
     *  典型的排列组合问题:
     *  直观想法就是循环: 第一次循环找到一个数，然后进行第二次循环找到第二个数。可是K的值不仅仅是2,可以是很多值
     *  这时只傻傻的手动循环就傻逼了。
     *  很明显需要循环k次, k可以使任意的值。这时可以想到递归。调用k次递归函数即可达到循环k次的目的。
     *  如 设计一个函数
     *  dfs(n,k){
     *      if(k == 0) return;
     *      dfs(n, k-1);
     *  }
     *  即可以达到循环k次的目的
     *
     *  自己写的过程中遇到的问题
     *  1.存放某一个结果的数组  List<Integer> list = new ArrayList<>() 该怎样传递的问题?
     *  这个问题是看了题解才知道的。
     *  在递归时一直使用同一个 List<Integer> list = new ArrayList<>(); 在将其添加到res时,
     *  采用 res.add(new ArrayList<>(list)) 这种方式将组合结果复制到一个新的数组中再添加到res。
     *
     *  2.题目要求选出的n值不能重复的问题？即不能出现[1,1]的组合
     *  这个问题可以在递归时添加一个start参数记录可选值的起始值
     *   dfs(start,n,k,res,result);
     */
    public List<List<Integer>> combine_rewirte(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> result = new ArrayList<>(); // 符合条件的组合
        dfs(1,n,k,result,res);
        return res;
    }
    public void dfs(int start ,int n, int k ,List<Integer> result, List<List<Integer>> res){
        if (k == 0){
            res.add(new ArrayList<>(result));
        } else {
            for (int i = start; i <= n; i++) {
                result.add(i);
                /**
                 * 易错的点，我就犯了
                 * dfs递归时start参数传入的应该是 i + 1 而不是 start+1
                 * 也很容易想清楚，下一轮循环的值应该在i变量之后，因为i之前的值已经使用过了。
                 */
                dfs(i+1, n, k - 1, result,res); // 深度优先搜索
                // 回溯阶段
                result.remove(result.size()-1);
            }
        }
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

