package com.interviewCode._00_偶遇的面试题;
public class _1_爬楼梯 {
    public static void main(String[] args) {
        /** n阶台阶的走法 每次走m个台阶（m<=n）
         f(n) = f(n-1) + f(n-2) + ... + f(2) + f(1);
         f(n-1) = f(n-2) + ... + f(2) + f(1) + f(0);
         f(n) = 2f(n-1) = 2*2f(n-2) = 2^(n-1)f(1) = 2^(n-1);   f(1)=1;

         动态规划
         dp[i]记录第i个台阶的走法
         dp[i] = sum( dp[1,i-1]);
         初始化
         dp[1] = 1;
         dp[2] = 2;
         */
        System.out.printf("%d",getMaxStep(5));
    }
    public  static  int getMaxStep(int n){
        int [] dp  = new int[n+1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j];
            }
        }
        return dp[n];
    }
}
