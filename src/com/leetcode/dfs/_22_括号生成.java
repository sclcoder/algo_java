package com.leetcode.dfs;

import java.util.LinkedList;
import java.util.List;

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

    /**
     * 当前左右括号都有大于 0个可以使用的时候，才产生分支；
     * 产生左分支的时候，只看当前是否还有左括号可以使用；
     * 产生右分支的时候，还受到左分支的限制，右边剩余可以使用的括号数量一定得在严格大于左边剩余的数量的时候，才可以产生分支；
     * 在左边和右边剩余的括号数都等于 000 的时候结算。
     *
     * 作者：liweiwei1419
     * 链接：https://leetcode-cn.com/problems/generate-parentheses/solution/hui-su-suan-fa-by-liweiwei1419/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}
