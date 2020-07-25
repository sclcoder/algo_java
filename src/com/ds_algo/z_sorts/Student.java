package com.ds_algo.z_sorts;

public class Student implements Comparable<Student> {
    /**
     * java 语法问题记录: 当要真正实现接口函数时使用 implements
     * 当设计一个泛型遵守某个接口时使用 extends
     */
    public int score;
    public int age;

    public Student(int score, int age) {
        this.score = score;
        this.age = age;
    }

    @Override
    public int compareTo(Student o) {
        return age - o.age;
    }
}
