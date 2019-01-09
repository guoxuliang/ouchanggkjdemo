package com.example.ouc.demo.entity;

public class SubmitCustomerInfoEntity {

    /**
     * data : 2
     * code : 200
     * msg : 新的订单记录已生成
     * execute : true
     * success : true
     */

    private String data;
    private int code;
    private String msg;
    private boolean execute;
    private boolean success;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
