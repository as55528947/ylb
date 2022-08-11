package com.bjpowernode.price.proxyPr;

/**
 * 动力节点乌兹
 * 2022-8-2
 */
public class TestString {
    public static void main(String[] args) {
        String a = "24ab_2t2";
        //String encode = encode(a);
        //System.out.println(encode);//444_aaaaa_a_b_\UL_ttt_t_2
        String[] s = a.split("_");
        System.out.println(s[0]);
    }
    public static String encode(String s){
        //encode后的字符串
        StringBuilder encodeString = new StringBuilder();
        char c = 0;
        int a = 0;

        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c >= '0' && c <= '9' &&i != s.length() -1){
                a = c -'0'+ 1;
                for (int j = 0; j < a; j++) {
                    encodeString.append(s.charAt(i + 1));
                }
            } else if (c == '_'){
                encodeString.append("\\UL");

            } else {
                encodeString.append(c);
            }
            encodeString.append('_');
        }
        encodeString.deleteCharAt(encodeString.length() -1);
        return encodeString.toString();
    }

    public static String decode(String s){
        String [] decodeString = s.split("_");
        return "zz";
    }
}
