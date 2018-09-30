package com.example.ouc.demo.entity;

public class GetVersionEntity {

    /**
     * data : 2
     * code : 200
     * msg : 获得版本号成功
     * execute : false
     * success : true
     */

    private int data;
    private int code;
    private String msg;
    private boolean execute;
    private boolean success;

    public int getData() {
        return data;
    }

    public void setData(int data) {
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

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
