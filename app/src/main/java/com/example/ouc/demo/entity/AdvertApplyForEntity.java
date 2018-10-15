package com.example.ouc.demo.entity;

public class AdvertApplyForEntity {

    /**
     * data : null
     * msg : 申请广告投放成功
     * execute : true
     * code : 200
     * success : true
     */

    private Object data;
    private String msg;
    private boolean execute;
    private int code;
    private boolean success;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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
}
