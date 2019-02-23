package com.example.ouc.demo.entity;

public class ConfirmGoodsEntity {

    /**
     * data : null
     * code : 200
     * success : true
     * msg : 确认收货成功。
     * execute : true
     */

    private Object data;
    private int code;
    private boolean success;
    private String msg;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }
}
