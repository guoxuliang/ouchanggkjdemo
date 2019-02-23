package com.example.ouc.demo.entity;

public class AddSettingAddressEntity {

    /**
     * data : null
     * success : true
     * code : null
     * execute : false
     * msg : 添加成功！
     */

    private Object data;
    private boolean success;
    private int code;
    private boolean execute;
    private String msg;

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
