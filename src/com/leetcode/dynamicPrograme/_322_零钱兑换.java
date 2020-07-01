package com.leetcode.dynamicPrograme;

import java.util.Arrays;
/**
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 *
 * 示例 1:
 *
 * 输入: coins = [1, 2, 5], amount = 11
 * 输出: 3
 * 解释: 11 = 5 + 5 + 1
 *
 * 示例 2:
 *
 * 输入: coins = [2], amount = 3
 * 输出: -1
 *
 * 说明:
 * 你可以认为每种硬币的数量是无限的。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/coin-change
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _322_零钱兑换 {

    public static void main(String[] args) {
//        System.out.println(coinChange(new int[]{2} , 3));
//        System.out.println(coinChange2(new int[]{1,2,5} , 11));
//        System.out.println(coinChange_scl(new int[]{2} , 3));
        System.out.println(coinChange_scl2(new int[]{1,2,5} , 11));

    }

    // 使用动态规划
    public static int coinChange_scl3(int[] coins, int amount){
        // dp[i] 记录兑换金额为i时，需要金币的的最少个数
        // dp[i] = min( dp[i-coin(0...coin)]) + 1;
        int[] dp = new int[amount+1]; // 默认值为0
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin < 0) continue;
                dp[i] = Math.min(dp[i-coin] + 1, dp[i]);
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    // 使用动态规划
    public static int coinChange_scl2(int[] coins, int amount){
        // dp[i] 记录兑换金额为i时，需要金币的的最少个数
        // dp[i] = min( dp[i-coin(0...coin)]) + 1;
        int[] dp = new int[amount+1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin < 0) continue;
                dp[i] = Math.min(dp[i-coin] + 1, dp[i]);
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }


    /// 改题目本身递归比较简单，但是写起来有很多坑
    public static int coinChange_scl1(int[] coins, int amount){
        if (amount == 0) return 0;
        if (amount < 0) return Integer.MAX_VALUE;
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 转变为子问题
            int subCount = coinChange_scl(coins , amount - coin);
            //  subCount的取值可能为Integer.MAX_VALUE  or  -1 这两个值都输入无解的情况
            if (subCount == Integer.MAX_VALUE || subCount == -1) continue;
//            System.out.println("subcount" + subCount);
            res = Math.min(subCount + 1, res);
        }
        return res == Integer.MAX_VALUE ? -1 : res;  // 题目要求无法兑换返回-1. 这个-1可能成为subCount的值 判断时需要注意
    }
    /// 出现问题的版本 测试用例 [2] 3
    public static int coinChange_scl(int[] coins, int amount){
        if (amount == 0) return 0;
        if (amount < 0) return Integer.MAX_VALUE;
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 转变为子问题
            int subCount = coinChange_scl(coins , amount - coin);
            System.out.println("subcount: " + subCount + "   amount: " + amount); // 通过日志发现问题
            //  subCount的取值可能为Integer.MAX_VALUE   -1
            if (subCount == Integer.MAX_VALUE) continue;
            // 当 amount为1时,subCount == Integer.MAX_VALUE。跳过了本次循环。此时coins的遍历结束了，
            // 进入到了 return res == Integer.MAX_VALUE ? -1 : res;处最终amount==3的返回值成了-1
            res = Math.min(subCount + 1, res);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
    /**
     * 递归操作: 有点类型回溯
     */
    public static int coinChange(int[] coins, int amount) {
        if (amount == 0 ) return 0;
        int ans = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (amount - coin  < 0) continue; // 小于0的情况说明本次无解 跳出本次循环
            int subP = coinChange(coins,amount-coin);
            if (subP == -1) continue;
            ans = Math.min(subP + 1,ans);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans; // ans==Integer.MAX_VALUE说明本次循环无解amount-coin<0
    }

    /**
     * 动态规划
     */
    public static int coinChange2(int[] coins, int amount){
        int []dp = new int[amount + 1]; // dp数组记录 0--amount 的金额需要的最少张数  具备最优子问题
        Arrays.fill(dp,amount+1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins){
                if (i - coin < 0) continue;
                dp[i] = Math.min(dp[i] , dp[i-coin] + 1); // dp[i-coin] 记录了这个金额需要的最小张数
//                System.out.println(dp[i]);
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}
