package com.example.ouc.demo.entity;

public class MembersEntity {


    /**
     * data : {"waitaccount":110,"commission":2000}
     * code : 200
     * msg : 查询用户余额，待进账成功
     * execute : true
     * success : true
     */

    private DataBean data;
    private int code;
    private String msg;
    private boolean execute;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * waitaccount : 110
         * commission : 2000
         */

        private double waitaccount;
        private double commission;

        public double getWaitaccount() {
            return waitaccount;
        }

        public void setWaitaccount(int waitaccount) {
            this.waitaccount = waitaccount;
        }

        public double getCommission() {
            return commission;
        }

        public void setCommission(int commission) {
            this.commission = commission;
        }
    }
}
