package com.leetcode.stack;


import java.util.HashMap;
import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *
 *     左括号必须用相同类型的右括号闭合。
 *     左括号必须以正确的顺序闭合。
 *
 * 注意空字符串可被认为是有效字符串。
 *
 * 示例 1:
 *
 * 输入: "()"
 * 输出: true
 *
 * 示例 2:
 *
 * 输入: "()[]{}"
 * 输出: true
 *
 * 示例 3:
 *
 * 输入: "(]"
 * 输出: false
 *
 * 示例 4:
 *
 * 输入: "([)]"
 * 输出: false
 *
 * 示例 5:
 *
 * 输入: "{[]}"
 * 输出: true
 */
public class _20_有效括号 {

    private static HashMap<Character,Character> map = new HashMap<>();
    static {
        map.put('(',')');
        map.put('[',']');
        map.put('{','}');
    }

    /**
     * 利用栈的先进后出的特点
     */
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)){
                // 左括号入栈
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                // 右括号与栈顶比较
                // value 是否相等
                if (c != map.get(stack.pop())) return false;
            }
        }
        // 最后栈为空 说明括号匹配有效
        return stack.isEmpty();
    }

    /**
     * 利用栈的先进后出的特点
     */
    public boolean isValid2(String s) {
       Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{'){
                // 左括号入栈
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                // 右括号与栈顶比较
                if (c == ')' && stack.pop() != '(')return false;
                if (c == ']' && stack.pop() != '[')return false;
                if (c == '}' && stack.pop() != '{')return false;
            }
        }
        // 最后栈为空 说明括号匹配有效
        return stack.isEmpty();
    }

    /**
     * 暴力但低效的解法
     */
    public boolean isValid1(String s) {
        while (s.contains("()") || s.contains("{}") || s.contains("[]")){
            s.replace("()","");
            s.replace("{}","");
            s.replace("[]","");
        }
       return s.isEmpty();
    }
}
