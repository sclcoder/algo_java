package com.algo.s01_ArrayList;

/**
 * 条件测试
 */
public class Assert {
    public static void test(boolean value){
        try {
            if (!value) throw new Exception("未通过测试");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
