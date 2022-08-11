package com.bjpowernode.price.proxyPr;

/**
 * 动力节点乌兹
 * 2022-7-4
 */
public class StudentProxy implements StudentService {
    private StudentService studentService;

    public StudentProxy() {
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void add() {
        System.out.println("日志功能");
        studentService.add();
    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }

    @Override
    public void select() {

    }
}
