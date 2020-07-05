package com.leetcode.dfs;
import java.util.ArrayList;
import java.util.List;

public class _748_字母大小写全排列 {
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
    public static void main(String[] args) {

        /**                  a1b2                       index from  0 to length
         *           a1b2           A1b2
         *       a1b2    a2B2       A1b2     A1B2       这棵树的子结点就是结果
         *
         *       这类搜索问题的思路就是画树形图，这个树形图一般也是递归结构，然后看着图把代码写出来。
         *       说起来比较简单，但是也是需要一定的代码积累。
         *
         *       思路: 从一个分支走到头 然后开始回溯,走其他分支
         */
        letterCasePermutation("a1b2");
    }
    public static List<String> letterCasePermutation(String S){
        int len = S.length();
        char[] chars = new char[len];
        List<String> res = new ArrayList<>();
        dsf(0,len,chars,S,res); // 深度优先搜索、回溯
        System.out.println(res);
        return res;
    }
    private static void  dsf(int start, int length,char[] chars ,String S,List<String> res){
        // 退出递归条件
        if (start == length){
            res.add(new String(chars));
        } else {
            chars[start] = S.charAt(start);
            // 先一条路走到黑
            dsf(start+1,length,chars,S,res);
            if(Character.isLetter(S.charAt(start))){
                // 回溯
                chars[start] = (char) (S.charAt(start) ^ (1 << 5));
                dsf(start+1,length,chars,S,res);
            }
        }

    }

}
