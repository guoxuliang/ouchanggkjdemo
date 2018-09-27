package com.example.ouc.demo.entity;

public class WithdrawalEntity {

    /**
     * data : 1待处理
     * msg : 提现申请提交成功
     * execute : true
     * code : 200
     * success : true
     */

    private String data;
    private String msg;
    private boolean execute;
    private int code;
    private boolean success;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
