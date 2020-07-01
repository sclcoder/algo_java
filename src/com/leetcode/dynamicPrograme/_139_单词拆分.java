package com.leetcode.dynamicPrograme;
import java.util.ArrayList;
import java.util.List;
/**
 * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
 *
 * 说明：
 *
 *     拆分时可以重复使用字典中的单词。
 *     你可以假设字典中没有重复的单词。
 *
 * 示例 1：
 *
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
 *
 * 示例 2：
 *
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
 *      注意你可以重复使用字典中的单词。
 *
 * 示例 3：
 *
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/word-break
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _139_单词拆分 {
    public static void main(String[] args) {
        List<String> wordDict = new ArrayList<>();
        wordDict.add("leet");
        wordDict.add("code");
        System.out.println(wordBreak("leetcode",wordDict));
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        /**
         * 既然可以递归解决 那么动态规划可以试试
         * dp[i] 记录着s的前i个字符拆分是否可以被空格拆分成wordDict中存在的单词
         *
         * key = "leetcode"   wordDic = {"leet" "code"}
         * dp[0] 索引0代表空串,表示0个字符 显然 dp[0] = true;
         * dp[1] "l",不在字典中，dp[1] = false;
         * dp[2] 代表"le", 不在字典中, dp[2] = false;
         * dp[3] 代表"lee", 不在字典中, dp[3] = false;
         * dp[4] 代表"leet", 在字典中, 又有dp[0]==true ,所以dp[4] = true;
         * dp[5] 代表"leetc", 不在字典中dp[5], 但是之前的dp[4]==true 需要判断"c"是否在字典中 "c"不在字典中 dp[5] = false;
         * dp[6] 代表"leetco", 不在字典中dp[6], 但是之前的dp[4]==true 需要判断"co"是否在字典中 "co"不在字典中 dp[6] = false;
         * dp[7] 代表"leetcod", 不在字典中dp[7], 但是之前的dp[4]==true 需要判断"cod"是否在字典中 "cod"不在字典中 dp[7] = false;
         * dp[8] 代表"leetcode", 不在字典中dp[8], 但是之前的dp[4]==true 需要判断"code"是否在字典中 "code" 在字典中 dp[8] = true;
         *
         * 状态转移: dp[i] = dp[j] and wordDic contain s(j,i]  其中j范围[0,i];
         */
        boolean[]dp = new boolean[s.length()+1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (wordDict.contains(s.substring(j,i)) &&  dp[j] == true){
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    /**
     * 暴力
     */
    public static boolean wordBreak1(String s, List<String> wordDict) {
        return wordBreak(s,wordDict,0);
    }

    public static boolean wordBreak(String s,List<String> wordDict, int start){
        if (start == s.length()) return true;
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start,end)) && wordBreak(s,wordDict,end)){
                return true;
            }
        }
        return false;
    }


}
