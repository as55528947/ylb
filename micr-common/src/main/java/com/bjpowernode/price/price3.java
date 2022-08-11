package com.bjpowernode.price;

public class price3 {
    /**
     * 用java语言实现两个函数encode()和decode()，分别实现对字符串的变换和复原。
     * 变换函数encode()顺序考察已知字符串的字符，按以下规则逐组生成新字符串：
     *
     * (1)若已知字符串的当前字符不是大于0的数字字符，则复制该字符于新字符串中。
     * (2)若已知字符串的当前字符是一个数字字符，且它之后没有后续字符，则简单地将他复制到新字符串中。
     * (3)若已知字符串的当前字符是一个大于0的数字字符，并且还有后续字符，设该数字字符的面值为n，则将它的后续字符（包括
     * 后续字符是一个数字字符）重复复制n+1次到新字符串中。
     * (4)若已知字符串的当前字符是下划线‘_’，则将当前字符串变换为用‘\UL’。
     * (5)以上述一次变换为一组，在不同组之间另插入一个下划线‘_’用于分隔。
     *
     * 例如：encode()函数对字符串24ab_2t2的变换结果为444_aaaaa_a_b_\UL_ttt_t_2
     *
     * 复原函数decode()做变换函数encode()的相反的工作，按照上述规则逆运算，变回原来的字符串。滤掉多余的下划线字符。
     *
     * 要求：代码可读性要好，逻辑要清。
     * @author Administrator
     *
     */
    public static void main(String[] args) {
        String testChar="24ab_2t2";
        // encode(testChar);
        System.out.println(encode(testChar));
        System.out.println(decode(encode(testChar)));
    }

    public  static String encode(String s){
        if(s==null){
            throw new RuntimeException("字符串末初始化！");
        }
        if(s.length()<0){

            throw new RuntimeException("字符串不能为空！");
        }
        StringBuffer sb = new StringBuffer();
        char c=0;
        int k =0;
        int n = s.length();
        for(int i=0;i<n;i++){
            c=s.charAt(i);
            //对字符进行处理：
            //若已知字符串的当前字符不是大于0的数字字符，则复制该字符于新字符串中
            //是数字，且不是最后一位，//获取当前的面值c，将后面一位进行你c+1次赋值
            if(c>='1' &&c<='9'&& i!=n-1){
                k=c-'0'+1;
                for(int m=0;m<k;m++){

                    sb.append( s.charAt(i+1)) ;
                }

            }else if(c=='_'){

                sb.append("\\UL");
            }else{
                sb.append(c);
            }
            sb.append("_");
        }
        return sb.deleteCharAt(sb.length()-1).toString();

    }
    /*
     * 1:先用“——”分割，如果只有一个（则可定只有一个字符，直接赋值），
     *  1.1如果有两个，（循环判断，每一个的长度等于一时，直接赋值，如果是“——”，转换成“\\UL,不然就是字符的个数减一的值”）
     */


    public static String decode(String str){
        StringBuffer sb = new StringBuffer();
        if(str==null){
            throw new RuntimeException("字符串末初始化！");
        }
        if(str.length()<0){

            throw new RuntimeException("字符串不能为空！");
        }


        String[] strArray= str.split("_");
        int n =strArray.length;
        if(n==1){
            sb.append(str);

        }else{
            for(int i = 0 ;i<n;i++){

                int length = strArray[i].length();
                char c =  strArray[i].charAt(0);
                if(length==1){

                    sb.append(c);
                }else{
                    if( strArray[i].equals("\\UL")){
                        sb.append("_");
                    }else {
                        sb.append(strArray[i].length()-1)   ;
                        //sb.append(strArray[i].charAt(0));
                    }
                }

            }
        }

        return sb.toString();



    }

}