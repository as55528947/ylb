package com.bjpowernode.price.proxyPr;

import java.util.Scanner;

/**
 * 动力节点乌兹
 * 2022-7-4
 */
public class diaoyongMain {

    public static void main(String[] args) {
      /* ZhongJie zhongJie = new ZhongJie(new FangDong());
       zhongJie.rent();*/
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入:");
        //String s = scanner.nextLine();
        int i = scanner.nextInt();

        System.out.println(i+ "xcasdad");
        StudentServiceImpl studentService = new StudentServiceImpl();
        StudentProxy studentProxy = new StudentProxy();
        studentProxy.setStudentService(studentService);
        studentProxy.add();
    }
}
