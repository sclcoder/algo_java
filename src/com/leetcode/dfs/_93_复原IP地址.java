package com.leetcode.dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * 93. 复原IP地址
 * 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
 *
 * 有效的 IP 地址正好由四个整数（每个整数位于 0 到 255 之间组成），整数之间用 '.' 分隔。
 *
 * 示例:
 *
 * 输入: "25525511135"
 * 输出: ["255.255.11.135", "255.255.111.35"]
 */
public class _93_复原IP地址 {

    static List<String> res = new ArrayList<>();
    public static List<String> restoreIpAddresses(String s) {
        if (s == null || s.length() == 0 || s.length() > 12) return res;
        List<String> result = new ArrayList<>();
        dfs(0,s,result);
        return res;
    }

    /**
     * cur: 记录当前位置
     */
    public static void dfs(int cur , String s,List<String> result){
        if (result.size() == 4 && cur == s.length()){ // 段数足够了 且 cur到结尾了
            res.add(String.join(".", result));
        } else {
            /// 每一段都有三中选择 长度分别为 1、2、3
            for (int len = 1; len <= 3; len++) {
                if (cur + len > s.length()) continue;
                String segment = s.substring(cur,cur + len);
                /// 超过255 或者 长度大于1时以0开头
                if (
                        len == 3 && Integer.parseInt(segment) > 255 ||
                        len > 1 && segment.startsWith("0")
                   )    continue;

                /// 找到合适的一个segment
                result.add(segment);
                dfs(cur + len, s, result);
                result.remove(result.size()-1);
            }
        }
    }


    public static void main(String[] args) {
        System.out.println(restoreIpAddresses("25525511135"));
    }
}
