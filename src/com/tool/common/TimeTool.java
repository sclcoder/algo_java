package com.tool.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTool {
    private  static final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss.SSS");
    // 接口
    public interface Task {
        void execute();
    }

    public static void check(String title, Task task){
        if (task == null) return;
        title = (title == null) ? "" : ("【" + title + "】");
        System.out.println(title);
        System.out.println("开始:" + fmt.format(new Date()));
        long begin = System.currentTimeMillis();
        task.execute();
        long end = System.currentTimeMillis();
        System.out.println("结束:" + fmt.format(new Date()));
        // 这里除1000.0 而不是除 1000 。如果是1000那么结果会省略小数部分不精确
        double delta = (end - begin) / 1000.0;
        System.out.println("耗时:" + delta + "秒");
        System.out.println("------------------------");
    }
}
