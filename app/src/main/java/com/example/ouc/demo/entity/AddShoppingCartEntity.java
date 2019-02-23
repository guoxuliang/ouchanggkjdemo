package com.example.ouc.demo.entity;

public class AddShoppingCartEntity {

    /**
     * data : null
     * success : true
     * code : 200
     * msg : 添加成功。
     * execute : true
     */

    private Object data;
    private boolean success;
    private int code;
    private String msg;
    private boolean execute;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
}
