package com.leetcode.dfs;


import java.util.ArrayList;
import java.util.List;

/**
 * 面试题 08.09. 括号
 * 括号。设计一种算法，打印n对括号的所有合法的（例如，开闭一一对应）组合。
 * 说明：解集不能包含重复的子集。
 * 例如，给出 n = 3，生成结果为：
 *
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 *                (
 *            (      )
 *          (  )      (
 *         )  ( )    ( )
 *        )   ) )    ) ( 
 *       )    ) (    ) )
 */
public class _面试0809括号 {
    static List<String> res = new ArrayList<>();
    public static List<String> generateParenthesis(int n) {
        StringBuilder sb = new StringBuilder();
        dfs(n,n, sb);
        return res;
    }
    public static void dfs(int left, int right , StringBuilder sb){

        if (left == 0 &&  right == 0){
            res.add(sb.toString()); /// 找到一个合适的解
            return;
        }

        /// 剪枝处理，处理不合理的情况

        if (left > 0){
            /// 每次面临的选择是什么呢？无非就是左右括号两种选择
            sb.append("("); // 选择左括号
            dfs(left - 1 , right , sb);
            sb.deleteCharAt(sb.length()-1);
        }
        if (right > 0 && left < right) {
            sb.append(")"); // 选择右括号
            dfs(left , right - 1 , sb);
            sb.deleteCharAt(sb.length()-1);
        }
    }

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }

}
