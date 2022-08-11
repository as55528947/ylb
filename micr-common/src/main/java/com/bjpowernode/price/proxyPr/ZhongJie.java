package com.bjpowernode.price.proxyPr;

/**
 * 动力节点乌兹
 * 2022-7-4
 */
public class ZhongJie implements Rent{
    private FangDong fangDong;

    public ZhongJie() {
    }

    public ZhongJie(FangDong fangDong) {
        this.fangDong = fangDong;
    }

    @Override
    public void rent() {
        seeHourse();
        fangDong.rent();
        heTong();
        zhifu();
    }
    public void seeHourse(){
        System.out.println("中介带看房");
    }

    public void heTong(){
        System.out.println("看房满意签合同");
    }
    public void zhifu(){
        System.out.println("中介收房租");
        System.out.println("中介把中介费扣除剩下的给房东");
    }

}
