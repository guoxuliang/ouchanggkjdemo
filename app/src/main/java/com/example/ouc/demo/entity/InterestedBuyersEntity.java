package com.example.ouc.demo.entity;

public class InterestedBuyersEntity {


    /**
     * data : null
     * code : 200
     * success : true
     * execute : true
     * msg : 添加客户信息成功
     */

    private Object data;
    private int code;
    private boolean success;
    private boolean execute;
    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
