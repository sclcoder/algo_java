package com.ds_algo.j_map;

import com.ds_algo.i_set.Set;
import com.tool.common.file.FileInfo;
import com.tool.common.file.Files;

@SuppressWarnings({"unused"})
public class TestTreeMapMain {
    public static void main(String[] args) {
        test2();
    }

    static void test1() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("c", 2);
        map.put("a", 5);
        map.put("b", 6);
        map.put("a", 8);
        System.out.println(map.size());
        map.traversal(new Map.Visitor<String, Integer>() {
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }


    static void test2() {
        String filePath1 = "/Users/tiny/Desktop/java_src/java/util";
        String filePath2 = "/Users/tiny/Desktop/algo_java/src";
        String filePath3 = "/Users/tiny/Desktop/yunjifen/server_qifu";

        FileInfo fileInfo = Files.read(filePath3,
                new String[]{"java"});

        System.out.println("文件数量：" + fileInfo.getFiles());
        System.out.println("代码行数：" + fileInfo.getLines());
        String[] words = fileInfo.words();
        System.out.println("单词数量：" + words.length);

        Map<String, Integer> map = new TreeMap<>();
        for (String word : words) {
            Integer count = map.get(word);
            count = (count == null) ? 1 : (count + 1);
            map.put(word, count);
        }

        map.traversal(new Map.Visitor<String, Integer>() {
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    static void test3() {
        Set<String> set = new TreeSet<>();
        set.add("c");
        set.add("b");
        set.add("c");
        set.add("c");
        set.add("a");

        set.traversal(new Set.Visitor<String>() {
            public boolean visit(String element) {
                System.out.println(element);
                return false;
            }
        });
    }

}
