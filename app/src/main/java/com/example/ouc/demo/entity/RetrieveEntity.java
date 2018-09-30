package com.example.ouc.demo.entity;

public class RetrieveEntity {

    /**
     * data : null
     * code : 404
     * execute : false
     * msg : 密码修改失败
     * success : true
     */

    private Object data;
    private int code;
    private boolean execute;
    private String msg;
    private boolean success;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
