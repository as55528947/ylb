package com.bjpowernode;

import com.bjpowernode.price.Person;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动力节点乌兹
 * 2022-4-28
 */
public class myTest{
    @Test
    public void hashMapTest() throws InterruptedException, IllegalAccessException, InstantiationException {

        AtomicInteger ac = new AtomicInteger();
        ac.set(10);
        ac.addAndGet(2);
        System.out.println(ac);
        Person person = new Person();
        Person person1 = Person.class.newInstance();

        TreeSet set = new TreeSet();
        Integer z = 32;
        List<String> strings = new ArrayList<>();
        strings.add("xx");
        strings.add("zz");
        strings.get(1);
        List<String> stringk = new ArrayList<>();
        stringk.add("xx");
        stringk.get(1);
        Map<String, String> testMap = new HashMap<>();
        System.out.println(testMap == null);
    }

    @Test
    public void test2(){
        String testz = "zzaascbnujkasbdjkbanbqwiubdoiwjdakncpzklnqpnmskadnlpqjdknajkcxncbjhdsbfhjwvyhjdgyuqqpkweo";
        String testzz = "aaabbbzzzzzz";
        Map<Character,Integer> map = new HashMap<>();
        //System.out.println(testz.length());85
        //System.out.println(testz.charAt(1));
        for (int i = 0; i < testzz.length(); i++) {
            if (!map.containsKey(testzz.charAt(i))){
                map.put(testzz.charAt(i), 1);
            }else {
                map.put(testzz.charAt(i), map.get(testzz.charAt(i)) + 1);
            }
        }
        System.out.println(map);
    }
}
