package com.leetcode.dfs;

import java.util.ArrayList;
import java.util.List;

public class _401_二进制手表 {

    /**
     *  二进制手表顶部有 4 个 LED 代表小时（0-11），底部的 6 个 LED 代表分钟（0-59）。
     * 每个 LED 代表一个 0 或 1，最低位在右侧。
     *  8 4 2 1          时钟
     *  32 16 8 4 2 1    分钟
     *
     *  例如，上面的二进制手表读取 “3:25”。
     *
     * 给定一个非负整数 n 代表当前 LED 亮着的数量，返回所有可能的时间。
     *
     * 案例:
     * 输入: n
     * 返回: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/binary-watch
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * 经典的dfs解法:
     *  此题无非也是经典的组合问题的变种。选择范围为 时钟部分的 8 4 2 1 分钟部分的 32 16 8 4 2 1
     *  一开始想的太复杂，觉得要在时钟部分选择几个剩下的在分钟部分选择几个值，觉得很复杂。。。。
     *  其实将这两部分合成一部分来看，使用暴力法解答时，自然就可以将所有的情况包含到。
     *  其实就是真这么简单，这样是用dfs配合着剪枝操作就可以方便的解答该问题了。唯一和经典组合不同的时,迭代到的结果需要按时钟、分钟区分开来
     */

    public static List<String> readBinaryWatch(int num) {
        // 可选择的内容数组
        int[] times = new int[]{8, 4, 2, 1, 32, 16, 8, 4, 2, 1};
        List<String> res = new ArrayList<>();
        int hours = 0;
        int minutes = 0;
        dfs(times, num, 0, hours, minutes, res);
        return res;
    }

    public static void dfs(int[] times, int num, int start, int hours, int minutes, List<String> res) {
        if (0 == num) {
            if (hours < 12 && minutes < 60) { // 合理的值
                StringBuilder sb = new StringBuilder();
                sb.append(hours).append(':').append(minutes < 10 ? "0" + minutes : minutes);
                res.add(sb.toString());
            }
        } else {
            for (int i = start; i < times.length; i++) {
                if (i < 4) { // hours
                    hours += times[i];
                    dfs(times, num - 1, i + 1, hours, minutes, res);
                    hours -= times[i];
                } else {
                    minutes += times[i];
                    dfs(times, num - 1, i + 1, hours, minutes, res);
                    minutes -= times[i];
                }
            }
        }
    }


        public static void main(String[] args) {
            System.out.println(readBinaryWatch(3));
        }
    }
