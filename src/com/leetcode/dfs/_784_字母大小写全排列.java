package com.leetcode.dfs;
import java.util.ArrayList;
import java.util.List;

public class _784_字母大小写全排列 {
    /**
     * 给定一个字符串S，通过将字符串S中的每个字母转变大小写，我们可以获得一个新的字符串。返回所有可能得到的字符串集合。
     *
     * 示例:
     * 输入: S = "a1b2"
     * 输出: ["a1b2", "a1B2", "A1b2", "A1B2"]
     *
     * 输入: S = "3z4"
     * 输出: ["3z4", "3Z4"]
     *
     * 输入: S = "12345"
     * 输出: ["12345"]
     *
     * 注意：
     *
     *     S 的长度不超过12。
     *     S 仅由数字和字母组成。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/letter-case-permutation
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */



    /**
     *  S = "a1b2"
     *  其实该题目和经典的组合问题并没有本质区别
     *  比如组合问题77，在解题时每次递归面临的选择都是多个值,所以用的for循环来遍历可能的值。
     *  这个题目面临的选择很少
     *  1.在遇到数字时,只有只一个选择就是选择这个数子,
     *  2.遇到字母时有两种选择 小写或大写
     *  情况很少，少到都没必要使用for循环来迭代所有的情况，只要枚举出来即可
     *
     */

    public static void main(String[] args) {
        List<String> list = letterCasePermutation("a1b2");
        System.out.println(list);
    }

    public static List<String> letterCasePermutation(String S){
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder(S);
        dfs(0, sb , res );
        return res;
    }

    /**
     *  别看写了这么多的dfs，其实本质上和组合问题是一样的。只不过经典的组合问题,面临的选择多，我们使用for循环
     *  来进行迭代的。这里因为数字没得选择所以直接dfs了,不存在回溯时状态重置的问题。
     *  当遇到的是字母时,因为遇到字母时有两种选择。选择了一个字母后,回溯阶段需要将其进行大小写转换(状态重置)并重新进行选择
     */
    public static void dfs(int index , StringBuilder sb , List<String> res){
        if (sb.length() == index){
            res.add(sb.toString());
        } else {
            char ch = sb.charAt(index);
            /// 说明是a-z A-Z
            if (ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122){
               dfs(index + 1, sb, res);  // 递归阶段
                // 回溯阶段:此时索引为index。 将以前选中的index位置的ch,更新为另一个选择。即状态重置后的选择新的选项
               sb.setCharAt(index, (char) (ch^32)); // 该操作其实包含了两个步骤: 1. 状态重置 2.选择其他选项
               dfs(index + 1, sb, res);  // 回溯后的重新递归, 因为这里没有for循环，所以是手动迭代
            } else {
                dfs(index + 1, sb, res);  // 非字母的情况
            }

        }
    }

}
