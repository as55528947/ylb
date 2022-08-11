package com.bjpowernode.price;

/**
 * 动力节点乌兹
 * 2022-7-3
 */
public class Student extends Person {
    @Override
    public boolean equals(Object obj) {
        obj = (Student)obj;
        if (this.getAge() == ((Student) obj).getAge()){
            if (this.getName() == ((Student) obj).getName()){
                return true;
            }
        }
        return false;
    }
}
