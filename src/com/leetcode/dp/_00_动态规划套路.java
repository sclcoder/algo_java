package com.leetcode.dp;

public class _00_动态规划套路 {
    public static void main(String[] args) {

        // 计算斐波那契数字
        TimeTool.check("递归", new TimeTool.Task() {
            @Override
            public void execute() {
                System.out.println(fib(40));
            }
        });
        TimeTool.check("递归-备忘录", new TimeTool.Task() {
            @Override
            public void execute() {
                System.out.println(fib2(40));
            }
        });
        TimeTool.check("动态规划", new TimeTool.Task() {
            @Override
            public void execute() {
                System.out.println(fib3(40));
            }
        });
    }
    private static int fib(int n){
        if (n == 1 || n == 2) return 1;
        return fib(n-1 ) + fib(n-2);
    }

    private static int fib2(int n){
        int[] memo  = new int[n + 1];
        return helper(memo,n);
    }
    private static int helper(int[]memo, int n){
        if (memo[n] != 0) return memo[n];
        if (n == 1 || n == 2) return 1;
        memo[n] = helper(memo,n-1 ) + helper(memo,n-2);
        return memo[n];
    }

    /**
     * 有了上一步「备忘录」的启发，我们可以把这个「备忘录」独立出来成为一张表，就叫做 DP table 吧
     * 在这张表上完成「自底向上」的推算岂不美哉！
     */
    private static int fib3(int n){
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = dp[2] = 1;
        for (int i = 3; i < n+1; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
}
