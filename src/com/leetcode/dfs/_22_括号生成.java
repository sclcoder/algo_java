package com.leetcode.dfs;
import java.util.LinkedList;
import java.util.List;

/**
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * 示例：
 * 输入：n = 3
 * 输出：[
 *        "((()))",
 *        "(()())",
 *        "(())()",
 *        "()(())",
 *        "()()()"
 *      ]
 *
 * 通过次数144,722
 * 提交次数191,173
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

/**
 * 当前左右括号都有大于 0个可以使用的时候，才产生分支；
 * 产生左分支的时候，只看当前是否还有左括号可以使用；
 * 产生右分支的时候，还受到左分支的限制，右边剩余可以使用的括号数量一定得在严格大于左边剩余的数量的时候，才可以产生分支；
 * 在左边和右边剩余的括号数都等于 0 的时候结算。
 *
 * 作者：liweiwei1419
 * 链接：https://leetcode-cn.com/problems/generate-parentheses/solution/hui-su-suan-fa-by-liweiwei1419/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class _22_括号生成 {

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }
    static List<String> res = new LinkedList<>();
    public static List<String> generateParenthesis(int n) {
        dfs("",n,n);
        return res;
    }
    private static void dfs(String result, int left, int right){
        if (left==0 && right==0){
            res.add(result);
            return;
        }
        if (left > 0){
            dfs(result+"(",left-1,right);
        }
        if (left < right){
            dfs(result+")",left,right-1);
        }

    }


}
