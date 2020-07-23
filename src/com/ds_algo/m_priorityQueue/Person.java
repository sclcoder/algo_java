package com.ds_algo.m_priorityQueue;

import java.util.Comparator;

public class Person implements Comparable<Person> {
    int age;
    String name;
    int brokenBone;

    public Person(int age, String name, int brokenBone) {
        this.age = age;
        this.name = name;
        this.brokenBone = brokenBone;
    }

    @Override
    public int compareTo(Person o) {
        return this.brokenBone - o.brokenBone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", brokenBone=" + brokenBone +
                '}';
    }
}
