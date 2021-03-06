package com.example.ouc.demo.entity;

public class CodeEntity {

    /**
     * data : 380066
     * code : null
     * success : true
     * execute : false
     * msg : 验证码发送成功
     */

    private String data;
    private int code;
    private boolean success;
    private boolean execute;
    private String msg;

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
