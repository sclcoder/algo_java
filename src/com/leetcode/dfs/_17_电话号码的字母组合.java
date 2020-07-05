package com.leetcode.dfs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 *
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *  1 !@#   2 abc  3 def
 *  4 ghi   5 jkl  6 mno
 *  7 pqrs  8 tuv  9 wxyz
 *
 * 示例:
 * 输入："23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 *
 * 说明:
 * 尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *
 */


public class _17_电话号码的字母组合 {
    public static void main(String[] args) {
        List<String> list = letterCombinations("237");
        System.out.println(list);
    }
    static List<String> res = new ArrayList<>();
    static Map<String,String> phone = new HashMap<String, String>(){
        {
            put("2", "abc");
            put("3", "def");
            put("4", "ghi");
            put("5", "jkl");
            put("6", "mno");
            put("7", "pqrs");
            put("8", "tuv");
            put("9", "wxyz");
        }
    };
    public static List<String> letterCombinations(String digits) {
        String result = "";
        dfs(result,digits);
        return res;
    }
    private static void dfs(String result ,String digits){
        if (digits.length() == 0) {
            res.add(result);
        }else {
            String digit = digits.substring(0,1);
            String letters = phone.get(digit);
            for (int i = 0; i < letters.length(); i++) {
                result = result + letters.charAt(i);
                dfs(result,digits.substring(1));
                result = result.substring(0,result.length()-1); // 回溯
            }
        }
    }
}
