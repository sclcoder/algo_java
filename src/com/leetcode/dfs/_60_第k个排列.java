package com.leetcode.dfs;

public class _60_第k个排列 {
    public static void main(String[] args) {
        System.out.println(getPermutation(3,3));
    }

    static int index = 0;
    static String res = "";
    public static String getPermutation(int n, int k) {
        int [] used = new int[n+1];
        String str = "";
        dfs(n,str,used,k);
        return res;
    }
    private static void dfs(int n, String str,int[] used ,int k){
        if (str.length() == n){
            index++;
            if (index == k) { // 怎样结束递归
//                System.out.println(str);
                res = str;
            }
        } else if(index <= k) {
            for (int i = 1; i <= n ; i++) {
                if (used[i] == 1) continue;
                str = str + i;
                used[i] = 1;
                dfs(n,str,used,k);
                str = str.substring(0,str.length()-1); // 开始回溯
                used[i] = 0; // 状态重置
            }
        }
    }
    /**
     * 给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。
     *
     * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
     *
     *     "123"
     *     "132"
     *     "213"
     *     "231"
     *     "312"
     *     "321"
     *
     * 给定 n 和 k，返回第 k 个排列。
     *
     * 说明：
     *
     *     给定 n 的范围是 [1, 9]。
     *     给定 k 的范围是[1,  n!]。
     *
     * 示例 1:
     *
     * 输入: n = 3, k = 3
     * 输出: "213"
     *
     * 示例 2:
     *
     * 输入: n = 4, k = 9
     * 输出: "2314"
     *
     *
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/permutation-sequence
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
}
