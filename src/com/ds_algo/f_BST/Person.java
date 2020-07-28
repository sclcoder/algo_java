package com.ds_algo.f_BST;

@SuppressWarnings("unused")
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
		return age - e.age;
	}
	
	@Override
	public String toString() {
		return "age=" + age + " name=" + name;
	}
	
}
