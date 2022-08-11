package com.bjpowernode.front.view;

import com.bjpowernode.enums.RCode;

import java.util.List;
import java.util.Map;

/**
 * 动力节点乌兹
 * 2022-4-19
 *
 * 应答结果 controller返回值的结果都用这个类
 */
public class RespResult {
    //应答码
    private int code;

    //code的文字说明，一般提示给用看/
    private String msg;

    //单个数据
    private Object data;

    //集合数据
    private List list;

    private Map map;

    //分页
    private PageInfo page;

    //访问token
    private String accessToken;

    //表示应答成功的RespResult对象
    public static RespResult ok(){
        RespResult result = new RespResult();
        result.setCode(RCode.SUCC);
        return result;
    }

    //表示应答失败的RespResult对象
    public static RespResult fail(){
        RespResult result = new RespResult();
        result.setCode(RCode.UNKOWN);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(RCode rCode) {
        this.code = rCode.getCode();
        this.msg = rCode.getText();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

}
