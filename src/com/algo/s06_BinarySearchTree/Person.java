package com.algo.s06_BinarySearchTree;

/**
 * Comparable java.util.lang中的接口
 */
public class Person implements Comparable<Person> {
	private int age;
	private String name;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person(int age) {
		this.age = age;
	}
	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public int compareTo(Person e) {
//		if (age > e.age) return 1;
//		if (age < e.age) return -1;
//		return 0;
		return age - e.age;
	}
	
	@Override
	public String toString() {
		return "age=" + age + " name=" + name;
	}
	
}
