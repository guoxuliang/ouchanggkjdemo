package com.example.ouc.demo.entity;

public class ShoppingCollectionEntity {


    /**
     * data : null
     * code : 200
     * msg : 收藏成功
     * success : true
     * execute : false
     */

    private Object data;
    private int code;
    private String msg;
    private boolean success;
    private boolean execute;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
}
