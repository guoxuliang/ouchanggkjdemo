package com.example.ouc.demo.entity;

public class AuditEntity {


    /**
     * data : 1
     * success : true
     * msg : 查询提现申请状态成功
     * execute : true
     * code : 200
     */

    private String data;
    private boolean success;
    private String msg;
    private boolean execute;
    private int code;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
