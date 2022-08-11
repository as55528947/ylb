package com.bjpowernode.price;

import java.util.Comparator;
/**
 * 动力节点乌兹
 * 2022-7-1
 */
public class Person implements Comparator  {
    private String name;
    private Integer age;
    static String j = "10";

    public Person() {
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public static String getJ() {
        return j;
    }

    public static void setJ(String j) {
        Person.j = j;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
