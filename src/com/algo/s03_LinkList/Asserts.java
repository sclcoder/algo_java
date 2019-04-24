package com.algo.s03_LinkList;

/**
 * 条件测试
 */
public class Asserts {
    public static void test(boolean value){
        try {
            if (!value) throw new Exception("未通过测试");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
